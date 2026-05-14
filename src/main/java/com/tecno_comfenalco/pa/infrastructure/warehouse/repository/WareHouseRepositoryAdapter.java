package com.tecno_comfenalco.pa.infrastructure.warehouse.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.warehouse.ports.IWareHouseRepositoryPort;
import com.tecno_comfenalco.pa.domain.warehouse.models.WareHouseModel;
import com.tecno_comfenalco.pa.infrastructure.warehouse.entity.WareHouseDocument;
import com.tecno_comfenalco.pa.infrastructure.warehouse.mapper.WareHouseMapper;
import com.tecno_comfenalco.pa.infrastructure.warehouse.repository.mongo.WareHouseRepository;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

@Repository
public class WareHouseRepositoryAdapter implements IWareHouseRepositoryPort {

    private final WareHouseRepository wareHouseRepository;

    public WareHouseRepositoryAdapter(WareHouseRepository wareHouseRepository) {
        this.wareHouseRepository = wareHouseRepository;
    }

    @Override
    public WareHouseModel save(WareHouseModel model) {
        WareHouseDocument document = WareHouseMapper.toEntity(model);
        WareHouseDocument saved = wareHouseRepository.save(document);

        return WareHouseMapper.toDomain(saved);
    }

    @Override
    public Optional<WareHouseModel> findByIdAndDistributorId(UUID warehouseId, UUID distributorId) {
        return wareHouseRepository.findByIdAndDistributorId(warehouseId, distributorId)
                .map(WareHouseMapper::toDomain);
    }

    @Override
    public PagedResult<WareHouseModel> findAllPaged(UUID distributorId, String name, Integer page, Integer size,
            String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<WareHouseDocument> result;

        if (name != null && !name.isBlank()) {
            result = wareHouseRepository.findByDistributorIdAndNameContainingIgnoreCase(distributorId, name, pageable);

        } else {
            result = wareHouseRepository.findByDistributorId(distributorId, pageable);
        }

        List<WareHouseModel> models = result.getContent()
                .stream()
                .map(WareHouseMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(), result.getTotalElements(),
                result.getTotalPages(), result.hasNext());

        return new PagedResult<WareHouseModel>(models, meta);
    };
}
