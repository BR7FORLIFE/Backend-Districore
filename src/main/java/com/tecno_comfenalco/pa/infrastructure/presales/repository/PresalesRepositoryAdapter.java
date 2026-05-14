package com.tecno_comfenalco.pa.infrastructure.presales.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.presales.ports.IPresalesRepositoryPort;
import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;
import com.tecno_comfenalco.pa.infrastructure.presales.entity.PresalesDocument;
import com.tecno_comfenalco.pa.infrastructure.presales.mapper.PresalesMapper;
import com.tecno_comfenalco.pa.infrastructure.presales.repository.mongo.PresalesRepository;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

@Repository
public class PresalesRepositoryAdapter implements IPresalesRepositoryPort {

    private PresalesRepository presalesRepository;

    public PresalesRepositoryAdapter(PresalesRepository presalesRepository) {
        this.presalesRepository = presalesRepository;
    }

    @Override
    public boolean existsPresalesbyDocumentNumber(String documentNumber) {
        return presalesRepository.existsByPhoneNumber(documentNumber);
    }

    @Override
    public PresalesModel save(PresalesModel presalesModel) {
        PresalesDocument presalesDocument = PresalesMapper.toEntity(presalesModel);
        PresalesDocument saved = presalesRepository.save(presalesDocument);

        return PresalesMapper.toDomain(saved);
    }

    @Override
    public PagedResult<PresalesModel> findAllPaged(UUID distributorId, String name, Integer page, Integer size,
            String sortBy,
            String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<PresalesDocument> result;

        if (name != null && !name.isBlank()) {
            result = presalesRepository.findByDistributorIdAndNameContainingIgnoreCase(distributorId, name, pageable);
        } else {
            result = presalesRepository.findByDistributorId(distributorId, pageable);
        }

        List<PresalesModel> models = result.getContent()
                .stream()
                .map(PresalesMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(), result.getTotalElements(),
                result.getTotalPages(), result.hasNext());

        return new PagedResult<PresalesModel>(models, meta);
    }

    @Override
    public Optional<PresalesModel> findPresalesById(UUID distributorId, UUID presalesId) {
        return presalesRepository.findByIdAndDistributorId(presalesId, distributorId)
                .map(PresalesMapper::toDomain);
    }

    @Override
    public Optional<PresalesModel> findPresalesByIdAndDistributorId(UUID presaleId, UUID distributorId) {
        return presalesRepository.findByIdAndDistributorId(presaleId, distributorId)
                .map(PresalesMapper::toDomain);
    }

    @Override
    public Optional<PresalesModel> findPresalesByUserIdAndDistributorId(UUID userId, UUID distributorId) {
        return presalesRepository.findByUserIdAndDistributorId(userId, distributorId)
                .map(PresalesMapper::toDomain);
    }
}
