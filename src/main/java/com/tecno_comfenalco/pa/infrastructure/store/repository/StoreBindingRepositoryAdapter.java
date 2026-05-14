package com.tecno_comfenalco.pa.infrastructure.store.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.store.ports.IStoreBindingRepositoryPort;
import com.tecno_comfenalco.pa.domain.store.models.StoreBindingRequestModel;
import com.tecno_comfenalco.pa.infrastructure.store.entity.StoreBindingRequestDocument;
import com.tecno_comfenalco.pa.infrastructure.store.mapper.StoreBindingMapper;
import com.tecno_comfenalco.pa.infrastructure.store.repository.mongo.StoreBindingRepository;
import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

@Repository
public class StoreBindingRepositoryAdapter implements IStoreBindingRepositoryPort {

    private final StoreBindingRepository storeBindingRepository;

    public StoreBindingRepositoryAdapter(StoreBindingRepository storeBindingRepository) {
        this.storeBindingRepository = storeBindingRepository;
    }

    @Override
    public boolean existsStoreBindingByNitAndDistributorId(String nit, UUID distributorId) {
        return storeBindingRepository.existsByNitAndDistributorId(nit, distributorId);
    }

    @Override
    public StoreBindingRequestModel save(StoreBindingRequestModel model) {
        StoreBindingRequestDocument document = StoreBindingMapper.toEntity(model);
        StoreBindingRequestDocument saved = storeBindingRepository.save(document);

        return StoreBindingMapper.toDomain(saved);
    }

    @Override
    public PagedResult<StoreBindingRequestModel> findAllPaged(UUID distributorId, String name, Integer page,
            Integer size, String sortBy, String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<StoreBindingRequestDocument> result;

        if (name != null && !name.isBlank()) {
            result = storeBindingRepository.findByDistributorIdAndTempNameContainingIgnoreCase(distributorId, name,
                    pageable);

        } else {
            result = storeBindingRepository.findByDistributorId(distributorId, pageable);
        }

        List<StoreBindingRequestModel> models = result.getContent()
                .stream()
                .map(StoreBindingMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(), result.getTotalElements(),
                result.getTotalPages(), result.hasNext());

        return new PagedResult<StoreBindingRequestModel>(models, meta);
    }

    @Override
    public Optional<StoreBindingRequestModel> findByIdAndDistributorId(UUID bindingId, UUID distributorId) {
        return storeBindingRepository.findByIdAndDistributorId(bindingId, distributorId)
                .map(StoreBindingMapper::toDomain);
    }

    @Override
    public Optional<StoreBindingRequestModel> findByNitAndToken(String nit, String token) {
        return storeBindingRepository.findByNitAndActivationCode(nit, token)
                .map(StoreBindingMapper::toDomain);
    }

    @Override
    public List<StoreBindingRequestModel> findByNitAndDistributorIdAndBindingStatusAndIsConsumedTrue(
            String nit, UUID distributorId, BindingStatusEnum statusEnum) {
        return storeBindingRepository
                .findByNitAndDistributorIdAndBindingStatusAndIsConsumedTrue(nit, distributorId, statusEnum)
                .stream()
                .map(StoreBindingMapper::toDomain)
                .collect(Collectors.toList());
    }
}
