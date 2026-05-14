package com.tecno_comfenalco.pa.application.inventory.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

public interface IInventoryRepositoryPort {
    InventoryModel save(InventoryModel model);

    Optional<InventoryModel> findByIdAndDistributorId(UUID inventoryId, UUID distributorId);

    PagedResult<InventoryModel> findAllPaged(UUID distributorId, Integer page, Integer size, String sortBy,
            String direction);

    void deleteByIdAndDistributorId(UUID inventoryId, UUID distributorId);

    List<InventoryModel> findByDistributorIdAndProductIdIn(UUID distributorId, List<UUID> productsIds);
}
