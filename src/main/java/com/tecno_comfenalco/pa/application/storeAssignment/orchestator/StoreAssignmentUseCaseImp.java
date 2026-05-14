package com.tecno_comfenalco.pa.application.storeAssignment.orchestator;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
import com.tecno_comfenalco.pa.application.store.exceptions.StoreNotFoundException;
import com.tecno_comfenalco.pa.application.store.ports.IStoreBindingRepositoryPort;
import com.tecno_comfenalco.pa.application.store.ports.IStoreRepositoryPort;
import com.tecno_comfenalco.pa.application.storeAssignment.command.actions.GetAllAsignmentDistributorsCommand;
import com.tecno_comfenalco.pa.application.storeAssignment.command.actions.GetAllAsignmentStoresCommand;
import com.tecno_comfenalco.pa.application.storeAssignment.command.actions.GetAllAssignmentStoreByIdCommand;
import com.tecno_comfenalco.pa.application.storeAssignment.command.response.GetAllAsignmentDistributorsCommandResult;
import com.tecno_comfenalco.pa.application.storeAssignment.command.response.GetAllAsignmentStoresCommandResult;
import com.tecno_comfenalco.pa.application.storeAssignment.command.response.GetAllAssignmentStoreByIdCommandResult;
import com.tecno_comfenalco.pa.application.storeAssignment.exceptions.NoStoreAssigmentNotFoundException;
import com.tecno_comfenalco.pa.application.storeAssignment.exceptions.NotDistributorsAssignmentException;
import com.tecno_comfenalco.pa.application.storeAssignment.ports.IStoreAssignmentRepositoryPort;
import com.tecno_comfenalco.pa.application.storeAssignment.usecases.StoreAssignmentUseCase;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.domain.store.models.StoreModel;
import com.tecno_comfenalco.pa.domain.storeAssignment.models.StoreAssignmentModel;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

@Service
public class StoreAssignmentUseCaseImp implements StoreAssignmentUseCase {

        private final IStoreAssignmentRepositoryPort storeAssignmentRepositoryPort;
        private final IStoreRepositoryPort storeRepositoryPort;
        private final IDistributorRepositoryPort distributorRepositoryPort;

        public StoreAssignmentUseCaseImp(
                        IStoreAssignmentRepositoryPort storeAssignmentRepositoryPort,
                        IStoreRepositoryPort storeRepositoryPort,
                        IStoreBindingRepositoryPort storeBindingRepositoryPort,
                        IDistributorRepositoryPort distributorRepositoryPort) {
                this.storeAssignmentRepositoryPort = storeAssignmentRepositoryPort;
                this.storeRepositoryPort = storeRepositoryPort;
                this.distributorRepositoryPort = distributorRepositoryPort;
        }

        @Override
        public GetAllAsignmentDistributorsCommandResult getAllDistributorByStore(
                        GetAllAsignmentDistributorsCommand cmd) {
                // obtenemos la informacion de la tienda
                Optional<StoreModel> optStoreModel = storeRepositoryPort.findByUserId(cmd.userStoreId());

                if (optStoreModel.isEmpty()) {
                        throw new StoreNotFoundException();
                }

                // obtenemos todos los assignments vinculados a la tienda
                List<StoreAssignmentModel> allAsignments = storeAssignmentRepositoryPort
                                .findAllByStoreId(optStoreModel.get().getId());

                // agrupamos en un Set todas las Id de las distribuidoras ademas de eliminar
                // duplicados
                Set<UUID> distributorsId = allAsignments
                                .stream()
                                .map(StoreAssignmentModel::getDistributorId)
                                .collect(Collectors.toSet());

                // validamos que haya una coleccion de ids
                if (distributorsId == null || distributorsId.isEmpty()) {
                        throw new NotDistributorsAssignmentException();
                }

                PagedResult<DistributorModel> distributorsAssignments = distributorRepositoryPort.findByIdIn(
                                distributorsId,
                                cmd.params().name(),
                                cmd.params().page(),
                                cmd.params().size(),
                                cmd.params().sortBy(),
                                cmd.params().direction().name());

                return new GetAllAsignmentDistributorsCommandResult(distributorsAssignments.data(),
                                distributorsAssignments.meta(), "distributors assignments obtain succesfull!");
        }

        @Override
        public GetAllAsignmentStoresCommandResult getAllStoresByDistributor(GetAllAsignmentStoresCommand cmd) {

                Optional<DistributorModel> optDistributor = Optional.empty();

                // si es la distribuidora quien requiera la informacion
                if (cmd.userDistributorId() != null) {
                        optDistributor = distributorRepositoryPort
                                        .findByUserId(cmd.userDistributorId());
                }

                // si es el preventista quien requiera la informacion
                if (cmd.distributorId() != null) {
                        optDistributor = distributorRepositoryPort.findById(cmd.distributorId());
                }

                if (optDistributor.isEmpty()) {
                        throw new DistributorNotFoundException();
                }

                // obtenemos todos los assignments vinculados a la distribuidora
                List<StoreAssignmentModel> allAsignments = storeAssignmentRepositoryPort
                                .findAllByDistributorId(optDistributor.get().getId());

                // agrupamos en un Set todas las Id de las distribuidoras ademas de eliminar
                // duplicados
                Set<UUID> storesId = allAsignments
                                .stream()
                                .map(StoreAssignmentModel::getStoreId)
                                .collect(Collectors.toSet());

                // validamos que haya una coleccion de ids
                if (storesId == null || storesId.isEmpty()) {
                        throw new NotDistributorsAssignmentException();
                }

                PagedResult<StoreModel> storesAssignments = storeRepositoryPort.findByIdIn(
                                storesId,
                                cmd.params().name(),
                                cmd.params().page(),
                                cmd.params().size(),
                                cmd.params().sortBy(),
                                cmd.params().direction().name());

                return new GetAllAsignmentStoresCommandResult(storesAssignments.data(),
                                storesAssignments.meta(), "distributors assignments obtain succesfull!");
        }

        @Override
        public GetAllAssignmentStoreByIdCommandResult getStoreByIdWithAssignment(GetAllAssignmentStoreByIdCommand cmd) {
                Optional<DistributorModel> optDistributor = Optional.empty();

                // si es la distribuidora quien requiera la informacion
                if (cmd.userDistributorId() != null) {
                        optDistributor = distributorRepositoryPort
                                        .findByUserId(cmd.userDistributorId());
                }

                // si es el preventista quien requiera la informacion
                if (cmd.distributorId() != null) {
                        optDistributor = distributorRepositoryPort.findById(cmd.distributorId());
                }

                if (optDistributor.isEmpty()) {
                        throw new DistributorNotFoundException();
                }

                // revisamos si hay un asigment entre la distribuidora y la tienda
                boolean existsAssigment = storeAssignmentRepositoryPort
                                .existsByStoreIdAndDistributorId(cmd.storeId(), optDistributor.get().getId());

                if (!existsAssigment) {
                        throw new NoStoreAssigmentNotFoundException();
                }

                Optional<StoreModel> optStore = storeRepositoryPort.findById(cmd.storeId());

                if (optStore.isEmpty()) {
                        throw new StoreNotFoundException();
                }

                return new GetAllAssignmentStoreByIdCommandResult(optStore.get(), "store obtain succesfull!");
        }
}
