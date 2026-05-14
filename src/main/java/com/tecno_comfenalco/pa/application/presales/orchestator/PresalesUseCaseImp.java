package com.tecno_comfenalco.pa.application.presales.orchestator;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.auth.Exceptions.UserAlreadyExistsException;
import com.tecno_comfenalco.pa.application.auth.ports.IUserRepositoryPort;
import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
import com.tecno_comfenalco.pa.application.presales.command.actions.EditPresalesCommand;
import com.tecno_comfenalco.pa.application.presales.command.actions.GetPresalesInfoCommand;
import com.tecno_comfenalco.pa.application.presales.command.actions.ListPresalesCommand;
import com.tecno_comfenalco.pa.application.presales.command.actions.RegisterPresalesCommand;
import com.tecno_comfenalco.pa.application.presales.command.response.EditPresalesCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.GetPresalesByIdCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.GetPresalesInfoCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.ListPresalesCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.RegisterPresalesCommandResult;
import com.tecno_comfenalco.pa.application.presales.exceptions.PresalesAlreadyExistsException;
import com.tecno_comfenalco.pa.application.presales.exceptions.PresalesNotFoundException;
import com.tecno_comfenalco.pa.application.presales.ports.IPresalesRepositoryPort;
import com.tecno_comfenalco.pa.application.presales.usecases.PresalesUseCase;
import com.tecno_comfenalco.pa.domain.auth.models.UserModel;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;
import com.tecno_comfenalco.pa.shared.utils.helper.ValidateQueryParams;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

@Service
public class PresalesUseCaseImp implements PresalesUseCase {

    private final IDistributorRepositoryPort distributorRepositoryPort;
    private final IPresalesRepositoryPort presalesRepositoryPort;
    private final IUserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public PresalesUseCaseImp(IDistributorRepositoryPort distributorRepositoryPort,
            IPresalesRepositoryPort presalesRepositoryPort, IUserRepositoryPort userRepositoryPort,
            PasswordEncoder passwordEncoder) {
        this.presalesRepositoryPort = presalesRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.distributorRepositoryPort = distributorRepositoryPort;
    }

    @Override
    public RegisterPresalesCommandResult registerPresales(RegisterPresalesCommand cmd) {

        // buscamos la distribuidora vinculada por el userId
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        if (presalesRepositoryPort.existsPresalesbyDocumentNumber(cmd.documentNumber())) {
            throw new PresalesAlreadyExistsException();
        }

        if (userRepositoryPort.existsByEmail(cmd.email()) || userRepositoryPort.existsByUsername(cmd.username())) {
            throw new UserAlreadyExistsException();
        }

        UserModel newUserPresales = UserModel.createDraft(optDistributor.get().getId(), cmd.username(),
                passwordEncoder.encode(cmd.password()),
                Set.of("PRESALES"), cmd.email(), true);

        UserModel saved = userRepositoryPort.save(newUserPresales);

        PresalesModel newPresales = PresalesModel.createDraft(optDistributor.get().getId(), saved.getId(), cmd.name(),
                cmd.phoneNumber(),
                cmd.email(), cmd.documentType(), cmd.documentNumber());

        PresalesModel result = presalesRepositoryPort.save(newPresales);

        return new RegisterPresalesCommandResult(result.getId(), result.getDistributorId(), saved.getId(),
                "Presales register succesfull!");
    }

    @Override
    public EditPresalesCommandResult editPresales(EditPresalesCommand cmd) {

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<PresalesModel> optPresales = presalesRepositoryPort.findPresalesById(optDistributor.get().getId(),
                cmd.presalesId());

        if (optPresales.isEmpty()) {
            throw new PresalesNotFoundException();
        }

        PresalesModel updatePresales = PresalesModel.createNew(optPresales.get().getId(),
                optPresales.get().getDistributorId(), optPresales.get().getUserId(), optPresales.get().getName(),
                cmd.phoneNumber(),
                optPresales.get().getEmail(), optPresales.get().getCreateAt(), Instant.now(),
                optPresales.get().getDocumentTypeEnum(),
                optPresales.get().getDocumentNumber());

        PresalesModel result = presalesRepositoryPort.save(updatePresales);

        return new EditPresalesCommandResult(result.getDistributorId(), result.getId(), "Presales update succesfull!");
    }

    @Override
    public ListPresalesCommandResult listPresales(ListPresalesCommand cmd) {
        ValidateQueryParams.validate(cmd.params());

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        PagedResult<PresalesModel> presalesModels = presalesRepositoryPort.findAllPaged(optDistributor.get().getId(),
                cmd.params().name(),
                cmd.params().page(),
                cmd.params().size(), cmd.params().sortBy(), cmd.params().direction().name());

        return new ListPresalesCommandResult(presalesModels.data(), presalesModels.meta(),
                "Presales obtain succesfull!");
    }

    @Override
    public GetPresalesByIdCommandResult showPresales(UUID userDistributorId, UUID presalesId) {

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(userDistributorId);

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<PresalesModel> optPresalesModel = presalesRepositoryPort.findPresalesById(optDistributor.get().getId(),
                presalesId);

        if (optPresalesModel.isEmpty()) {
            throw new PresalesNotFoundException();
        }

        return new GetPresalesByIdCommandResult(optPresalesModel.get(), "Presale obtain succesfull!");
    }

    @Override
    public GetPresalesInfoCommandResult getPresalesInfo(GetPresalesInfoCommand cmd) {

        Optional<PresalesModel> optPresales = presalesRepositoryPort.findPresalesByUserIdAndDistributorId(
                cmd.userPresaleId(),
                cmd.distributorId());

        if (optPresales.isEmpty()) {
            throw new PresalesNotFoundException();
        }

        return new GetPresalesInfoCommandResult(optPresales.get(), "Presale info obtain succesfull!");
    }
}
