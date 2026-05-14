package com.tecno_comfenalco.pa.application.catalog.usecase;

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
import com.tecno_comfenalco.pa.application.catalog.command.responses.GetProductByCategoryCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.ListCatalogCommandResult;

public interface CatalogUseCase {
    AddCategoryToCatalogCommandResult addCategoryToCatalog(AddCategoryToCatalogCommand cmd);

    AddProductToCategoryCommandResult addProductToCategory(AddProductToCategoryCommand cmd);

    GetProductByCategoryCommandResult getProductsByCategory(GetProductByCategoryCommand cmd);

    CreateCatalogCommandResult createCatalog(CreateCatalogCommand cmd);

    ListCatalogCommandResult listCatalog(ListCatalogCommand cmd);

    GetCatalogByIdCommandResult getCatalogById(GetCatalogByIdCommand cmd);
}
