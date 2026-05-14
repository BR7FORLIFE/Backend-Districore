package com.tecno_comfenalco.pa.infrastructure.store.repository.mongo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.infrastructure.store.entity.StoreBindingRequestDocument;
import com.tecno_comfenalco.pa.shared.enums.BindingStatusEnum;

public interface StoreBindingRepository extends MongoRepository<StoreBindingRequestDocument, UUID> {
        boolean existsByNitAndDistributorId(String nit, UUID DistributorId);

        Page<StoreBindingRequestDocument> findByDistributorId(UUID DistributorId, Pageable pageable);

        Page<StoreBindingRequestDocument> findByDistributorIdAndTempNameContainingIgnoreCase(UUID DistributorId,
                        String tempName,
                        Pageable pageable);

        Optional<StoreBindingRequestDocument> findByIdAndDistributorId(UUID Id, UUID DistributorId);

        Optional<StoreBindingRequestDocument> findByNitAndActivationCode(String Nit, String ActivationCode);

        List<StoreBindingRequestDocument> findByNitAndDistributorIdAndBindingStatusAndIsConsumedTrue(String nit,
                        UUID distributorId, BindingStatusEnum bindingStatus);
}
