package com.tecno_comfenalco.pa.shared.utils.http;

public record PaginationMeta(Integer currentPage, Integer pageSize, Long totalElements, Integer totalPages,
                boolean hasMore) {

}
