package com.tecno_comfenalco.pa.application.delivery.orchestator;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecno_comfenalco.pa.application.auth.Exceptions.UserAlreadyExistsException;
import com.tecno_comfenalco.pa.application.auth.ports.IUserRepositoryPort;
import com.tecno_comfenalco.pa.application.delivery.command.actions.GetDeliveryByIdCommand;
import com.tecno_comfenalco.pa.application.delivery.command.actions.ListAllDeliveryCommand;
import com.tecno_comfenalco.pa.application.delivery.command.actions.RegisterDeliveryCommand;
import com.tecno_comfenalco.pa.application.delivery.command.actions.UpdateDeliveryCommand;
import com.tecno_comfenalco.pa.application.delivery.command.response.GetDeliveryByIdCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.ListAllDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.MeDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.RegisterDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.UpdateDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.exceptions.DeliveryAlreadyExistsException;
import com.tecno_comfenalco.pa.application.delivery.exceptions.DeliveryNotFoundException;
import com.tecno_comfenalco.pa.application.delivery.ports.IDeliveryRepositoryPort;
import com.tecno_comfenalco.pa.application.delivery.usecases.DeliveryUseCase;
import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
import com.tecno_comfenalco.pa.domain.auth.models.UserModel;
import com.tecno_comfenalco.pa.domain.delivery.model.DeliveryModel;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.domain.vehicle.model.VehicleSummaryModel;
import com.tecno_comfenalco.pa.shared.utils.helper.ValidateQueryParams;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

@Service
public class DeliveryUseCaseImp implements DeliveryUseCase {

    private final IDistributorRepositoryPort distributorRepositoryPort;
    private final IDeliveryRepositoryPort deliveryRepositoryPort;
    private final IUserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public DeliveryUseCaseImp(IDistributorRepositoryPort distributorRepositoryPort,
            IDeliveryRepositoryPort iDeliveryRepositoryPort, IUserRepositoryPort userRepositoryPort,
            PasswordEncoder passwordEncoder) {
        this.deliveryRepositoryPort = iDeliveryRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.distributorRepositoryPort = distributorRepositoryPort;
    }

    @Transactional
    @Override
    public RegisterDeliveryCommandResult registerDelivery(RegisterDeliveryCommand cmd) {

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        if (deliveryRepositoryPort.existsByDocumentNumber(cmd.documentNumber())) {
            throw new DeliveryAlreadyExistsException();
        }

        if (userRepositoryPort.existsByEmail(cmd.email()) || userRepositoryPort.existsByUsername(cmd.username())) {
            throw new UserAlreadyExistsException();
        }

        List<VehicleSummaryModel> vehicles = cmd.vehicles() == null
                ? List.of()
                : cmd.vehicles();

        UserModel changeRolForUser = UserModel.createDraft(optDistributor.get().getId(), cmd.username(),
                passwordEncoder.encode(cmd.password()), Set.of("DELIVERY"), cmd.email(), true);

        UserModel saved = userRepositoryPort.save(changeRolForUser);

        DeliveryModel newDelivery = DeliveryModel.createDraft(optDistributor.get().getId(), saved.getId(), cmd.name(),
                cmd.email(), cmd.documentTypeEnum(),
                cmd.documentNumber(), cmd.phoneNumber(), cmd.licenseNumber(), cmd.licenseTypeEnum(), vehicles);

        DeliveryModel savedDelivery = deliveryRepositoryPort.save(newDelivery);

        return new RegisterDeliveryCommandResult(savedDelivery.getDistributorId(), savedDelivery.getId(), saved.getId(),
                "Delivery register succesfull!");
    }

    @Override
    public ListAllDeliveryCommandResult listAllDeliveries(ListAllDeliveryCommand cmd) {
        ValidateQueryParams.validate(cmd.params());

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        PagedResult<DeliveryModel> deliveries = deliveryRepositoryPort.findAllPaged(optDistributor.get().getId(),
                cmd.params().name(),
                cmd.params().page(), cmd.params().size(), cmd.params().sortBy(), cmd.params().direction().name());

        return new ListAllDeliveryCommandResult(deliveries.data(), deliveries.meta(), "Deliveries obtain succesfull!");
    }

    @Override
    public GetDeliveryByIdCommandResult getDeliveryById(GetDeliveryByIdCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<DeliveryModel> optDelivery = deliveryRepositoryPort.findByIdAndDistributorId(cmd.deliveryId(),
                optDistributor.get().getId());

        if (optDelivery.isEmpty()) {
            throw new DeliveryNotFoundException();
        }

        return new GetDeliveryByIdCommandResult(optDelivery.get(), "Delivery obtain succesfull!");
    }

    @Override
    public UpdateDeliveryCommandResult updateDelivery(UpdateDeliveryCommand cmd) {

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<DeliveryModel> optDelivery = deliveryRepositoryPort.findByIdAndDistributorId(cmd.deliveryId(),
                optDistributor.get().getId());

        if (optDelivery.isEmpty()) {
            throw new DeliveryNotFoundException();
        }

        DeliveryModel updateDelivery = DeliveryModel.createNew(optDelivery.get().getId(),
                optDelivery.get().getDistributorId(), optDelivery.get().getUserId(), cmd.name(),
                optDelivery.get().getEmail(),
                optDelivery.get().getDocumentType(),
                optDelivery.get().getDocumentNumber(), cmd.phoneNumber(), optDelivery.get().getLicenseNumber(),
                cmd.licenseTypeEnum(), cmd.vehicles(), optDelivery.get().getCreateAt(), Instant.now());

        DeliveryModel result = deliveryRepositoryPort.save(updateDelivery);

        return new UpdateDeliveryCommandResult(result.getId(), "delivery update succesfull!");
    }

    @Override
    public MeDeliveryCommandResult me(UUID userDeliveryId, UUID distributorId) {
        Optional<DeliveryModel> optDelivery = deliveryRepositoryPort.findByUserIdAndDistributorId(
                userDeliveryId,
                distributorId);

        if (optDelivery.isEmpty()) {
            throw new DeliveryNotFoundException();
        }

        return new MeDeliveryCommandResult(optDelivery.get(), "delivery obtain succesfull!");
    }
}
