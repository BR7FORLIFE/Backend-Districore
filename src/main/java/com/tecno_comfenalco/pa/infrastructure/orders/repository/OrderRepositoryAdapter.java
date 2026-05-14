package com.tecno_comfenalco.pa.infrastructure.orders.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.orders.ports.IOrderRepositoryPort;
import com.tecno_comfenalco.pa.application.zGlobalExceptions.OperationNotAllowedException;
import com.tecno_comfenalco.pa.domain.orders.model.OrderModel;
import com.tecno_comfenalco.pa.infrastructure.orders.entity.OrderDocument;
import com.tecno_comfenalco.pa.infrastructure.orders.mapper.OrderMapper;
import com.tecno_comfenalco.pa.infrastructure.orders.repository.mongo.OrderRepository;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

@Repository
public class OrderRepositoryAdapter implements IOrderRepositoryPort {
        private final OrderRepository orderRepository;

        public OrderRepositoryAdapter(OrderRepository orderRepository) {
                this.orderRepository = orderRepository;
        }

        @Override
        public OrderModel save(OrderModel orderModel) {
                OrderDocument document = OrderMapper.toEntity(orderModel);
                OrderDocument saved = orderRepository.save(document);

                return OrderMapper.toDomain(saved);
        }

        @Override
        public Optional<OrderModel> findByIdAndDistributorId(UUID orderId, UUID distributorId) {
                return orderRepository.findByIdAndDistributorId(orderId, distributorId)
                                .map(OrderMapper::toDomain);
        }

        @Override
        public PagedResult<OrderModel> findAllPaged(UUID distributorId, Integer page, Integer size,
                        String sortBy, String direction) {

                Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC;

                Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

                Page<OrderDocument> result = orderRepository.findByDistributorId(distributorId, pageable);

                List<OrderModel> models = result.getContent()
                                .stream()
                                .map(OrderMapper::toDomain)
                                .collect(Collectors.toList());

                PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(),
                                result.getTotalElements(),
                                result.getTotalPages(), result.hasNext());

                return new PagedResult<OrderModel>(models, meta);
        }

        @Override
        public PagedResult<OrderModel> findAllPagedByAnotherRole(UUID distributorId, UUID userRoleId,
                        Integer page, Integer size, String sortBy, String direction, String role) {

                Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC
                                : Sort.Direction.DESC;
                Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

                Page<OrderDocument> result;

                result = switch (role) {
                        case "ROLE_STORE" -> orderRepository.findByDistributorIdAndStoreId(distributorId,
                                        userRoleId, pageable);
                        case "ROLE_PRESALES" ->
                                orderRepository.findByDistributorIdAndPresalesId(distributorId, userRoleId,
                                                pageable);
                        default -> throw new OperationNotAllowedException();
                };

                List<OrderModel> models = result.getContent().stream()
                                .map(OrderMapper::toDomain)
                                .collect(Collectors.toList());

                PaginationMeta meta = new PaginationMeta(
                                result.getNumber(),
                                result.getSize(),
                                result.getTotalElements(),
                                result.getTotalPages(),
                                result.hasNext());

                return new PagedResult<OrderModel>(models, meta);
        }
}
