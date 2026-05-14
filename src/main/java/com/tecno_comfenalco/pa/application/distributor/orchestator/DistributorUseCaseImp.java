package com.tecno_comfenalco.pa.application.distributor.orchestator;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecno_comfenalco.pa.application.auth.Exceptions.UserNotFoundException;
import com.tecno_comfenalco.pa.application.auth.ports.IUserRepositoryPort;
import com.tecno_comfenalco.pa.application.distributor.command.actions.EditDistributorCommand;
import com.tecno_comfenalco.pa.application.distributor.command.actions.ListAllDistributorsCommand;
import com.tecno_comfenalco.pa.application.distributor.command.actions.RegisterDistributorCommand;
import com.tecno_comfenalco.pa.application.distributor.command.response.EditDistributorCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.GetDistributorByIdCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.GetDistributorByNITCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.ListAllDistributorsCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.RegisterDistributorCommandResult;
import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorAlreadyExists;
import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
import com.tecno_comfenalco.pa.application.distributor.usecase.DistributorUseCase;
import com.tecno_comfenalco.pa.domain.auth.models.UserModel;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.shared.utils.helper.ValidateQueryParams;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

@Service
public class DistributorUseCaseImp implements DistributorUseCase {

    private final IDistributorRepositoryPort distributorRepositoryPort;
    private final IUserRepositoryPort userRepositoryPort;

    public DistributorUseCaseImp(IDistributorRepositoryPort iDistributorRepositoryPort,
            IUserRepositoryPort userRepositoryPort) {
        this.distributorRepositoryPort = iDistributorRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Transactional
    @Override
    public RegisterDistributorCommandResult registerDistributor(RegisterDistributorCommand cmd) {

        // validamos que no exista un NIT asociadd
        if (distributorRepositoryPort.existsDistributorByNit(cmd.nit())) {
            throw new DistributorAlreadyExists();
        }

        // validamos que no exista un email asociado
        if (distributorRepositoryPort.existsDistributorByEmail(cmd.email())
                || distributorRepositoryPort.existsDistributorByNit(cmd.nit())) {
            throw new DistributorAlreadyExists();
        }

        Optional<UserModel> optUser = userRepositoryPort.findByUserId(cmd.userId());

        if (optUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        DistributorModel newDistributorModel = DistributorModel.createDraft(optUser.get().getId(), cmd.nit(),
                cmd.name(), cmd.phoneNumber(), cmd.email(), cmd.directionDto());

        DistributorModel result = distributorRepositoryPort.save(newDistributorModel);

        UserModel changeRolForUser = UserModel.createNew(optUser.get().getId(), result.getId(),
                optUser.get().getUsername(), optUser.get().getPassword(), Set.of("DISTRIBUTOR"),
                optUser.get().getEmail(), optUser.get().isEnabled());

        UserModel saved = userRepositoryPort.save(changeRolForUser);

        return new RegisterDistributorCommandResult(result.getId(), saved.getId(),
                "Distributor created succesfull!");
    }

    @Override
    public ListAllDistributorsCommandResult listAllDistributors(ListAllDistributorsCommand cmd) {
        // Validar los queryparams
        ValidateQueryParams.validate(cmd.params());

        PagedResult<DistributorModel> distributorModels = distributorRepositoryPort.findAllPaged(cmd.params().name(),
                cmd.params().page(),
                cmd.params().size(),
                cmd.params().sortBy(), cmd.params().direction().name());

        return new ListAllDistributorsCommandResult(distributorModels.data(), distributorModels.meta(),
                "Distributors obtain succesfull!");
    }

    @Override
    public GetDistributorByIdCommandResult getDistributorById(UUID distributorId) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findById(distributorId);

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        return new GetDistributorByIdCommandResult(optDistributor.get(), "distributor obtain succesfull");
    }

    @Override
    public GetDistributorByNITCommandResult getDistributorByNIT(String NIT) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByNIT(NIT);

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        return new GetDistributorByNITCommandResult(optDistributor.get(), "distributor obtain succesfull!");
    }

    @Override
    public EditDistributorCommandResult editDistributor(EditDistributorCommand cmd) {

        Optional<DistributorModel> optDistributorModel = distributorRepositoryPort.findById(cmd.distributorId());

        if (optDistributorModel.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        DistributorModel updateDistributor = DistributorModel.createNew(cmd.distributorId(),
                optDistributorModel.get().getUserId(), optDistributorModel.get().getNit(), cmd.name(),
                cmd.phoneNumber(), optDistributorModel.get().getEmail(), optDistributorModel.get().getCreateAt(),
                Instant.now(), cmd.direction());

        distributorRepositoryPort.save(updateDistributor);

        return new EditDistributorCommandResult("Distributor update succesfull!");
    }
}
