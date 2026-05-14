package com.tecno_comfenalco.pa.infrastructure.storeAssignment.repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.storeAssignment.ports.IStoreAssignmentRepositoryPort;
import com.tecno_comfenalco.pa.domain.storeAssignment.models.StoreAssignmentModel;
import com.tecno_comfenalco.pa.infrastructure.storeAssignment.entity.StoreAssignmentDocument;
import com.tecno_comfenalco.pa.infrastructure.storeAssignment.mapper.StoreAssignmentMapper;
import com.tecno_comfenalco.pa.infrastructure.storeAssignment.repository.mongo.StoreAssignmentRepository;

@Repository
public class StoreAssignmentRepositoryAdapter implements IStoreAssignmentRepositoryPort {

    private final StoreAssignmentRepository storeAssignmentRepository;

    public StoreAssignmentRepositoryAdapter(StoreAssignmentRepository repository) {
        this.storeAssignmentRepository = repository;
    }

    @Override
    public StoreAssignmentModel save(StoreAssignmentModel model) {
        StoreAssignmentDocument document = StoreAssignmentMapper.toEntity(model);
        StoreAssignmentDocument saved = storeAssignmentRepository.save(document);

        return StoreAssignmentMapper.toDomain(saved);
    }

    @Override
    public List<StoreAssignmentModel> findAllByStoreId(UUID storeId) {
        return storeAssignmentRepository.findAllByStoreId(storeId)
                .stream()
                .map(StoreAssignmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreAssignmentModel> findAllByDistributorId(UUID distributorId) {
        return storeAssignmentRepository.findAllByDistributorId(distributorId)
                .stream()
                .map(StoreAssignmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByStoreIdAndDistributorId(UUID storeId, UUID distributorId) {
        return storeAssignmentRepository.existsByStoreIdAndDistributorId(storeId, distributorId);
    }
}
