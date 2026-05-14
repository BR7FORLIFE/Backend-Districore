package com.tecno_comfenalco.pa.application.assetsResources.usecases;

import java.util.UUID;

import com.tecno_comfenalco.pa.application.assetsResources.command.actions.DeleteAssetsByIdCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.actions.GetAssetsByIdCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.actions.SubmitAssetsCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.actions.UpdateAssetsCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.DeleteAssetsByIdCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.GetAllAssetsCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.GetAssetsByIdCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.SubmitAssetsCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.UpdateAssetsCommandResult;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

public interface AssetsResourcesUseCase {
    SubmitAssetsCommandResult submitAssets(SubmitAssetsCommand cmd);

    UpdateAssetsCommandResult updateAssets(UpdateAssetsCommand cmd);

    GetAssetsByIdCommandResult getAssetsById(GetAssetsByIdCommand cmd);

    DeleteAssetsByIdCommandResult deleteAsset(DeleteAssetsByIdCommand cmd);

    GetAllAssetsCommandResult getAllAssets(UUID userDistributorId, RequestParams params);
}
