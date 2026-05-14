package com.tecno_comfenalco.pa.application.inventory.orchestator;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
import com.tecno_comfenalco.pa.application.inventory.command.actions.CreateInventoryCommand;
import com.tecno_comfenalco.pa.application.inventory.command.actions.DeleteInventoryByIdCommand;
import com.tecno_comfenalco.pa.application.inventory.command.actions.GetAllInventoryCommand;
import com.tecno_comfenalco.pa.application.inventory.command.actions.UpdateInventoryCommand;
import com.tecno_comfenalco.pa.application.inventory.command.response.CreateInventoryCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.DeleteInventoryByIdCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.GetAllInventoryCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.GetInventoryByIdCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.UpdateInventoryCommandResult;
import com.tecno_comfenalco.pa.application.inventory.exceptions.BadInventoryStockException;
import com.tecno_comfenalco.pa.application.inventory.exceptions.InventoryNotFoundException;
import com.tecno_comfenalco.pa.application.inventory.ports.IInventoryRepositoryPort;
import com.tecno_comfenalco.pa.application.inventory.usecase.InventoryUseCase;
import com.tecno_comfenalco.pa.application.product.exceptions.ProductNotFoundException;
import com.tecno_comfenalco.pa.application.product.ports.IProductRepositoryPort;
import com.tecno_comfenalco.pa.application.warehouse.exceptions.WareHouseNotFoundException;
import com.tecno_comfenalco.pa.application.warehouse.ports.IWareHouseRepositoryPort;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.domain.warehouse.models.WareHouseModel;
import com.tecno_comfenalco.pa.shared.utils.helper.ValidateQueryParams;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

@Service
public class InventoryUseCaseImp implements InventoryUseCase {

    private final IDistributorRepositoryPort distributorRepositoryPort;
    private final IWareHouseRepositoryPort wareHouseRepositoryPort;
    private final IProductRepositoryPort productRepositoryPort;
    private final IInventoryRepositoryPort iInventoryRepositoryPort;

    public InventoryUseCaseImp(IDistributorRepositoryPort distributorRepositoryPort,
            IWareHouseRepositoryPort wareHouseRepositoryPort,
            IInventoryRepositoryPort iInventoryRepositoryPort,
            IProductRepositoryPort productRepositoryPort) {
        this.distributorRepositoryPort = distributorRepositoryPort;
        this.wareHouseRepositoryPort = wareHouseRepositoryPort;
        this.iInventoryRepositoryPort = iInventoryRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public CreateInventoryCommandResult createInventory(CreateInventoryCommand cmd) {
        // verifamos que exista la distribuidora, producto y warehouse
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<ProductModel> optProduct = productRepositoryPort.findByProductId(
                optDistributor.get().getId(),
                cmd.productId());

        if (optProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        Optional<WareHouseModel> optWareHouse = wareHouseRepositoryPort.findByIdAndDistributorId(
                cmd.warehouseId(),
                optDistributor.get().getId());

        if (optWareHouse.isEmpty()) {
            throw new WareHouseNotFoundException();
        }

        if (cmd.quantity() < 0) {
            throw new BadInventoryStockException();
        }

        InventoryModel newInventory = InventoryModel.createDraft(
                optDistributor.get().getId(),
                optProduct.get().getId(),
                optWareHouse.get().getId(),
                cmd.quantity());

        InventoryModel saved = iInventoryRepositoryPort.save(newInventory);

        return new CreateInventoryCommandResult(
                saved.getId(),
                saved.getWarehouseId(),
                saved.getDistributorId(),
                saved.getProductId(),
                "inventory created succesfull!");
    }

    @Override
    public UpdateInventoryCommandResult updateInventory(UpdateInventoryCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<InventoryModel> optInventory = iInventoryRepositoryPort.findByIdAndDistributorId(
                cmd.inventoryId(),
                optDistributor.get().getId());

        if (optInventory.isEmpty()) {
            throw new InventoryNotFoundException();
        }

        InventoryModel updateInventory = InventoryModel.createNew(
                optInventory.get().getId(),
                optInventory.get().getDistributorId(),
                optInventory.get().getProductId(),
                optInventory.get().getWarehouseId(),
                cmd.quantity());

        InventoryModel saved = iInventoryRepositoryPort.save(updateInventory);

        return new UpdateInventoryCommandResult(saved.getId(), "inventory update succesfull!");
    }

    @Override
    public GetInventoryByIdCommandResult getInventoryById(UUID userDistributorId, UUID inventoryId) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(userDistributorId);

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<InventoryModel> optInventory = iInventoryRepositoryPort.findByIdAndDistributorId(
                inventoryId,
                optDistributor.get().getId());

        if (optInventory.isEmpty()) {
            throw new InventoryNotFoundException();
        }

        return new GetInventoryByIdCommandResult(optInventory.get(), "inventory obtain succesfull!");
    }

    @Override
    public GetAllInventoryCommandResult getAllInventories(GetAllInventoryCommand cmd) {
        ValidateQueryParams.validate(cmd.params());

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        PagedResult<InventoryModel> inventories = iInventoryRepositoryPort.findAllPaged(
                optDistributor.get().getId(),
                cmd.params().page(),
                cmd.params().size(),
                cmd.params().sortBy(),
                cmd.params().direction().name());

        return new GetAllInventoryCommandResult(inventories.data(), inventories.meta(),
                "inventories obtain succesfull!");
    }

    @Override
    public DeleteInventoryByIdCommandResult deleteInventoryById(DeleteInventoryByIdCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<InventoryModel> optInventory = iInventoryRepositoryPort.findByIdAndDistributorId(
                cmd.inventoryId(),
                optDistributor.get().getId());

        if (optInventory.isEmpty()) {
            throw new InventoryNotFoundException();
        }

        iInventoryRepositoryPort.deleteByIdAndDistributorId(optInventory.get().getId(), optDistributor.get().getId());

        return new DeleteInventoryByIdCommandResult(optInventory.get().getId(), "inventory deleted succesfull!");
    }
}
