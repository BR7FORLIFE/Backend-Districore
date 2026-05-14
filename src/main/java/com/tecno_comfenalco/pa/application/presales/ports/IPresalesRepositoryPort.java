package com.tecno_comfenalco.pa.application.presales.ports;

import java.util.Optional;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

public interface IPresalesRepositoryPort {

    Optional<PresalesModel> findPresalesByUserIdAndDistributorId(UUID userId, UUID distributorId);

    boolean existsPresalesbyDocumentNumber(String documentNumber);

    Optional<PresalesModel> findPresalesById(UUID distributorId, UUID presalesId);

    Optional<PresalesModel> findPresalesByIdAndDistributorId(UUID presaleId, UUID distributorId);

    PagedResult<PresalesModel> findAllPaged(UUID distributorId, String name, Integer page, Integer size, String sortBy,
            String direction);

    PresalesModel save(PresalesModel presalesModel);
}
