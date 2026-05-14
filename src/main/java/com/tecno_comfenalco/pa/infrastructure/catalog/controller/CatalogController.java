package com.tecno_comfenalco.pa.infrastructure.catalog.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.application.auth.Exceptions.UserNotFoundException;
import com.tecno_comfenalco.pa.application.catalog.command.actions.AddCategoryToCatalogCommand;
import com.tecno_comfenalco.pa.application.catalog.command.actions.AddProductToCategoryCommand;
import com.tecno_comfenalco.pa.application.catalog.command.actions.CreateCatalogCommand;
import com.tecno_comfenalco.pa.application.catalog.command.actions.GetCatalogByIdCommand;
import com.tecno_comfenalco.pa.application.catalog.command.actions.ListCatalogCommand;
import com.tecno_comfenalco.pa.application.catalog.command.responses.AddCategoryToCatalogCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.AddProductToCategoryCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.CreateCatalogCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.GetCatalogByIdCommandResult;
import com.tecno_comfenalco.pa.application.catalog.command.responses.ListCatalogCommandResult;
import com.tecno_comfenalco.pa.application.catalog.dto.request.AddCategoryToCatalogRequestDto;
import com.tecno_comfenalco.pa.application.catalog.dto.request.AddExistingProductToCategoryRequestDto;
import com.tecno_comfenalco.pa.application.catalog.dto.request.CreateCatalogRequestDto;
import com.tecno_comfenalco.pa.application.catalog.dto.response.AddCategoryToCatalogResponseDto;
import com.tecno_comfenalco.pa.application.catalog.dto.response.AddProductToCategoryResponseDto;
import com.tecno_comfenalco.pa.application.catalog.dto.response.CreateCatalogResponseDto;
import com.tecno_comfenalco.pa.application.catalog.dto.response.GetCatalogByIdResponseDto;
import com.tecno_comfenalco.pa.application.catalog.dto.response.GetCatalogResponseDto;
import com.tecno_comfenalco.pa.application.catalog.dto.response.GetCategoryProductsResponseDto;
import com.tecno_comfenalco.pa.application.catalog.usecase.CatalogUseCase;
import com.tecno_comfenalco.pa.infrastructure.security.CustomUserDetails;
import com.tecno_comfenalco.pa.shared.utils.http.DirectionEnum;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('DISTRIBUTOR')")
@RestController
@RequestMapping("/catalogs")
public class CatalogController {

    private final CatalogUseCase catalogUseCase;

    public CatalogController(CatalogUseCase catalogUseCase) {
        this.catalogUseCase = catalogUseCase;
    }

    /**
     * Agrega una categoría al catálogo de la distribuidora autenticada
     * Solo DISTRIBUTOR puede ejecutar este método
     * El catálogo se detecta automáticamente según el usuario logueado
     */
    @PostMapping
    public ResponseEntity<CreateCatalogResponseDto> createCatalog(@RequestBody CreateCatalogRequestDto dto,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        CreateCatalogCommand cmd = new CreateCatalogCommand(details.getUserId(), dto.name(), dto.catalogCode());
        CreateCatalogCommandResult result = catalogUseCase.createCatalog(cmd);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateCatalogResponseDto(result.catalog(), result.message()));
    }

    @PostMapping("/{catalogId}/categories")
    public ResponseEntity<AddCategoryToCatalogResponseDto> addCategoryToCatalog(
            @PathVariable UUID catalogId,
            @RequestBody @Valid AddCategoryToCatalogRequestDto request,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        AddCategoryToCatalogCommand cmd = new AddCategoryToCatalogCommand(details.getUserId(), catalogId,
                request.name());

        AddCategoryToCatalogCommandResult result = catalogUseCase.addCategoryToCatalog(cmd);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new AddCategoryToCatalogResponseDto(result.catalogId(), result.categoryId(), result.message()));
    }

    /**
     * Agrega un producto existente a una categoría
     * Solo DISTRIBUTOR puede ejecutar este método
     * Valida automáticamente que la categoría pertenezca al catálogo del usuario
     * autenticado
     */
    @PostMapping("/{catalogId}/categories/{categoryId}/products")
    public ResponseEntity<AddProductToCategoryResponseDto> addProductToCategory(
            @PathVariable UUID catalogId,
            @PathVariable UUID categoryId,
            Authentication authentication,
            @RequestBody @Valid AddExistingProductToCategoryRequestDto request) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        AddProductToCategoryCommand cmd = new AddProductToCategoryCommand(details.getUserId(), catalogId, categoryId,
                request.productId());
        AddProductToCategoryCommandResult result = catalogUseCase.addProductToCategory(cmd);

        return ResponseEntity.ok().body(new AddProductToCategoryResponseDto(result.message()));
    }

    /**
     * Obtiene el catálogo completo de la distribuidora del usuario autenticado
     * Tanto DISTRIBUTOR como PRESALES pueden ver el catálogo de su distribuidora
     */
    @PreAuthorize("hasAnyRole('DISTRIBUTOR', 'PRESALES')")
    @GetMapping
    public ResponseEntity<GetCatalogResponseDto> getMyCatalog(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        String role = details.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException());

        UUID distributorId = details.getDistributorId();

        RequestParams params = new RequestParams(name, page, size, sortBy, direction);

        ListCatalogCommand cmd = new ListCatalogCommand(details.getUserId(), distributorId, role, params);
        ListCatalogCommandResult result = catalogUseCase.listCatalog(cmd);

        return ResponseEntity.ok().body(new GetCatalogResponseDto(result.catalogs(), result.meta(), result.message()));
    }

    @PreAuthorize("hasAnyRole('DISTRIBUTOR', 'PRESALES')")
    @GetMapping("/{catalogId}")
    public ResponseEntity<GetCatalogByIdResponseDto> getCatalogById(
            @PathVariable UUID catalogId,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        String role = details.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException());

        GetCatalogByIdCommand cmd = new GetCatalogByIdCommand(
                details.getUserId(),
                details.getDistributorId(),
                catalogId,
                role);

        GetCatalogByIdCommandResult result = catalogUseCase.getCatalogById(cmd);

        return ResponseEntity.ok().body(new GetCatalogByIdResponseDto(result.catalog(), result.message()));
    }

    /**
     * Obtiene todos los productos de una categoría específica
     * Tanto DISTRIBUTOR como PRESALES pueden ver, pero solo de su propia
     * distribuidora
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<GetCategoryProductsResponseDto> getProductsByCategory(@PathVariable UUID categoryId) {
        return null;
    }
}
