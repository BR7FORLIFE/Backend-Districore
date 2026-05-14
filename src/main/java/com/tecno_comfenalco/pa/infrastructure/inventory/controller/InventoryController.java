package com.tecno_comfenalco.pa.infrastructure.inventory.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.application.inventory.command.actions.CreateInventoryCommand;
import com.tecno_comfenalco.pa.application.inventory.command.actions.DeleteInventoryByIdCommand;
import com.tecno_comfenalco.pa.application.inventory.command.actions.GetAllInventoryCommand;
import com.tecno_comfenalco.pa.application.inventory.command.actions.UpdateInventoryCommand;
import com.tecno_comfenalco.pa.application.inventory.command.response.CreateInventoryCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.DeleteInventoryByIdCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.GetAllInventoryCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.GetInventoryByIdCommandResult;
import com.tecno_comfenalco.pa.application.inventory.command.response.UpdateInventoryCommandResult;
import com.tecno_comfenalco.pa.application.inventory.dto.request.CreateInventoryRequestDto;
import com.tecno_comfenalco.pa.application.inventory.dto.request.UpdateInventoryRequestDto;
import com.tecno_comfenalco.pa.application.inventory.dto.response.CreateInventoryResponseDto;
import com.tecno_comfenalco.pa.application.inventory.dto.response.DeleteInventoryResponseDto;
import com.tecno_comfenalco.pa.application.inventory.dto.response.GetAllInventoryResponseDto;
import com.tecno_comfenalco.pa.application.inventory.dto.response.GetInventoryByIdResponseDto;
import com.tecno_comfenalco.pa.application.inventory.dto.response.UpdateInventoryResponseDto;
import com.tecno_comfenalco.pa.application.inventory.usecase.InventoryUseCase;
import com.tecno_comfenalco.pa.infrastructure.security.CustomUserDetails;
import com.tecno_comfenalco.pa.shared.utils.http.DirectionEnum;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('DISTRIBUTOR')")
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryUseCase inventoryUseCase;

    public InventoryController(InventoryUseCase inventoryUseCase) {
        this.inventoryUseCase = inventoryUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateInventoryResponseDto> createInventory(
            @RequestBody @Valid CreateInventoryRequestDto dto,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        CreateInventoryCommand cmd = new CreateInventoryCommand(
                details.getUserId(),
                dto.warehouseId(),
                dto.productId(),
                dto.quantity());

        CreateInventoryCommandResult result = inventoryUseCase.createInventory(cmd);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateInventoryResponseDto(
                result.inventoryId(),
                result.warehouseId(),
                result.distributorId(),
                result.productId(),
                result.message()));
    }

    @PostMapping("/{inventoryId}")
    public ResponseEntity<UpdateInventoryResponseDto> updateInventory(
            @PathVariable UUID inventoryId,
            @RequestBody UpdateInventoryRequestDto dto,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        UpdateInventoryCommand cmd = new UpdateInventoryCommand(details.getUserId(), inventoryId, dto.quantity());
        UpdateInventoryCommandResult result = inventoryUseCase.updateInventory(cmd);

        return ResponseEntity.ok().body(new UpdateInventoryResponseDto(result.inventoryId(), result.message()));
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<GetInventoryByIdResponseDto> getInventoryById(
            @PathVariable UUID inventoryId,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        GetInventoryByIdCommandResult result = inventoryUseCase.getInventoryById(details.getUserId(), inventoryId);

        return ResponseEntity.ok().body(new GetInventoryByIdResponseDto(result.inventory(), result.message()));
    }

    @GetMapping
    public ResponseEntity<GetAllInventoryResponseDto> getAllInventories(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        RequestParams params = new RequestParams(null, page, size, sortBy, direction);

        GetAllInventoryCommand cmd = new GetAllInventoryCommand(details.getUserId(), params);
        GetAllInventoryCommandResult result = inventoryUseCase.getAllInventories(cmd);

        return ResponseEntity.ok()
                .body(new GetAllInventoryResponseDto(result.inventories(), result.meta(), result.message()));
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<DeleteInventoryResponseDto> deleteInventoryById(
            @PathVariable UUID inventoryId,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        DeleteInventoryByIdCommand cmd = new DeleteInventoryByIdCommand(details.getUserId(), inventoryId);
        DeleteInventoryByIdCommandResult result = inventoryUseCase.deleteInventoryById(cmd);

        return ResponseEntity.ok().body(new DeleteInventoryResponseDto(result.inventoryId(), result.message()));
    }
}
