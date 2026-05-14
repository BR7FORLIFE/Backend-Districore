package com.tecno_comfenalco.pa.infrastructure.delivery.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.delivery.ports.IDeliveryRepositoryPort;
import com.tecno_comfenalco.pa.domain.delivery.model.DeliveryModel;
import com.tecno_comfenalco.pa.infrastructure.delivery.entity.DeliveryDocument;
import com.tecno_comfenalco.pa.infrastructure.delivery.mapper.DeliveryMapper;
import com.tecno_comfenalco.pa.infrastructure.delivery.repository.mongo.DeliveryRepository;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

@Repository
public class DeliveryRepositoryAdapter implements IDeliveryRepositoryPort {

    private DeliveryRepository deliveryRepository;

    public DeliveryRepositoryAdapter(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return deliveryRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public DeliveryModel save(DeliveryModel deliveryModel) {
        DeliveryDocument delivery = DeliveryMapper.toEntity(deliveryModel);
        DeliveryDocument saved = deliveryRepository.save(delivery);

        return DeliveryMapper.toDomain(saved);
    }

    @Override
    public PagedResult<DeliveryModel> findAllPaged(UUID distributorId, String name, Integer page, Integer size,
            String sortBy,
            String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<DeliveryDocument> result;

        if (name != null && !name.isBlank()) {
            result = deliveryRepository.findByDistributorIdAndNameContainingIgnoreCase(distributorId, name, pageable);

        } else {
            result = deliveryRepository.findByDistributorId(distributorId, pageable);
        }

        List<DeliveryModel> models = result.getContent()
                .stream()
                .map(DeliveryMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(), result.getTotalElements(),
                result.getTotalPages(), result.hasNext());

        return new PagedResult<DeliveryModel>(models, meta);
    }

    @Override
    public Optional<DeliveryModel> findByIdAndDistributorId(UUID id, UUID distributorId) {
        return deliveryRepository.findByIdAndDistributorId(id, distributorId)
                .map(DeliveryMapper::toDomain);
    }

    @Override
    public Optional<DeliveryModel> findByUserIdAndDistributorId(UUID id, UUID distributorId) {
        return deliveryRepository.findByUserIdAndDistributorId(id, distributorId)
                .map(DeliveryMapper::toDomain);
    }
}
