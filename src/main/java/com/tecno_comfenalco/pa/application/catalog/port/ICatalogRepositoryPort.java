package com.tecno_comfenalco.pa.application.catalog.port;

import java.util.Optional;
import java.util.UUID;

import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;
import com.tecno_comfenalco.pa.domain.category.models.CategoryModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductSummaryModel;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

public interface ICatalogRepositoryPort {
    boolean existsByDistributorIdAndCode(UUID distributorId, String code);

    boolean existsCategoryByDistributorIdAndName(UUID catalogId, String categoryName);

    boolean existsCategoryByCatalogIdAndCategoryId(UUID catalogId, UUID categoryId);

    boolean existsByIdAndCategoriesIdAndCategoriesProductsId(UUID catalogId, UUID categoryId, UUID productId);

    boolean existsByIdAndDistributorId(UUID catalogId, UUID distributorId);

    CatalogModel save(CatalogModel catalogModel);

    void addCategoryToCatalog(UUID catalogId, CategoryModel categoryModel);

    void addProductToCategory(UUID categoryId, ProductSummaryModel model);

    boolean existsCatalogById(UUID catalogId);

    PagedResult<CatalogModel> findAllPaged(UUID distributorId, String name, Integer page, Integer size, String sortBy,
            String direction);

    Optional<CatalogModel> findByCatalogIdAndDistributorId(UUID catalogId, UUID distributorId);
}
