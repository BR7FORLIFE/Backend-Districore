package com.tecno_comfenalco.pa.infrastructure.inventory.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.inventory.ports.IInventoryRepositoryPort;
import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;
import com.tecno_comfenalco.pa.infrastructure.inventory.entity.InventoryDocument;
import com.tecno_comfenalco.pa.infrastructure.inventory.mapper.InventoryMapper;
import com.tecno_comfenalco.pa.infrastructure.inventory.repository.mongo.InventoryRepository;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

@Repository
public class InventoryRepositoryAdapter implements IInventoryRepositoryPort {

    private final InventoryRepository inventoryRepository;

    public InventoryRepositoryAdapter(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryModel save(InventoryModel model) {
        InventoryDocument document = InventoryMapper.toEntity(model);
        InventoryDocument saved = inventoryRepository.save(document);

        return InventoryMapper.toDomain(saved);
    }

    @Override
    public Optional<InventoryModel> findByIdAndDistributorId(UUID inventoryId, UUID distributorId) {
        return inventoryRepository.findByIdAndDistributorId(inventoryId, distributorId)
                .map(InventoryMapper::toDomain);
    }

    @Override
    public PagedResult<InventoryModel> findAllPaged(UUID distributorId, Integer page, Integer size,
            String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<InventoryDocument> result;

        result = inventoryRepository.findByDistributorId(distributorId, pageable);

        List<InventoryModel> models = result.getContent()
                .stream()
                .map(InventoryMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(), result.getTotalElements(),
                result.getTotalPages(), result.hasNext());

        return new PagedResult<InventoryModel>(models, meta);
    }

    @Override
    public void deleteByIdAndDistributorId(UUID inventoryId, UUID distributorId) {
        inventoryRepository.deleteByIdAndDistributorId(inventoryId, distributorId);
    }

    @Override
    public List<InventoryModel> findByDistributorIdAndProductIdIn(UUID distributorId, List<UUID> productsIds) {
        return inventoryRepository.findByDistributorIdAndProductIdIn(distributorId, productsIds)
                .stream()
                .map(InventoryMapper::toDomain)
                .toList();
    }
}
