package com.tecno_comfenalco.pa.application.orders.orchestator;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.application.distributor.exceptions.DistributorNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
import com.tecno_comfenalco.pa.application.inventory.exceptions.InventoryNotFoundException;
import com.tecno_comfenalco.pa.application.inventory.ports.IInventoryRepositoryPort;
import com.tecno_comfenalco.pa.application.orders.command.actions.ChangeStatusOrderCommand;
import com.tecno_comfenalco.pa.application.orders.command.actions.CreateOrderCommand;
import com.tecno_comfenalco.pa.application.orders.command.actions.GetAllOrderCommand;
import com.tecno_comfenalco.pa.application.orders.command.actions.GetOrderByIdCommand;
import com.tecno_comfenalco.pa.application.orders.command.response.ChangeStatusOrderCommandResult;
import com.tecno_comfenalco.pa.application.orders.command.response.CreateOrderCommandResult;
import com.tecno_comfenalco.pa.application.orders.command.response.GetAllOrderCommandResult;
import com.tecno_comfenalco.pa.application.orders.command.response.GetOrderByIdCommandResult;
import com.tecno_comfenalco.pa.application.orders.draft.OrderProductDraft;
import com.tecno_comfenalco.pa.application.orders.events.InventoryDiffEvent;
import com.tecno_comfenalco.pa.application.orders.exceptions.OrderExpirationException;
import com.tecno_comfenalco.pa.application.orders.exceptions.OrderNotFoundException;
import com.tecno_comfenalco.pa.application.orders.exceptions.UnprocessableOrderException;
import com.tecno_comfenalco.pa.application.orders.ports.IOrderRepositoryPort;
import com.tecno_comfenalco.pa.application.orders.usecases.OrderUsecase;
import com.tecno_comfenalco.pa.application.presales.exceptions.PresalesNotFoundException;
import com.tecno_comfenalco.pa.application.presales.ports.IPresalesRepositoryPort;
import com.tecno_comfenalco.pa.application.product.ports.IProductRepositoryPort;
import com.tecno_comfenalco.pa.application.store.exceptions.StoreNotFoundException;
import com.tecno_comfenalco.pa.application.store.ports.IStoreRepositoryPort;
import com.tecno_comfenalco.pa.application.storeAssignment.exceptions.NoStoreAssigmentNotFoundException;
import com.tecno_comfenalco.pa.application.storeAssignment.ports.IStoreAssignmentRepositoryPort;
import com.tecno_comfenalco.pa.application.zGlobalExceptions.OperationNotAllowedException;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.domain.inventory.models.InventoryModel;
import com.tecno_comfenalco.pa.domain.inventory.service.InventoryService;
import com.tecno_comfenalco.pa.domain.orders.model.OrderModel;
import com.tecno_comfenalco.pa.domain.orders.model.OrderProductModel;
import com.tecno_comfenalco.pa.domain.orders.services.OrderService;
import com.tecno_comfenalco.pa.domain.presales.model.PresalesModel;
import com.tecno_comfenalco.pa.domain.product.model.ProductModel;
import com.tecno_comfenalco.pa.domain.store.models.StoreModel;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;

@Service
public class OrderUseCaseImp implements OrderUsecase {

        @Value("${government.iva}")
        private Double IVA_GOVERNMENT;

        @Value("${order.expiration-order}")
        private Long expirationTimeOrder; // 3 dias en segundos

        private final IStoreRepositoryPort storeRepositoryPort;
        private final IProductRepositoryPort productRepositoryPort;
        private final IInventoryRepositoryPort inventoryRepositoryPort;
        private final IPresalesRepositoryPort presalesRepositoryPort;
        private final IDistributorRepositoryPort distributorRepositoryPort;
        private final IStoreAssignmentRepositoryPort storeAssignmentRepositoryPort;
        private final IOrderRepositoryPort orderRepositoryPort;

        // eventos asincronos
        private final ApplicationEventPublisher applicationEventPublisher;

        public OrderUseCaseImp(
                        IStoreRepositoryPort storeRepositoryPort,
                        IProductRepositoryPort productRepositoryPort,
                        IInventoryRepositoryPort inventoryRepositoryPort,
                        IPresalesRepositoryPort presalesRepositoryPort,
                        IDistributorRepositoryPort distributorRepositoryPort,
                        IStoreAssignmentRepositoryPort storeAssignmentRepositoryPort,
                        IOrderRepositoryPort orderRepositoryPort,
                        ApplicationEventPublisher applicationEventPublisher) {
                this.storeRepositoryPort = storeRepositoryPort;
                this.productRepositoryPort = productRepositoryPort;
                this.inventoryRepositoryPort = inventoryRepositoryPort;
                this.presalesRepositoryPort = presalesRepositoryPort;
                this.distributorRepositoryPort = distributorRepositoryPort;
                this.storeAssignmentRepositoryPort = storeAssignmentRepositoryPort;
                this.orderRepositoryPort = orderRepositoryPort;
                this.applicationEventPublisher = applicationEventPublisher;
        }

        @Override
        public CreateOrderCommandResult createOrder(CreateOrderCommand cmd) {
                Instant expirationTime = Instant.now().plusSeconds(this.expirationTimeOrder);

                // 1. verificamos si existen las entidades en el negocio
                Optional<StoreModel> optStore = storeRepositoryPort.findById(cmd.storeId());

                if (optStore.isEmpty()) {
                        throw new StoreNotFoundException();
                }

                Optional<DistributorModel> optDistributor = distributorRepositoryPort
                                .findById(cmd.distributorId());

                if (optDistributor.isEmpty()) {
                        throw new DistributorNotFoundException();
                }

                // 2. validamos que la tienda y la distribuidora tenga un binding vigente
                boolean existsAssigment = storeAssignmentRepositoryPort
                                .existsByStoreIdAndDistributorId(optStore.get().getId(),
                                                cmd.distributorId());

                if (!existsAssigment) {
                        throw new NoStoreAssigmentNotFoundException();
                }
                Optional<PresalesModel> optPresales = Optional.empty();

                if (cmd.role().equals("ROLE_PRESALES")) {
                        optPresales = presalesRepositoryPort.findPresalesByUserIdAndDistributorId(
                                        cmd.userId(),
                                        cmd.distributorId());

                        if (optPresales.isEmpty()) {
                                throw new PresalesNotFoundException();
                        }
                }

                // 3. verificamos la lista de productos que nos envian para crear la orden

                List<UUID> productsIds = cmd.products().stream()
                                .map(OrderProductDraft::productId)
                                .toList();

                List<ProductModel> getProductsById = productRepositoryPort.findByDistributorIdAndIdIn(
                                optDistributor.get().getId(),
                                productsIds);

                // 4. validamos en el dominio si hay productos que no existen
                OrderService.validateProduct(cmd.products(), getProductsById);

                // 5. validamos inventario para ver si cumple con la orden solicitada
                List<InventoryModel> inventoriesByProductsIds = inventoryRepositoryPort
                                .findByDistributorIdAndProductIdIn(optDistributor.get().getId(),
                                                productsIds);

                // validamos si existe el inventario (evitar el silent failure)
                if (inventoriesByProductsIds.isEmpty() && !productsIds.isEmpty()) {
                        throw new InventoryNotFoundException();
                }

                // agrupamos el inventatio o convertimos en un mapa donde la id del producto
                // tiene su respectivo inventario
                Map<UUID, InventoryModel> inventoryMap = inventoriesByProductsIds.stream()
                                .collect(Collectors.toMap(
                                                InventoryModel::getProductId,
                                                inventory -> inventory));

                // agrupamos los orderProduct para mejor operativa de inventario
                Map<UUID, OrderProductDraft> orderProductsDraft = cmd.products().stream()
                                .collect(Collectors.toMap(OrderProductDraft::productId,
                                                orderProduct -> orderProduct));

                /*
                 * La idea es no cancelar el pedido completo sino hay stock de un producto en
                 * especifico
                 * 
                 * 1. lo que hacemos es verificar el inventory y el product por un campo en
                 * comun lo cual es el productId
                 * 
                 * 2. llamamos el servicio verifyInventory para poder filtrar los ProductModel
                 * que cumplen con el stock
                 * sino cumple con el stock le asignamos null
                 * 
                 * 3. por ultimo filtramos los productos que cumplieron todas las condiciones
                 * anteriores
                 * mas el stock
                 */
                List<ProductModel> filterProductsWithoutInventory = getProductsById.stream()
                                .map(product -> {
                                        InventoryModel inventory = inventoryMap.get(product.getId());

                                        if (inventory == null) {
                                                return null;
                                        }

                                        OrderProductDraft orderProduct = orderProductsDraft
                                                        .get(product.getId());

                                        return InventoryService.verifyInventory(inventory, product,
                                                        orderProduct);
                                })
                                .filter(Objects::nonNull)
                                .toList();

                if (filterProductsWithoutInventory.isEmpty()) {
                        throw new UnprocessableOrderException();
                }

                // llamamos el servicion de order llamado logicAndCreateSubOrder que nos
                // permitira crear
                // los calculos correspondiente de OrderProductModel para cada productModel

                List<OrderProductModel> orderProducts = filterProductsWithoutInventory.stream()
                                .map(product -> {
                                        Long quantity = orderProductsDraft.get(product.getId())
                                                        .quantity();
                                        return OrderService.logicAndCreateSubOrder(product,
                                                        IVA_GOVERNMENT, quantity);
                                })
                                .toList();

                Double total = orderProducts
                                .stream()
                                .mapToDouble(OrderProductModel::getTotal)
                                .sum();

                // 6. calculamos los demas parametros de la orden en general
                OrderModel order = OrderModel.createDraft(
                                optDistributor.get().getId(),
                                optStore.get().getId(),
                                optPresales.map(PresalesModel::getId).orElse(null),
                                orderProducts,
                                null,
                                null,
                                IVA_GOVERNMENT,
                                total,
                                expirationTime);

                OrderModel saved = orderRepositoryPort.save(order);

                // lanzamos el evento asincrono para la resta de inventario
                applicationEventPublisher.publishEvent(new InventoryDiffEvent(
                                optDistributor.get().getId(),
                                orderProducts));

                // verificamos que la orden no sea la misma
                return new CreateOrderCommandResult(
                                saved.getId(),
                                saved.getNumberOrder(),
                                saved.getDistributorId(),
                                saved.getStoreId(),
                                saved.getStatusOrder(),
                                saved.getExpiration(),
                                "order created succesfull!");
        }

        @Override
        public GetOrderByIdCommandResult getOrderById(GetOrderByIdCommand cmd) {
                Optional<DistributorModel> optDistributor = Optional.empty();
                Optional<StoreModel> optStore = Optional.empty();
                Optional<PresalesModel> optPresales = Optional.empty();
                Optional<OrderModel> optOrder = Optional.empty();

                switch (cmd.role()) {
                        case "ROLE_DISTRIBUTOR":
                                optDistributor = distributorRepositoryPort.findByUserId(cmd.userId());

                                if (optDistributor.isEmpty()) {
                                        throw new DistributorNotFoundException();
                                }

                                optOrder = orderRepositoryPort.findByIdAndDistributorId(
                                                cmd.orderId(),
                                                optDistributor.get().getId());

                                if (optOrder.isEmpty()) {
                                        throw new OrderNotFoundException();
                                }

                                break;

                        case "ROLE_PRESALES":
                                optPresales = presalesRepositoryPort.findPresalesByUserIdAndDistributorId(
                                                cmd.userId(),
                                                cmd.distributorId());

                                if (optPresales.isEmpty()) {
                                        throw new PresalesNotFoundException();
                                }

                                optOrder = orderRepositoryPort.findByIdAndDistributorId(
                                                cmd.orderId(),
                                                cmd.distributorId());

                                if (optOrder.isEmpty()) {
                                        throw new OrderNotFoundException();
                                }

                                break;

                        case "ROLE_STORE":
                                optStore = storeRepositoryPort.findByUserId(cmd.userId());

                                if (optStore.isEmpty()) {
                                        throw new StoreNotFoundException();
                                }

                                boolean existsAssigment = storeAssignmentRepositoryPort
                                                .existsByStoreIdAndDistributorId(optStore.get().getId(),
                                                                cmd.distributorId());

                                if (!existsAssigment) {
                                        throw new NoStoreAssigmentNotFoundException();
                                }

                                optOrder = orderRepositoryPort.findByIdAndDistributorId(
                                                cmd.orderId(),
                                                cmd.distributorId());

                                if (optOrder.isEmpty()) {
                                        throw new OrderNotFoundException();
                                }

                                break;

                        default:
                                throw new OperationNotAllowedException();
                }

                return new GetOrderByIdCommandResult(optOrder.get(), "order obtain succesfull!");
        }

        @Override
        public GetAllOrderCommandResult getAllOrders(GetAllOrderCommand cmd) {
                Optional<DistributorModel> optDistributor = Optional.empty();
                Optional<StoreModel> optStore = Optional.empty();
                Optional<PresalesModel> optPresales = Optional.empty();
                PagedResult<OrderModel> orders = null;

                switch (cmd.role()) {
                        case "ROLE_DISTRIBUTOR":
                                optDistributor = distributorRepositoryPort.findByUserId(cmd.userId());

                                if (optDistributor.isEmpty()) {
                                        throw new DistributorNotFoundException();
                                }

                                orders = orderRepositoryPort.findAllPaged(
                                                optDistributor.get().getId(),
                                                cmd.params().page(),
                                                cmd.params().size(),
                                                cmd.params().sortBy(),
                                                cmd.params().direction().name());

                                break;

                        case "ROLE_PRESALES":
                                optPresales = presalesRepositoryPort.findPresalesByUserIdAndDistributorId(
                                                cmd.userId(),
                                                cmd.distributorId());

                                if (optPresales.isEmpty()) {
                                        throw new PresalesNotFoundException();
                                }

                                orders = orderRepositoryPort.findAllPagedByAnotherRole(
                                                cmd.distributorId(),
                                                optPresales.get().getId(),
                                                cmd.params().page(),
                                                cmd.params().size(),
                                                cmd.params().sortBy(),
                                                cmd.params().direction().name(),
                                                cmd.role());
                                break;

                        case "ROLE_STORE":
                                optStore = storeRepositoryPort.findByUserId(cmd.userId());

                                if (optStore.isEmpty()) {
                                        throw new StoreNotFoundException();
                                }

                                boolean existsAssigment = storeAssignmentRepositoryPort
                                                .existsByStoreIdAndDistributorId(optStore.get().getId(),
                                                                cmd.distributorId());

                                if (!existsAssigment) {
                                        throw new NoStoreAssigmentNotFoundException();
                                }

                                orders = orderRepositoryPort.findAllPagedByAnotherRole(
                                                cmd.distributorId(),
                                                optStore.get().getId(),
                                                cmd.params().page(),
                                                cmd.params().size(),
                                                cmd.params().sortBy(),
                                                cmd.params().direction().name(),
                                                cmd.role());
                                break;

                        default:
                                throw new OperationNotAllowedException();
                }

                return new GetAllOrderCommandResult(orders.data(), orders.meta(), "orders obtain succesfull!");
        }

        @Override
        public ChangeStatusOrderCommandResult changeStatusOrder(ChangeStatusOrderCommand cmd) {
                Optional<DistributorModel> optDistributor = distributorRepositoryPort
                                .findByUserId(cmd.userDistributorId());

                if (optDistributor.isEmpty()) {
                        throw new DistributorNotFoundException();
                }

                Optional<OrderModel> optOrder = orderRepositoryPort.findByIdAndDistributorId(
                                cmd.orderId(),
                                optDistributor.get().getId());

                if (optOrder.isEmpty()) {
                        throw new OrderNotFoundException();
                }

                if (optOrder.get().getExpiration().isBefore(Instant.now())) {
                        throw new OrderExpirationException();
                }

                OrderModel updateStatusOrder = OrderService.transitionedStateRequestEnum(
                                optOrder.get(),
                                cmd.orderStatus());

                OrderModel saved = orderRepositoryPort.save(updateStatusOrder);

                return new ChangeStatusOrderCommandResult(saved.getId(), saved.getDistributorId(),
                                "change status realized succesfull!");
        }
}
