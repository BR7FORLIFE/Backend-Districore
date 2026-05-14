package com.tecno_comfenalco.pa.application.inventory.usecase;

import java.util.UUID;

import com.tecno_comfenalco.pa.application.inventory.command.actions.CreateInventoryCommand;
import com.tecno_comfenalco.pa.application.inventory.command.actions.DeleteInventoryByIdCommand;
import com.tecno_comfenalco.pa.application.inventory.command.actions.GetAllInventoryCommand;
import com.tecno_comfenalco.pa.application.inventory.command.actions.UpdateInventoryCommand;
import com.tecno_comfenalco.pa.application.inventory.command.response.CreateInventoryCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.DeleteInventoryByIdCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.GetAllInventoryCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.GetInventoryByIdCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.UpdateInventoryCommandResult;

public interface InventoryUseCase {
    CreateInventoryCommandResult createInventory(CreateInventoryCommand cmd);

    UpdateInventoryCommandResult updateInventory(UpdateInventoryCommand cmd);

    GetInventoryByIdCommandResult getInventoryById(UUID userDistributorId, UUID inventoryId);

    GetAllInventoryCommandResult getAllInventories(GetAllInventoryCommand cmd);

    DeleteInventoryByIdCommandResult deleteInventoryById(DeleteInventoryByIdCommand cmd);
}
