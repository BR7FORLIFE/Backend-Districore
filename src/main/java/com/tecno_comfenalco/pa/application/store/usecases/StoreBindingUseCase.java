package com.tecno_comfenalco.pa.application.store.usecases;

import com.tecno_comfenalco.pa.application.store.command.storeBinding.actions.ChangeStatusBindingCommand;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.actions.ListAllBindingCommand;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.actions.ReceiveAceptationByStoreCommand;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.actions.SendBindingCommand;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.response.ChangeStatusBindingCommandResult;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.response.ListAllBindingCommandResult;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.response.ReceiveAceptationByStoreCommandResult;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.response.SendBindingCommandResult;

public interface StoreBindingUseCase {

    SendBindingCommandResult sendBindingStoreRequest(SendBindingCommand cmd);

    ListAllBindingCommandResult listAllBindings(ListAllBindingCommand cmd);

    ChangeStatusBindingCommandResult changeStatusBindingByDistributor(ChangeStatusBindingCommand cmd);

    ReceiveAceptationByStoreCommandResult receiveAceptationByStore(ReceiveAceptationByStoreCommand cmd);
}
