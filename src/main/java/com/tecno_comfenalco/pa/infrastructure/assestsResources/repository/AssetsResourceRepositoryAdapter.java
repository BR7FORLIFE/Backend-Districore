package com.tecno_comfenalco.pa.infrastructure.assestsResources.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.assetsResources.ports.IAssetsResourcesRepositoryPort;
import com.tecno_comfenalco.pa.domain.assestsResources.model.AssestsResourceModel;
import com.tecno_comfenalco.pa.infrastructure.assestsResources.entity.AssestsResourceDocument;
import com.tecno_comfenalco.pa.infrastructure.assestsResources.mapper.AssestsResourceMapper;
import com.tecno_comfenalco.pa.infrastructure.assestsResources.repository.mongo.AssetsResourceRepository;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

@Repository
public class AssetsResourceRepositoryAdapter implements IAssetsResourcesRepositoryPort {

    private final AssetsResourceRepository assetsResourceRepository;

    public AssetsResourceRepositoryAdapter(AssetsResourceRepository assetsResourceRepository) {
        this.assetsResourceRepository = assetsResourceRepository;
    }

    @Override
    public Optional<AssestsResourceModel> findByIdAndDistributorId(UUID assetId, UUID distributorId) {
        return assetsResourceRepository.findByIdAndDistributorId(assetId, distributorId)
                .map(AssestsResourceMapper::toDomain);
    }

    @Override
    public Optional<AssestsResourceModel> findByProductIdAndDistributorId(UUID productId, UUID distributorId) {
        return assetsResourceRepository.findByProductIdAndDistributorId(productId, distributorId)
                .map(AssestsResourceMapper::toDomain);
    }

    @Override
    public boolean existsByProductIdAndDistributorId(UUID productId, UUID distributorId) {
        return assetsResourceRepository.existsByProductIdAndDistributorId(productId, distributorId);
    }

    @Override
    public boolean existsByIdAndDistributorId(UUID assetId, UUID distributorId) {
        return assetsResourceRepository.existsByIdAndDistributorId(assetId, distributorId);
    }

    @Override
    public AssestsResourceModel save(AssestsResourceModel model) {
        AssestsResourceDocument document = AssestsResourceMapper.toEntity(model);
        AssestsResourceDocument saved = assetsResourceRepository.save(document);

        return AssestsResourceMapper.toDomain(saved);
    }

    @Override
    public void deleteAssetByIdAndDistributorId(UUID assetId, UUID distributorId) {
        assetsResourceRepository.deleteByIdAndDistributorId(assetId, distributorId);
    }

    @Override
    public PagedResult<AssestsResourceModel> findAllPaged(UUID distributorId, String name, Integer page, Integer size,
            String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<AssestsResourceDocument> result;

        if (name != null && !name.isBlank()) {
            result = assetsResourceRepository.findByDistributorIdAndNameContainingIgnoreCase(distributorId, name,
                    pageable);

        } else {
            result = assetsResourceRepository.findByDistributorId(distributorId, pageable);
        }

        List<AssestsResourceModel> models = result.getContent()
                .stream()
                .map(AssestsResourceMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(), result.getTotalElements(),
                result.getTotalPages(), result.hasNext());

        return new PagedResult<AssestsResourceModel>(models, meta);
    }
}
