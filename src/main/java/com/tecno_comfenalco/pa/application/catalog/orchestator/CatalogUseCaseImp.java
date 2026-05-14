package com.tecno_comfenalco.pa.application.catalog.orchestator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.auth.Exceptions.UserNotFoundException;
import com.tecno_comfenalco.pa.application.catalog.command.actions.AddCategoryToCatalogCommand;
import com.tecno_comfenalco.pa.application.catalog.command.actions.AddProductToCategoryCommand;
import com.tecno_comfenalco.pa.application.catalog.command.actions.CreateCatalogCommand;
import com.tecno_comfenalco.pa.application.catalog.command.actions.GetCatalogByIdCommand;
import com.tecno_comfenalco.pa.application.catalog.command.actions.GetProductByCategoryCommand;
import com.tecno_comfenalco.pa.application.catalog.command.actions.ListCatalogCommand;
import com.tecno_comfenalco.pa.application.catalog.command.responses.AddCategoryToCatalogCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.AddProductToCategoryCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.CreateCatalogCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.GetCatalogByIdCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.GetCatalogForAuthenticatedUserCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.GetProductByCategoryCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.ListCatalogCommandResult;
import com.tecno_comfenalco.pa.application.catalog.exceptions.CatalogExistsException;
import com.tecno_comfenalco.pa.application.catalog.exceptions.CatalogNotFoundException;
import com.tecno_comfenalco.pa.application.catalog.exceptions.CategoryExistsException;
import com.tecno_comfenalco.pa.application.catalog.exceptions.CategoryNotFoundException;
import com.tecno_comfenalco.pa.application.catalog.exceptions.ProductExistsInCategoryException;
import com.tecno_comfenalco.pa.application.catalog.port.ICatalogRepositoryPort;
import com.tecno_comfenalco.pa.application.catalog.usecase.CatalogUseCase;
import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
import com.tecno_comfenalco.pa.application.presales.exceptions.PresalesNotFoundException;
import com.tecno_comfenalco.pa.application.presales.ports.IPresalesRepositoryPort;
import com.tecno_comfenalco.pa.application.product.exceptions.ProductNotFoundException;
import com.tecno_comfenalco.pa.application.product.ports.IProductRepositoryPort;
import com.tecno_comfenalco.pa.domain.catalog.models.CatalogModel;
import com.tecno_comfenalco.pa.domain.category.models.CategoryModel;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductSummaryModel;
import com.tecno_comfenalco.pa.shared.utils.helper.ValidateQueryParams;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

@Service
public class CatalogUseCaseImp implements CatalogUseCase {

    private final ICatalogRepositoryPort catalogRepositoryPort;
    private final IDistributorRepositoryPort distributorRepositoryPort;
    private final IProductRepositoryPort productRepositoryPort;
    private final IPresalesRepositoryPort presalesRepositoryPort;

    public CatalogUseCaseImp(ICatalogRepositoryPort iCatalogRepositoryPort,
            IDistributorRepositoryPort distributorRepositoryPort,
            IProductRepositoryPort productRepositoryPort,
            IPresalesRepositoryPort presalesRepositoryPort) {
        this.catalogRepositoryPort = iCatalogRepositoryPort;
        this.distributorRepositoryPort = distributorRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
        this.presalesRepositoryPort = presalesRepositoryPort;
    }

    @Override
    public CreateCatalogCommandResult createCatalog(CreateCatalogCommand cmd) {
        // encontramos a la distribuidora enlazada
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        String catalogNameLowercase = cmd.name().toLowerCase();

        boolean existsCatalog = catalogRepositoryPort.existsByDistributorIdAndCode(optDistributor.get().getId(),
                cmd.catalogCode());

        if (existsCatalog) {
            throw new CatalogExistsException();
        }

        CatalogModel newCatalog = CatalogModel.createDraft(optDistributor.get().getId(), cmd.catalogCode(),
                catalogNameLowercase,
                List.of());

        catalogRepositoryPort.save(newCatalog);

        return new CreateCatalogCommandResult(newCatalog, "Catalog created succesfull!");
    }

    @Override
    public AddCategoryToCatalogCommandResult addCategoryToCatalog(AddCategoryToCatalogCommand cmd) {
        // buscamos la distribuidora
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        boolean ownsCatalog = catalogRepositoryPort.existsByIdAndDistributorId(
                cmd.catalogId(),
                optDistributor.get().getId());

        if (!ownsCatalog) {
            throw new CatalogNotFoundException();
        }

        // vericamos que exista el catalogo
        boolean existsCatalog = catalogRepositoryPort.existsCatalogById(cmd.catalogId());

        if (!existsCatalog) {
            throw new CatalogNotFoundException();
        }

        String categoryLowercase = cmd.name().toLowerCase();

        boolean existsCategory = catalogRepositoryPort.existsCategoryByDistributorIdAndName(
                cmd.catalogId(),
                categoryLowercase);

        if (existsCategory) {
            throw new CategoryExistsException();
        }

        CategoryModel categoryModel = CategoryModel.createDraft(categoryLowercase, null);

        catalogRepositoryPort.addCategoryToCatalog(cmd.catalogId(), categoryModel);

        return new AddCategoryToCatalogCommandResult(
                cmd.catalogId(),
                categoryModel.getId(),
                "Category added succesfull!");
    }

    @Override
    public AddProductToCategoryCommandResult addProductToCategory(AddProductToCategoryCommand cmd) {
        // validamos que exista la distribuidora
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        boolean ownsCatalog = catalogRepositoryPort.existsByIdAndDistributorId(
                cmd.catalogId(),
                optDistributor.get().getId());

        if (!ownsCatalog) {
            throw new CatalogNotFoundException();
        }

        // validamos que exista el catalogo
        boolean existsCatalog = catalogRepositoryPort.existsCatalogById(cmd.catalogId());

        if (!existsCatalog) {
            throw new CatalogNotFoundException();
        }

        // validamos que exista la categoria
        boolean existsCategory = catalogRepositoryPort.existsCategoryByCatalogIdAndCategoryId(
                cmd.catalogId(),
                cmd.categoryId());

        if (!existsCategory) {
            throw new CategoryNotFoundException();
        }

        // validamos que exista el producto
        Optional<ProductModel> optProduct = productRepositoryPort.findByProductId(
                optDistributor.get().getId(),
                cmd.productId());

        if (optProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        // validar si un producto no ha sido agregado a una categoria
        boolean existsProductIntoCategory = catalogRepositoryPort.existsByIdAndCategoriesIdAndCategoriesProductsId(
                cmd.catalogId(),
                cmd.categoryId(),
                optProduct.get().getId());

        if (existsProductIntoCategory) {
            throw new ProductExistsInCategoryException();
        }

        ProductSummaryModel summaryProduct = new ProductSummaryModel(
                optProduct.get().getId(),
                optProduct.get().getSku(),
                optProduct.get().getName(),
                optProduct.get().getUnit(),
                optProduct.get().getPrice());

        catalogRepositoryPort.addProductToCategory(cmd.categoryId(), summaryProduct);

        return new AddProductToCategoryCommandResult("product added succesfull!");
    }

    @Override
    public GetProductByCategoryCommandResult getProductsByCategory(GetProductByCategoryCommand cmd) {
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByCategory'");
    }

    @Override
    public ListCatalogCommandResult listCatalog(ListCatalogCommand cmd) {

        switch (cmd.role()) {
            case "ROLE_DISTRIBUTOR":
                ValidateQueryParams.validate(cmd.params());

                Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userId());

                if (optDistributor.isEmpty()) {
                    throw new DistributorNotFoundException();
                }

                PagedResult<CatalogModel> catalogs = catalogRepositoryPort.findAllPaged(
                        optDistributor.get().getId(),
                        cmd.params().name(),
                        cmd.params().page(),
                        cmd.params().size(),
                        cmd.params().sortBy(),
                        cmd.params().direction().name());

                return new ListCatalogCommandResult(
                        catalogs.data(),
                        catalogs.meta(),
                        "catalogs obtain succesfull!");

            case "ROLE_PRESALES":
                ValidateQueryParams.validate(cmd.params());

                Optional<PresalesModel> optPresales = presalesRepositoryPort
                        .findPresalesByUserIdAndDistributorId(cmd.userId(), cmd.distributorId());

                if (optPresales.isEmpty()) {
                    throw new PresalesNotFoundException();
                }

                PagedResult<CatalogModel> catalogsP = catalogRepositoryPort.findAllPaged(
                        optPresales.get().getDistributorId(),
                        cmd.params().name(),
                        cmd.params().page(),
                        cmd.params().size(),
                        cmd.params().sortBy(),
                        cmd.params().direction().name());

                return new ListCatalogCommandResult(
                        catalogsP.data(),
                        catalogsP.meta(),
                        "catalogs obtain succesfull!");

            default:
                throw new UserNotFoundException();
        }
    }

    @Override
    public GetCatalogByIdCommandResult getCatalogById(GetCatalogByIdCommand cmd) {

        switch (cmd.role()) {
            case "ROLE_DISTRIBUTOR":
                Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userId());

                if (optDistributor.isEmpty()) {
                    throw new DistributorNotFoundException();
                }

                Optional<CatalogModel> optCatalog = catalogRepositoryPort.findByCatalogIdAndDistributorId(
                        cmd.catalogId(),
                        optDistributor.get().getId());

                if (optCatalog.isEmpty()) {
                    throw new CatalogNotFoundException();
                }

                return new GetCatalogByIdCommandResult(optCatalog.get(), "Catalog obtain succesfull!");

            case "ROLE_PRESALES":
                Optional<PresalesModel> optPresales = presalesRepositoryPort.findPresalesByUserIdAndDistributorId(
                        cmd.userId(),
                        cmd.distributorId());

                if (optPresales.isEmpty()) {
                    throw new PresalesNotFoundException();
                }

                Optional<CatalogModel> optCatalogP = catalogRepositoryPort.findByCatalogIdAndDistributorId(
                        cmd.catalogId(),
                        optPresales.get().getDistributorId());

                if (optCatalogP.isEmpty()) {
                    throw new CatalogNotFoundException();
                }

                return new GetCatalogByIdCommandResult(optCatalogP.get(), "Catalog obtain succesfull!");

            default:
                throw new UserNotFoundException();
        }
    }
}
