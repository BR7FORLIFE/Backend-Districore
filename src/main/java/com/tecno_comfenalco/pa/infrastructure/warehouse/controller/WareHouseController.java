package com.tecno_comfenalco.pa.infrastructure.warehouse.controller;

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

import com.tecno_comfenalco.pa.application.warehouse.command.actions.DeleteLogicWareHouseCommand;
import com.tecno_comfenalco.pa.application.warehouse.command.actions.GetAllWarehousesCommand;
import com.tecno_comfenalco.pa.application.warehouse.command.actions.GetWareHouseByIdCommand;
import com.tecno_comfenalco.pa.application.warehouse.command.actions.RegisterWarehouseCommand;
import com.tecno_comfenalco.pa.application.warehouse.command.actions.UpdateWareHouseCommand;
import com.tecno_comfenalco.pa.application.warehouse.command.response.DeleteLogicWareHouseCommandResult;
import com.tecno_comfenalco.pa.application.warehouse.command.response.GetAllWareHouseCommandResult;
import com.tecno_comfenalco.pa.application.warehouse.command.response.GetWareHouseByIdCommandResult;
import com.tecno_comfenalco.pa.application.warehouse.command.response.RegisterWareHouseCommandResult;
import com.tecno_comfenalco.pa.application.warehouse.command.response.UpdateWareHouseCommandResult;
import com.tecno_comfenalco.pa.application.warehouse.dto.request.RegisterWareHouseRequestDto;
import com.tecno_comfenalco.pa.application.warehouse.dto.request.UpdateWareHouseRequestDto;
import com.tecno_comfenalco.pa.application.warehouse.dto.response.DeleteLogicWareHouseResponseDto;
import com.tecno_comfenalco.pa.application.warehouse.dto.response.GetAllWareHousesResponseDto;
import com.tecno_comfenalco.pa.application.warehouse.dto.response.GetWareHouseByIdResponseDto;
import com.tecno_comfenalco.pa.application.warehouse.dto.response.RegisterWareHouseResponseDto;
import com.tecno_comfenalco.pa.application.warehouse.dto.response.UpdateWareHouseResponseDto;
import com.tecno_comfenalco.pa.application.warehouse.usecases.WareHouseUseCase;
import com.tecno_comfenalco.pa.infrastructure.security.CustomUserDetails;
import com.tecno_comfenalco.pa.shared.utils.http.DirectionEnum;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

@PreAuthorize("hasRole('DISTRIBUTOR')")
@RestController
@RequestMapping("/warehouse")
public class WareHouseController {

    private final WareHouseUseCase wareHouseUseCase;

    public WareHouseController(WareHouseUseCase wareHouseUseCase) {
        this.wareHouseUseCase = wareHouseUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterWareHouseResponseDto> registerWarehouse(
            @RequestBody RegisterWareHouseRequestDto dto,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        RegisterWarehouseCommand cmd = new RegisterWarehouseCommand(
                details.getUserId(),
                dto.name(),
                dto.direction());

        RegisterWareHouseCommandResult result = wareHouseUseCase.registerWarehouse(cmd);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterWareHouseResponseDto(result.distributorId(), result.warehouseId(), result.message()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetWareHouseByIdResponseDto> getWareHouseById(@PathVariable UUID id,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        GetWareHouseByIdCommand cmd = new GetWareHouseByIdCommand(details.getUserId(), id);
        GetWareHouseByIdCommandResult result = wareHouseUseCase.getWarehouseById(cmd);

        return ResponseEntity.ok().body(new GetWareHouseByIdResponseDto(result.warehouse(), result.message()));
    }

    @GetMapping
    public ResponseEntity<GetAllWareHousesResponseDto> listAllWareHouse(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        RequestParams params = new RequestParams(name, page, size, sortBy, direction);

        GetAllWarehousesCommand cmd = new GetAllWarehousesCommand(details.getUserId(), params);
        GetAllWareHouseCommandResult result = wareHouseUseCase.getAllWarehouses(cmd);

        return ResponseEntity.ok()
                .body(new GetAllWareHousesResponseDto(result.warehouses(), result.meta(), result.message()));
    }

    @PostMapping("/{warehouseId}")
    public ResponseEntity<UpdateWareHouseResponseDto> updateWarehouse(
            @RequestBody UpdateWareHouseRequestDto dto,
            @PathVariable UUID warehouseId,
            Authentication authentication) {

        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        UpdateWareHouseCommand cmd = new UpdateWareHouseCommand(
                details.getUserId(),
                warehouseId,
                dto.name(),
                dto.direction());

        UpdateWareHouseCommandResult result = wareHouseUseCase.updateWareHouse(cmd);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UpdateWareHouseResponseDto(
                result.distributorId(),
                result.warehouseId(),
                result.message()));
    }

    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<DeleteLogicWareHouseResponseDto> deleteLogicWareHouse(
            @PathVariable UUID warehouseId,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        DeleteLogicWareHouseCommand cmd = new DeleteLogicWareHouseCommand(details.getUserId(), warehouseId);
        DeleteLogicWareHouseCommandResult result = wareHouseUseCase.disabledWareHouse(cmd);

        return ResponseEntity.ok().body(new DeleteLogicWareHouseResponseDto(
                result.distributorId(),
                result.warehouseId(),
                result.message()));
    }
}
