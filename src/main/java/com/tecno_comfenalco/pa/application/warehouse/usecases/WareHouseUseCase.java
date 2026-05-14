package com.tecno_comfenalco.pa.application.warehouse.usecases;

import com.tecno_comfenalco.pa.application.warehouse.command.actions.DeleteLogicWareHouseCommand;
import com.tecno_comfenalco.pa.application.warehouse.command.actions.GetAllWarehousesCommand;
import com.tecno_comfenalco.pa.application.warehouse.command.actions.GetWareHouseByIdCommand;
import com.tecno_comfenalco.pa.application.warehouse.command.actions.RegisterWarehouseCommand;
import com.tecno_comfenalco.pa.application.warehouse.command.actions.UpdateWareHouseCommand;
import com.tecno_comfenalco.pa.application.warehouse.command.response.DeleteLogicWareHouseCommandResult;
import com.tecno_comfenalco.pa.application.warehouse.command.response.GetAllWareHouseCommandResult;
import com.tecno_comfenalco.pa.application.warehouse.command.response.GetWareHouseByIdCommandResult;
import com.tecno_comfenalco.pa.application.warehouse.command.response.RegisterWareHouseCommandResult;
import com.tecno_comfenalco.pa.application.warehouse.command.response.UpdateWareHouseCommandResult;

public interface WareHouseUseCase {
    RegisterWareHouseCommandResult registerWarehouse(RegisterWarehouseCommand cmd);

    GetWareHouseByIdCommandResult getWarehouseById(GetWareHouseByIdCommand cmd);

    GetAllWareHouseCommandResult getAllWarehouses(GetAllWarehousesCommand cmd);

    UpdateWareHouseCommandResult updateWareHouse(UpdateWareHouseCommand cmd);

    DeleteLogicWareHouseCommandResult disabledWareHouse(DeleteLogicWareHouseCommand cmd);
}
