package com.tecno_comfenalco.pa.application.store.orchestator;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.auth.Exceptions.UserNotFoundException;
import com.tecno_comfenalco.pa.application.auth.ports.IUserRepositoryPort;
import com.tecno_comfenalco.pa.application.catalog.exceptions.CatalogNotFoundException;
import com.tecno_comfenalco.pa.application.catalog.port.ICatalogRepositoryPort;
import com.tecno_comfenalco.pa.application.store.command.actions.GetAllCatalogByDistributorCommand;
import com.tecno_comfenalco.pa.application.store.command.actions.GetMyCatalogCommand;
import com.tecno_comfenalco.pa.application.store.command.actions.ListAllStoresCommand;
import com.tecno_comfenalco.pa.application.store.command.actions.RegisterStoreCommand;
import com.tecno_comfenalco.pa.application.store.command.actions.UpdateStoreCommand;
import com.tecno_comfenalco.pa.application.store.command.response.DisabledStoreCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.GetAllCatalogByDistributorCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.GetMyCatalogCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.GetStoreByIdCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.ListAllStoresCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.MeStoreCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.RegisterStoreCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.UpdateStoreCommandResult;
import com.tecno_comfenalco.pa.application.store.exceptions.StoreAlreadyExistsException;
import com.tecno_comfenalco.pa.application.store.exceptions.StoreNotFoundException;
import com.tecno_comfenalco.pa.application.store.ports.IStoreRepositoryPort;
import com.tecno_comfenalco.pa.application.store.usecases.StoreUseCase;
import com.tecno_comfenalco.pa.application.storeAssignment.exceptions.NoStoreAssigmentNotFoundException;
import com.tecno_comfenalco.pa.application.storeAssignment.ports.IStoreAssignmentRepositoryPort;
import com.tecno_comfenalco.pa.domain.auth.models.UserModel;
import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;
import com.tecno_comfenalco.pa.domain.store.models.StoreModel;
import com.tecno_comfenalco.pa.shared.utils.helper.ValidateQueryParams;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

@Service
public class StoreUseCaseImp implements StoreUseCase {

    private final IStoreRepositoryPort storeRepositoryPort;
    private final IUserRepositoryPort userRepositoryPort;
    private final IStoreAssignmentRepositoryPort storeAssignmentRepositoryPort;
    private final ICatalogRepositoryPort catalogRepositoryPort;

    public StoreUseCaseImp(IStoreRepositoryPort storeRepositoryPort,
            IUserRepositoryPort userRepositoryPort,
            IStoreAssignmentRepositoryPort storeAssignmentRepositoryPort,
            ICatalogRepositoryPort catalogRepositoryPort) {
        this.storeRepositoryPort = storeRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.storeAssignmentRepositoryPort = storeAssignmentRepositoryPort;
        this.catalogRepositoryPort = catalogRepositoryPort;
    }

    @Override
    public RegisterStoreCommandResult registerStore(RegisterStoreCommand cmd) {
        // verificamos que no exista la tienda
        if (storeRepositoryPort.existsStoreByNit(cmd.nit())) {
            throw new StoreAlreadyExistsException();
        }

        Optional<UserModel> optUserModel = userRepositoryPort.findByUserId(cmd.userId());

        if (optUserModel.isEmpty()) {
            throw new UserNotFoundException();
        }

        StoreModel newStore = StoreModel.createDraft(cmd.userId(), cmd.name(), cmd.nit(), cmd.phoneNumber(),
                cmd.email(), cmd.direction(), Instant.now(), Instant.now());

        StoreModel saved = storeRepositoryPort.save(newStore);

        UserModel changeRolForUser = UserModel.createNew(saved.getUserId(), null, optUserModel.get().getUsername(),
                optUserModel.get().getPassword(), Set.of("STORE"), optUserModel.get().getEmail(),
                optUserModel.get().isEnabled());

        UserModel savedUser = userRepositoryPort.save(changeRolForUser);

        return new RegisterStoreCommandResult(saved.getId(), savedUser.getId(), "Store registered succesfull!");
    }

    @Override
    public ListAllStoresCommandResult listAllStores(ListAllStoresCommand cmd) {
        // validamos los query params
        ValidateQueryParams.validate(cmd.params());

        PagedResult<StoreModel> storesModels = storeRepositoryPort.findAllpaged(cmd.params().name(),
                cmd.params().page(),
                cmd.params().size(), cmd.params().sortBy(), cmd.params().direction().name());

        return new ListAllStoresCommandResult(storesModels.data(), storesModels.meta(), "stores obtain succesfull!");
    }

    @Override
    public GetStoreByIdCommandResult getStoreById(UUID storeId) {
        Optional<StoreModel> optStore = storeRepositoryPort.findById(storeId);

        if (optStore.isEmpty()) {
            throw new StoreNotFoundException();
        }

        return new GetStoreByIdCommandResult(optStore.get(), "Store obtain succesfull!");
    }

    @Override
    public UpdateStoreCommandResult updateStore(UpdateStoreCommand cmd) {

        Optional<StoreModel> optStore = storeRepositoryPort.findById(cmd.storeId());

        if (optStore.isEmpty()) {
            throw new StoreNotFoundException();
        }

        StoreModel updateStore = StoreModel.createNew(
                optStore.get().getId(),
                optStore.get().getUserId(),
                cmd.name(),
                optStore.get().getNit(),
                cmd.phoneNumber(),
                optStore.get().getEmail(),
                cmd.direction(),
                optStore.get().getCreateAt(),
                Instant.now());

        StoreModel saved = storeRepositoryPort.save(updateStore);

        return new UpdateStoreCommandResult(saved.getId(), "store update succesfull!");
    }

    @Override
    public DisabledStoreCommandResult disabledStore(UUID id) {

        Optional<StoreModel> optStore = storeRepositoryPort.findById(id);

        // validamos que exista la tienda
        if (optStore.isEmpty()) {
            throw new StoreNotFoundException();
        }

        Optional<UserModel> updateRolByStore = userRepositoryPort.findByUserId(optStore.get().getUserId());

        UserModel updateUser = UserModel.createNew(
                updateRolByStore.get().getId(),
                null,
                updateRolByStore.get().getUsername(),
                updateRolByStore.get().getPassword(),
                Set.of("USER"),
                updateRolByStore.get().getEmail(),
                updateRolByStore.get().isEnabled());

        storeRepositoryPort.deleteStoreById(id); // borramos la tienda vinculado al usuario
        userRepositoryPort.save(updateUser); // guardamos el usuario actualizaco con rol USER

        return new DisabledStoreCommandResult(updateRolByStore.get().getId(), "store disabled succesfull!");
    }

    @Override
    public GetMyCatalogCommandResult getMyCatalog(GetMyCatalogCommand cmd) {
        Optional<StoreModel> optStore = storeRepositoryPort.findByUserId(cmd.userStoreId());

        if (optStore.isEmpty()) {
            throw new StoreNotFoundException();
        }

        boolean existsAssigment = storeAssignmentRepositoryPort
                .existsByStoreIdAndDistributorId(optStore.get().getId(), cmd.distributorId());

        if (!existsAssigment) {
            throw new NoStoreAssigmentNotFoundException();
        }

        Optional<CatalogModel> optCatalog = catalogRepositoryPort.findByCatalogIdAndDistributorId(
                cmd.catalogId(),
                cmd.distributorId());

        if (optCatalog.isEmpty()) {
            throw new CatalogNotFoundException();
        }

        return new GetMyCatalogCommandResult(optCatalog.get(), "catalog obtain succesfull!");
    }

    @Override
    public GetAllCatalogByDistributorCommandResult getAllCatalogByDistributor(GetAllCatalogByDistributorCommand cmd) {
        ValidateQueryParams.validate(cmd.params());

        Optional<StoreModel> optStore = storeRepositoryPort.findByUserId(cmd.userStoreId());

        if (optStore.isEmpty()) {
            throw new StoreNotFoundException();
        }

        boolean existsAssignment = storeAssignmentRepositoryPort.existsByStoreIdAndDistributorId(
                optStore.get().getId(),
                cmd.distributorId());

        if (!existsAssignment) {
            throw new NoStoreAssigmentNotFoundException();
        }

        PagedResult<CatalogModel> catalogs = catalogRepositoryPort.findAllPaged(
                cmd.distributorId(),
                cmd.params().name(),
                cmd.params().page(),
                cmd.params().size(),
                cmd.params().sortBy(),
                cmd.params().direction().name());

        return new GetAllCatalogByDistributorCommandResult(catalogs.data(), catalogs.meta(),
                "catalogs obtain succesfull!");
    }

    @Override
    public MeStoreCommandResult me(UUID userStoreId) {
        Optional<StoreModel> optStore = storeRepositoryPort.findByUserId(userStoreId);

        if (optStore.isEmpty()) {
            throw new StoreNotFoundException();
        }

        return new MeStoreCommandResult(optStore.get(), "store obtain succesfull!");
    }
}
