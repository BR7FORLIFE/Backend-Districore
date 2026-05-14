package com.tecno_comfenalco.pa.application.warehouse.orchestator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
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
import com.tecno_comfenalco.pa.application.warehouse.exceptions.WareHouseNotFoundException;
import com.tecno_comfenalco.pa.application.warehouse.ports.IWareHouseRepositoryPort;
import com.tecno_comfenalco.pa.application.warehouse.usecases.WareHouseUseCase;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.domain.warehouse.models.WareHouseModel;
import com.tecno_comfenalco.pa.shared.utils.helper.ValidateQueryParams;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

@Service
public class WareHouseUseCaseImp implements WareHouseUseCase {

    private final IWareHouseRepositoryPort wareHouseRepositoryPort;
    private final IDistributorRepositoryPort distributorRepositoryPort;

    public WareHouseUseCaseImp(
            IWareHouseRepositoryPort iWareHouseRepositoryPort,
            IDistributorRepositoryPort distributorRepositoryPort) {
        this.wareHouseRepositoryPort = iWareHouseRepositoryPort;
        this.distributorRepositoryPort = distributorRepositoryPort;
    }

    @Override
    public RegisterWareHouseCommandResult registerWarehouse(RegisterWarehouseCommand cmd) {
        // verifamos que exista la distribuidora
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        WareHouseModel newWarehouse = WareHouseModel.createDraft(
                optDistributor.get().getId(),
                cmd.name(),
                cmd.direction(),
                true);

        WareHouseModel result = wareHouseRepositoryPort.save(newWarehouse);

        return new RegisterWareHouseCommandResult(result.getId(), result.getDistributorId(),
                "warehouse register succesfull!");
    }

    @Override
    public GetWareHouseByIdCommandResult getWarehouseById(GetWareHouseByIdCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<WareHouseModel> optWareHouse = wareHouseRepositoryPort.findByIdAndDistributorId(
                cmd.warehouseId(),
                optDistributor.get().getId());

        if (optWareHouse.isEmpty()) {
            throw new WareHouseNotFoundException();
        }

        return new GetWareHouseByIdCommandResult(optWareHouse.get(), "warehouse obtain succesfull!");
    }

    @Override
    public GetAllWareHouseCommandResult getAllWarehouses(GetAllWarehousesCommand cmd) {
        // validamos los query params
        ValidateQueryParams.validate(cmd.params());

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        PagedResult<WareHouseModel> wareHouses = wareHouseRepositoryPort.findAllPaged(
                optDistributor.get().getId(),
                cmd.params().name(),
                cmd.params().page(),
                cmd.params().size(),
                cmd.params().sortBy(),
                cmd.params().direction().name());

        return new GetAllWareHouseCommandResult(wareHouses.data(), wareHouses.meta(), "warehouses obtain succesfull!");
    }

    @Override
    public UpdateWareHouseCommandResult updateWareHouse(UpdateWareHouseCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<WareHouseModel> optWarehouse = wareHouseRepositoryPort.findByIdAndDistributorId(
                cmd.warehouseId(),
                optDistributor.get().getId());

        if (optWarehouse.isEmpty()) {
            throw new WareHouseNotFoundException();
        }

        WareHouseModel updateWarehouse = WareHouseModel.createNew(
                optWarehouse.get().getId(),
                optWarehouse.get().getDistributorId(),
                cmd.name(),
                cmd.direction(),
                optWarehouse.get().isActive());

        WareHouseModel saved = wareHouseRepositoryPort.save(updateWarehouse);

        return new UpdateWareHouseCommandResult(saved.getDistributorId(), saved.getId(),
                "warehouse update succesfull!");
    }

    @Override
    public DeleteLogicWareHouseCommandResult disabledWareHouse(DeleteLogicWareHouseCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<WareHouseModel> optWarehouse = wareHouseRepositoryPort.findByIdAndDistributorId(
                cmd.warehouseId(),
                optDistributor.get().getId());

        if (optWarehouse.isEmpty()) {
            throw new WareHouseNotFoundException();
        }

        WareHouseModel deleteLogic = WareHouseModel.createNew(
                optWarehouse.get().getId(),
                optWarehouse.get().getDistributorId(),
                optWarehouse.get().getName(),
                optWarehouse.get().getDirectionDto(),
                false);

        WareHouseModel saved = wareHouseRepositoryPort.save(deleteLogic);

        return new DeleteLogicWareHouseCommandResult(saved.getDistributorId(), saved.getId(),
                "Warehouse desactive succesfull!");
    }
}
