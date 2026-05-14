package com.tecno_comfenalco.pa.application.store.usecases;

import java.util.UUID;

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

public interface StoreUseCase {
    RegisterStoreCommandResult registerStore(RegisterStoreCommand cmd);

    ListAllStoresCommandResult listAllStores(ListAllStoresCommand cmd);

    GetStoreByIdCommandResult getStoreById(UUID storeId);

    UpdateStoreCommandResult updateStore(UpdateStoreCommand cmd);

    DisabledStoreCommandResult disabledStore(UUID id);

    GetMyCatalogCommandResult getMyCatalog(GetMyCatalogCommand cmd);

    GetAllCatalogByDistributorCommandResult getAllCatalogByDistributor(GetAllCatalogByDistributorCommand cmd);

    MeStoreCommandResult me(UUID userStoreId);
}
