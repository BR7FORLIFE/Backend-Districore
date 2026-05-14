package com.tecno_comfenalco.pa.application.product.orchestator;

import java.time.Instant;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
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
import com.tecno_comfenalco.pa.application.product.events.ProductDeletedEvent;
import com.tecno_comfenalco.pa.application.product.events.ProductUpdatedEvent;
import com.tecno_comfenalco.pa.application.product.exceptions.ProductExistsException;
import com.tecno_comfenalco.pa.application.product.exceptions.ProductNotFoundException;
import com.tecno_comfenalco.pa.application.product.ports.IProductRepositoryPort;
import com.tecno_comfenalco.pa.application.product.usecases.ProductUseCase;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.shared.utils.helper.ValidateQueryParams;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

@Service
public class ProductUseCaseImp implements ProductUseCase {

    private final IDistributorRepositoryPort distributorRepositoryPort;
    private final IProductRepositoryPort iProductRepositoryPort;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductUseCaseImp(IProductRepositoryPort iProductRepositoryPort,
            IDistributorRepositoryPort distributorRepositoryPort,
            ApplicationEventPublisher applicationEventPublisher) {
        this.iProductRepositoryPort = iProductRepositoryPort;
        this.distributorRepositoryPort = distributorRepositoryPort;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public RegisterProductCommandResult registerProduct(RegisterProductCommand cmd) {

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        boolean existsProduct = iProductRepositoryPort.existsByDistributorIdAndSku(optDistributor.get().getId(),
                cmd.sku());

        if (existsProduct) {
            throw new ProductExistsException();
        }
        ProductModel productModel = ProductModel.createDraft(optDistributor.get().getId(), cmd.sku(), cmd.name(),
                cmd.unit(),
                cmd.price());

        ProductModel result = iProductRepositoryPort.save(productModel);

        return new RegisterProductCommandResult(result.getId(), result.getSku(), "Product created succesfull!");
    }

    // aca lanzaremos un eventPublisher para poder capturar el evento y poder
    // actualizar los catalogos enlazados
    @Override
    public EditProductCommandResult editProduct(EditProductCommand cmd) {

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<ProductModel> optProduct = iProductRepositoryPort.findByProductId(optDistributor.get().getId(),
                cmd.productId());

        if (optProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        ProductModel updateProduct = ProductModel.createNew(optProduct.get().getId(),
                optProduct.get().getDistributorId(), cmd.sku(), cmd.name(),
                cmd.unit(), cmd.price(), optProduct.get().getCreateAt(), Instant.now());

        ProductModel result = iProductRepositoryPort.save(updateProduct);

        // lanzamos el evento para que el consumer lo capture y pueda hacer los
        // respectivos cambios en los catalogos
        applicationEventPublisher.publishEvent(new ProductUpdatedEvent(
                result.getId(),
                result.getDistributorId(),
                result.getSku(),
                result.getName(),
                result.getUnit(),
                result.getPrice()));

        return new EditProductCommandResult(cmd.productId(), "Product update succesfull!");
    }

    // aca lanzaremos un eventPublisher para poder capturar el evento y poder
    // actualizar los catalogos enlazados
    @Override
    public DisabledProductCommandResult disabledProduct(DisabledProductCommand cmd) {

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        boolean existsProduct = iProductRepositoryPort.existsByProductIdAndDistributorId(cmd.productId(),
                optDistributor.get().getId());

        if (!existsProduct) {
            throw new ProductNotFoundException();
        }

        iProductRepositoryPort.deleteProductByIdAndDistributorId(cmd.productId(), optDistributor.get().getId());

        applicationEventPublisher.publishEvent(new ProductDeletedEvent(cmd.productId(), optDistributor.get().getId()));

        return new DisabledProductCommandResult("Product delete succesfull!");
    }

    @Override
    public ListProductCommandResult listAll(ListProductCommand cmd) {
        // validar los query params
        ValidateQueryParams.validate(cmd.params());

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        PagedResult<ProductModel> products = iProductRepositoryPort.findAllPaged(optDistributor.get().getId(),
                cmd.params().name(),
                cmd.params().page(),
                cmd.params().size(),
                cmd.params().sortBy(),
                cmd.params().direction().name());

        return new ListProductCommandResult(products.data(), products.meta(), "Products obtain sucessfull");
    }

    @Override
    public GetProductByIdCommandResult getProductById(GetProductByIdCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<ProductModel> product = iProductRepositoryPort.findByProductId(optDistributor.get().getId(),
                cmd.productId());

        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return new GetProductByIdCommandResult(product.get(), "Product obtain succesfull!");
    }
}