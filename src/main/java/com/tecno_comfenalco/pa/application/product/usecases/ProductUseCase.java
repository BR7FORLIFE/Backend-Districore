package com.tecno_comfenalco.pa.application.product.usecases;

import com.tecno_comfenalco.pa.application.product.command.actions.DisabledProductCommand;
import com.tecno_comfenalco.pa.application.product.command.actions.EditProductCommand;
import com.tecno_comfenalco.pa.application.product.command.actions.GetProductByIdCommand;
import com.tecno_comfenalco.pa.application.product.command.actions.ListProductCommand;
import com.tecno_comfenalco.pa.application.product.command.actions.RegisterProductCommand;
import com.tecno_comfenalco.pa.application.product.command.response.DisabledProductCommandResult;
import com.tecno_comfenalco.pa.application.product.command.response.EditProductCommandResult;
import com.tecno_comfenalco.pa.application.product.command.response.GetProductByIdCommandResult;
import com.tecno_comfenalco.pa.application.product.command.response.ListProductCommandResult;
import com.tecno_comfenalco.pa.application.product.command.response.RegisterProductCommandResult;

public interface ProductUseCase {
    RegisterProductCommandResult registerProduct(RegisterProductCommand cmd);

    EditProductCommandResult editProduct(EditProductCommand cmd);

    DisabledProductCommandResult disabledProduct(DisabledProductCommand cmd);

    ListProductCommandResult listAll(ListProductCommand cmd);

    GetProductByIdCommandResult getProductById(GetProductByIdCommand cmd);
}
