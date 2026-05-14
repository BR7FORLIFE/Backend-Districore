package com.tecno_comfenalco.pa.application.warehouse.ports;

import java.util.Optional;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.warehouse.models.WareHouseModel;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

public interface IWareHouseRepositoryPort {
    WareHouseModel save(WareHouseModel model);

    Optional<WareHouseModel> findByIdAndDistributorId(UUID warehouseId, UUID distributorId);

    PagedResult<WareHouseModel> findAllPaged(UUID distributorId, String name, Integer page, Integer size, String sortBy,
            String direction);
}
