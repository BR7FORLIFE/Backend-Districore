package com.tecno_comfenalco.pa.application.assetsResources.orchestator;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.assetsResources.command.actions.DeleteAssetsByIdCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.actions.GetAssetsByIdCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.actions.SubmitAssetsCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.actions.UpdateAssetsCommand;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.DeleteAssetsByIdCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.GetAllAssetsCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.GetAssetsByIdCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.SubmitAssetsCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.command.response.UpdateAssetsCommandResult;
import com.tecno_comfenalco.pa.application.assetsResources.exceptions.AssetsResourceAlreadyExistsException;
import com.tecno_comfenalco.pa.application.assetsResources.exceptions.AssetsResourceNotFoundException;
import com.tecno_comfenalco.pa.application.assetsResources.ports.IAssetsResourcesRepositoryPort;
import com.tecno_comfenalco.pa.application.assetsResources.usecases.AssetsResourcesUseCase;
import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
import com.tecno_comfenalco.pa.application.product.exceptions.ProductNotFoundException;
import com.tecno_comfenalco.pa.application.product.ports.IProductRepositoryPort;
import com.tecno_comfenalco.pa.domain.assestsResources.model.AssestsResourceModel;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.shared.utils.helper.ValidateQueryParams;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

@Service
public class AssetsResourcesUseCaseImp implements AssetsResourcesUseCase {

    private final IAssetsResourcesRepositoryPort assetsResourcesRepositoryPort;
    private final IDistributorRepositoryPort distributorRepositoryPort;
    private final IProductRepositoryPort productRepositoryPort;

    public AssetsResourcesUseCaseImp(
            IAssetsResourcesRepositoryPort assetsResourcesRepositoryPort,
            IDistributorRepositoryPort distributorRepositoryPort,
            IProductRepositoryPort productRepositoryPort) {
        this.assetsResourcesRepositoryPort = assetsResourcesRepositoryPort;
        this.distributorRepositoryPort = distributorRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public SubmitAssetsCommandResult submitAssets(SubmitAssetsCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        // verificamos sino hay un assets resource vinculado a un product id
        boolean existsAssetsResource = assetsResourcesRepositoryPort.existsByProductIdAndDistributorId(
                cmd.productId(),
                optDistributor.get().getId());

        if (existsAssetsResource) {
            throw new AssetsResourceAlreadyExistsException();
        }

        Optional<ProductModel> optProduct = productRepositoryPort.findByProductId(
                optDistributor.get().getId(),
                cmd.productId());

        if (optProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        AssestsResourceModel newAssets = AssestsResourceModel.createDraft(
                optDistributor.get().getId(),
                optProduct.get().getId(),
                cmd.cloudinaryPublicId(),
                cmd.url(),
                cmd.name(),
                cmd.isMain(),
                cmd.format(),
                cmd.width(),
                cmd.height());

        AssestsResourceModel saved = assetsResourcesRepositoryPort.save(newAssets);

        return new SubmitAssetsCommandResult(
                saved.getId(),
                saved.getDistributorId(),
                saved.getProductId(),
                "assets resource created succesfull!");
    }

    @Override
    public UpdateAssetsCommandResult updateAssets(UpdateAssetsCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        boolean existsAssets = assetsResourcesRepositoryPort.existsByIdAndDistributorId(
                cmd.assetId(),
                optDistributor.get().getId());

        if (!existsAssets) {
            throw new AssetsResourceNotFoundException();
        }

        Optional<AssestsResourceModel> optAssets = assetsResourcesRepositoryPort.findByIdAndDistributorId(
                cmd.assetId(),
                optDistributor.get().getId());

        if (optAssets.isEmpty()) {
            throw new AssetsResourceNotFoundException();
        }

        AssestsResourceModel updateAssets = AssestsResourceModel.createNew(
                optAssets.get().getId(),
                optAssets.get().getDistributorId(),
                optAssets.get().getProductId(),
                optAssets.get().getCloudinaryPublicId(),
                cmd.url(),
                cmd.name(),
                cmd.isMain(),
                cmd.format(),
                cmd.width(),
                cmd.height());

        AssestsResourceModel saved = assetsResourcesRepositoryPort.save(updateAssets);

        return new UpdateAssetsCommandResult(
                saved.getDistributorId(),
                saved.getId(),
                saved.getProductId(),
                "asset update succesfull!");
    }

    @Override
    public GetAssetsByIdCommandResult getAssetsById(GetAssetsByIdCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        Optional<AssestsResourceModel> optAsset = assetsResourcesRepositoryPort.findByIdAndDistributorId(
                cmd.assetId(),
                optDistributor.get().getId());

        if (optAsset.isEmpty()) {
            throw new AssetsResourceNotFoundException();
        }

        return new GetAssetsByIdCommandResult(optAsset.get(), "assets obtain succesfull!");
    }

    @Override
    public DeleteAssetsByIdCommandResult deleteAsset(DeleteAssetsByIdCommand cmd) {
        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(cmd.userDistributorId());

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        boolean existsAssetsResource = assetsResourcesRepositoryPort.existsByIdAndDistributorId(
                cmd.assetId(),
                optDistributor.get().getId());

        if (!existsAssetsResource) {
            throw new AssetsResourceNotFoundException();
        }

        assetsResourcesRepositoryPort.deleteAssetByIdAndDistributorId(cmd.assetId(), optDistributor.get().getId());

        return new DeleteAssetsByIdCommandResult(cmd.assetId(), "asset delete succesfull!");
    }

    @Override
    public GetAllAssetsCommandResult getAllAssets(UUID userDistributorId, RequestParams params) {
        ValidateQueryParams.validate(params);

        Optional<DistributorModel> optDistributor = distributorRepositoryPort.findByUserId(userDistributorId);

        if (optDistributor.isEmpty()) {
            throw new DistributorNotFoundException();
        }

        PagedResult<AssestsResourceModel> products = assetsResourcesRepositoryPort.findAllPaged(
                optDistributor.get().getId(),
                params.name(),
                params.page(),
                params.size(),
                params.sortBy(),
                params.direction().name());

        return new GetAllAssetsCommandResult(products.data(), products.meta(), "assets obtain sucessfull");
    }
}
