package com.tecno_comfenalco.pa.infrastructure.catalog.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;
import com.tecno_comfenalco.pa.application.catalog.exceptions.CategoryNotFoundException;
import com.tecno_comfenalco.pa.application.catalog.port.ICatalogRepositoryPort;
import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;
import com.tecno_comfenalco.pa.domain.category.models.CategoryModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductSummaryModel;
import com.tecno_comfenalco.pa.infrastructure.catalog.entity.CatalogDocument;
import com.tecno_comfenalco.pa.infrastructure.catalog.mapper.CatalogMapper;
import com.tecno_comfenalco.pa.infrastructure.catalog.repository.mongo.CatalogRepository;
import com.tecno_comfenalco.pa.infrastructure.category.entity.CategoryEmbeddedEntity;
import com.tecno_comfenalco.pa.infrastructure.category.mapper.CategoryEmbeddedMapper;
import com.tecno_comfenalco.pa.infrastructure.product.entity.ProductSummaryEmbeddedEntity;
import com.tecno_comfenalco.pa.infrastructure.product.mapper.ProductSummaryMapper;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

@Repository
public class CatalogRepositoryAdapter implements ICatalogRepositoryPort {

    private final CatalogRepository catalogRepository;
    private final MongoTemplate mongoTemplate;

    public CatalogRepositoryAdapter(CatalogRepository catalogRepository, MongoTemplate mongoTemplate) {
        this.catalogRepository = catalogRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean existsByDistributorIdAndCode(UUID distributorId, String code) {
        return catalogRepository.existsByDistributorIdAndCatalogCode(distributorId, code);
    }

    @Override
    public CatalogModel save(CatalogModel catalogModel) {
        CatalogDocument catalogDocument = CatalogMapper.toEntity(catalogModel);
        CatalogDocument saved = catalogRepository.save(catalogDocument);

        return CatalogMapper.toDomain(saved);
    }

    @Override
    public boolean existsCategoryByDistributorIdAndName(UUID catalogId, String categoryName) {
        return catalogRepository.existsByIdAndCategoriesName(catalogId, categoryName);
    }

    @Override
    public boolean existsByIdAndCategoriesIdAndCategoriesProductsId(UUID catalogId, UUID categoryId, UUID productId) {
        return catalogRepository.existsByIdAndCategoriesIdAndCategoriesProductsId(catalogId, categoryId, productId);
    }

    @Override
    public boolean existsByIdAndDistributorId(UUID catalogId, UUID distributorId) {
        return catalogRepository.existsByIdAndDistributorId(catalogId, distributorId);
    }

    @Override
    public void addCategoryToCatalog(UUID catalogId, CategoryModel categoryModel) {
        Query query = new Query(Criteria.where("_id").is(catalogId));
        CategoryEmbeddedEntity categoryEmbeddedEntity = CategoryEmbeddedMapper.toEntity(categoryModel);

        Update update = new Update().push("categories", categoryEmbeddedEntity);
        mongoTemplate.updateFirst(query, update, CatalogDocument.class);
    }

    @Override
    public void addProductToCategory(UUID categoryId, ProductSummaryModel model) {
        Query query = new Query(Criteria.where("categories._id").is(categoryId));

        ProductSummaryEmbeddedEntity productEntity = ProductSummaryMapper.toEntity(model);

        Update update = new Update().push("categories.$.products", productEntity);

        UpdateResult result = mongoTemplate.updateFirst(query, update, CatalogDocument.class);

        if (result.getMatchedCount() == 0) {
            throw new CategoryNotFoundException();
        }
    }

    @Override
    public boolean existsCatalogById(UUID catalogId) {
        return catalogRepository.existsById(catalogId);
    }

    @Override
    public boolean existsCategoryByCatalogIdAndCategoryId(UUID catalogId, UUID categoryId) {
        return catalogRepository.existsByIdAndCategoriesId(catalogId, categoryId);
    }

    @Override
    public PagedResult<CatalogModel> findAllPaged(UUID distributorId, String name, Integer page, Integer size,
            String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<CatalogDocument> result;

        if (name != null && !name.isBlank()) {
            result = catalogRepository.findByDistributorIdAndNameContainingIgnoreCase(distributorId, name, pageable);

        } else {
            result = catalogRepository.findByDistributorId(distributorId, pageable);
        }

        List<CatalogModel> models = result.getContent()
                .stream()
                .map(CatalogMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(), result.getTotalElements(),
                result.getTotalPages(), result.hasNext());

        return new PagedResult<CatalogModel>(models, meta);
    }

    @Override
    public Optional<CatalogModel> findByCatalogIdAndDistributorId(UUID catalogId, UUID distributorId) {
        return catalogRepository.findByIdAndDistributorId(catalogId, distributorId)
                .map(CatalogMapper::toDomain);
    }
}
