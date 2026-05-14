package com.tecno_comfenalco.pa.infrastructure.assestsResources.controller;

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

import com.tecno_comfenalco.pa.application.assetsResources.command.actions.DeleteAssetsByIdCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.actions.GetAssetsByIdCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.actions.SubmitAssetsCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.actions.UpdateAssetsCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.DeleteAssetsByIdCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.GetAllAssetsCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.GetAssetsByIdCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.SubmitAssetsCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.UpdateAssetsCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.dto.request.SubmitAssetsRequestDto;
import com.tecno_comfenalco.pa.application.assetsResources.dto.request.UpdateAssetsRequestDto;
import com.tecno_comfenalco.pa.application.assetsResources.dto.response.DeleteAssetByIdResponseDto;
import com.tecno_comfenalco.pa.application.assetsResources.dto.response.GetAllAssetsResponseDto;
import com.tecno_comfenalco.pa.application.assetsResources.dto.response.GetAssetsByIdResponseDto;
import com.tecno_comfenalco.pa.application.assetsResources.dto.response.SubmitAssetsResponseDto;
import com.tecno_comfenalco.pa.application.assetsResources.dto.response.UpdateAssetsResponseDto;
import com.tecno_comfenalco.pa.application.assetsResources.usecases.AssetsResourcesUseCase;
import com.tecno_comfenalco.pa.infrastructure.security.CustomUserDetails;
import com.tecno_comfenalco.pa.shared.utils.http.DirectionEnum;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

@PreAuthorize("hasRole('DISTRIBUTOR')")
@RestController
@RequestMapping("/assets")
public class AssestsResourceController {

    private final AssetsResourcesUseCase assetsResourcesUseCase;

    public AssestsResourceController(AssetsResourcesUseCase assetsResourcesUseCase) {
        this.assetsResourcesUseCase = assetsResourcesUseCase;
    }

    @PostMapping
    public ResponseEntity<SubmitAssetsResponseDto> submitAssets(
            @RequestBody SubmitAssetsRequestDto dto,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        SubmitAssetsCommand cmd = new SubmitAssetsCommand(
                details.getUserId(),
                dto.productId(),
                dto.cloudinaryPublicId(),
                dto.url(),
                dto.name(),
                dto.isMain(),
                dto.format(),
                dto.width(),
                dto.height());

        SubmitAssetsCommandResult result = assetsResourcesUseCase.submitAssets(cmd);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SubmitAssetsResponseDto(
                result.assestId(),
                result.distributorId(),
                result.productId(),
                result.message()));
    }

    @PostMapping("/{assetId}")
    public ResponseEntity<UpdateAssetsResponseDto> updateAssets(
            @PathVariable UUID assetId,
            @RequestBody UpdateAssetsRequestDto dto,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        UpdateAssetsCommand cmd = new UpdateAssetsCommand(
                details.getUserId(),
                assetId,
                dto.url(),
                dto.name(),
                dto.isMain(),
                dto.format(),
                dto.width(),
                dto.height());

        UpdateAssetsCommandResult result = assetsResourcesUseCase.updateAssets(cmd);

        return ResponseEntity.ok().body(new UpdateAssetsResponseDto(
                result.distributorId(),
                result.assetId(),
                result.productId(),
                result.message()));
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<GetAssetsByIdResponseDto> getAssetsById(
            @PathVariable UUID assetId,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        GetAssetsByIdCommand cmd = new GetAssetsByIdCommand(details.getUserId(), assetId);
        GetAssetsByIdCommandResult result = assetsResourcesUseCase.getAssetsById(cmd);

        return ResponseEntity.ok().body(new GetAssetsByIdResponseDto(result.asset(), result.message()));
    }

    @GetMapping
    public ResponseEntity<GetAllAssetsResponseDto> getAllAssets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        RequestParams params = new RequestParams(name, page, size, sortBy, direction);

        GetAllAssetsCommandResult result = assetsResourcesUseCase.getAllAssets(details.getUserId(), params);

        return ResponseEntity.ok().body(new GetAllAssetsResponseDto(result.assets(), result.meta(), result.message()));
    }

    @DeleteMapping("/{assetId}")
    public ResponseEntity<DeleteAssetByIdResponseDto> deleteAssets(
            @PathVariable UUID assetId,
            Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        DeleteAssetsByIdCommand cmd = new DeleteAssetsByIdCommand(details.getUserId(), assetId);
        DeleteAssetsByIdCommandResult result = assetsResourcesUseCase.deleteAsset(cmd);

        return ResponseEntity.ok().body(new DeleteAssetByIdResponseDto(result.assetId(), result.message()));
    }
}
