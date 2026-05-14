package com.tecno_comfenalco.pa.infrastructure.product.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.tecno_comfenalco.pa.application.product.dto.request.EditProductRequestDto;
import com.tecno_comfenalco.pa.application.product.dto.request.RegisterProductRequestDto;
import com.tecno_comfenalco.pa.application.product.dto.response.DisableProductResponseDto;
import com.tecno_comfenalco.pa.application.product.dto.response.EditProductResponseDto;
import com.tecno_comfenalco.pa.application.product.dto.response.GetProductByIdResponseDto;
import com.tecno_comfenalco.pa.application.product.dto.response.ListProductsResponseDto;
import com.tecno_comfenalco.pa.application.product.dto.response.RegisterProductResponseDto;
import com.tecno_comfenalco.pa.application.product.usecases.ProductUseCase;
import com.tecno_comfenalco.pa.infrastructure.security.CustomUserDetails;
import com.tecno_comfenalco.pa.shared.utils.http.DirectionEnum;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('DISTRIBUTOR')")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterProductResponseDto> registerProduct(
            @RequestBody @Valid RegisterProductRequestDto dtoProduct, Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        UUID userDistributorId = details.getUserId();

        RegisterProductCommand cmd = new RegisterProductCommand(userDistributorId, dtoProduct.sku(), dtoProduct.name(),
                dtoProduct.price(), dtoProduct.unit());

        RegisterProductCommandResult result = productUseCase.registerProduct(cmd);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterProductResponseDto(result.productId(), result.sku(), result.message()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditProductResponseDto> editProduct(@PathVariable UUID id,
            @RequestBody @Valid EditProductRequestDto dtoProduct, Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        UUID userDistributorId = details.getUserId();

        EditProductCommand cmd = new EditProductCommand(id, userDistributorId, dtoProduct.sku(),
                dtoProduct.name(), dtoProduct.unit(), dtoProduct.price());

        EditProductCommandResult result = productUseCase.editProduct(cmd);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new EditProductResponseDto(result.message(), result.productId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DisableProductResponseDto> disabledProduct(@PathVariable UUID id,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        UUID userDistributorId = details.getUserId();

        DisabledProductCommand cmd = new DisabledProductCommand(userDistributorId, id);
        DisabledProductCommandResult result = productUseCase.disabledProduct(cmd);

        return ResponseEntity.ok().body(new DisableProductResponseDto(result.message()));
    }

    @GetMapping
    public ResponseEntity<ListProductsResponseDto> listProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        UUID userDistributorId = details.getUserId();

        RequestParams params = new RequestParams(name, page, size, sortBy, direction);

        ListProductCommand cmd = new ListProductCommand(userDistributorId, params);

        ListProductCommandResult result = productUseCase.listAll(cmd);

        return ResponseEntity.ok()
                .body(new ListProductsResponseDto(result.products(), result.meta(), result.message()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProductByIdResponseDto> showProduct(@PathVariable UUID id, Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        UUID userDistributorId = details.getUserId();

        GetProductByIdCommand cmd = new GetProductByIdCommand(userDistributorId, id);
        GetProductByIdCommandResult result = productUseCase.getProductById(cmd);

        return ResponseEntity.ok().body(new GetProductByIdResponseDto(result.product(), result.message()));
    }
}
