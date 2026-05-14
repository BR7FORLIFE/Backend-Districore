package com.tecno_comfenalco.pa.application.assetsResources.ports;

import java.util.Optional;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.assestsResources.model.AssestsResourceModel;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

public interface IAssetsResourcesRepositoryPort {

    Optional<AssestsResourceModel> findByIdAndDistributorId(UUID assetId, UUID distributorId);

    Optional<AssestsResourceModel> findByProductIdAndDistributorId(UUID productId, UUID distributorId);

    boolean existsByProductIdAndDistributorId(UUID productId, UUID distributorId);

    boolean existsByIdAndDistributorId(UUID assetId, UUID distributorId);

    AssestsResourceModel save(AssestsResourceModel model);

    PagedResult<AssestsResourceModel> findAllPaged(UUID distributorId, String name, Integer page, Integer size,
            String sortBy,
            String direction);

    void deleteAssetByIdAndDistributorId(UUID assetId, UUID distributorId);
}
