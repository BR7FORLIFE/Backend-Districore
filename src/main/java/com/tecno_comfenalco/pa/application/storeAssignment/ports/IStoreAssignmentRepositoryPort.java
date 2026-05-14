package com.tecno_comfenalco.pa.application.storeAssignment.ports;

import java.util.List;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.storeAssignment.models.StoreAssignmentModel;

public interface IStoreAssignmentRepositoryPort {
    StoreAssignmentModel save(StoreAssignmentModel model);

    List<StoreAssignmentModel> findAllByStoreId(UUID storeId);

    List<StoreAssignmentModel> findAllByDistributorId(UUID distributorId);

    boolean existsByStoreIdAndDistributorId(UUID storeId, UUID distributorId);
}
