package com.tecno_comfenalco.pa.infrastructure.distributor.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.application.auth.Exceptions.UserNotFoundException;
import com.tecno_comfenalco.pa.application.distributor.command.actions.EditDistributorCommand;
import com.tecno_comfenalco.pa.application.distributor.command.actions.ListAllDistributorsCommand;
import com.tecno_comfenalco.pa.application.distributor.command.actions.RegisterDistributorCommand;
import com.tecno_comfenalco.pa.application.distributor.command.response.EditDistributorCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.GetDistributorByIdCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.GetDistributorByNITCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.ListAllDistributorsCommandResult;
import com.tecno_comfenalco.pa.application.distributor.command.response.RegisterDistributorCommandResult;
import com.tecno_comfenalco.pa.application.distributor.dto.request.EditDistributorRequestDto;
import com.tecno_comfenalco.pa.application.distributor.dto.request.RegisterDistributorRequestDto;
import com.tecno_comfenalco.pa.application.distributor.dto.response.EditDistributorResponseDto;
import com.tecno_comfenalco.pa.application.distributor.dto.response.GetDistributorResponseDto;
import com.tecno_comfenalco.pa.application.distributor.dto.response.ListDistributorResponseDto;
import com.tecno_comfenalco.pa.application.distributor.dto.response.RegisterDistributorResponseDto;
import com.tecno_comfenalco.pa.application.distributor.usecase.DistributorUseCase;
import com.tecno_comfenalco.pa.application.orders.command.actions.GetAllOrderCommand;
import com.tecno_comfenalco.pa.application.orders.command.actions.GetOrderByIdCommand;
import com.tecno_comfenalco.pa.application.orders.command.response.GetAllOrderCommandResult;
import com.tecno_comfenalco.pa.application.orders.command.response.GetOrderByIdCommandResult;
import com.tecno_comfenalco.pa.application.orders.dto.response.GetAllOrderResponseDto;
import com.tecno_comfenalco.pa.application.orders.dto.response.GetOrderByIdResponseDto;
import com.tecno_comfenalco.pa.application.orders.usecases.OrderUsecase;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.actions.ChangeStatusBindingCommand;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.actions.ListAllBindingCommand;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.response.ChangeStatusBindingCommandResult;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.response.ListAllBindingCommandResult;
import com.tecno_comfenalco.pa.application.store.dto.storeBinding.request.ChangeStatusBindingStoreRequestDto;
import com.tecno_comfenalco.pa.application.store.dto.storeBinding.response.ChangeStatusBindingStoreResponseDto;
import com.tecno_comfenalco.pa.application.store.dto.storeBinding.response.ListAllBindingStoreResponseDto;
import com.tecno_comfenalco.pa.application.store.usecases.StoreBindingUseCase;
import com.tecno_comfenalco.pa.application.storeAssignment.command.actions.GetAllAsignmentStoresCommand;
import com.tecno_comfenalco.pa.application.storeAssignment.command.actions.GetAllAssignmentStoreByIdCommand;
import com.tecno_comfenalco.pa.application.storeAssignment.command.response.GetAllAsignmentStoresCommandResult;
import com.tecno_comfenalco.pa.application.storeAssignment.command.response.GetAllAssignmentStoreByIdCommandResult;
import com.tecno_comfenalco.pa.application.storeAssignment.dto.response.GetAllAssignmentsStoreByIdResponseDto;
import com.tecno_comfenalco.pa.application.storeAssignment.dto.response.GetAllStoresAssignmentResponseDto;
import com.tecno_comfenalco.pa.application.storeAssignment.usecases.StoreAssignmentUseCase;
import com.tecno_comfenalco.pa.infrastructure.security.CustomUserDetails;
import com.tecno_comfenalco.pa.shared.utils.http.DirectionEnum;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/distributor")
public class DistributorController {

        private final StoreBindingUseCase storeBindingUseCase;
        private final DistributorUseCase distributorUseCase;
        private final StoreAssignmentUseCase storeAssignmentUseCase;
        private final OrderUsecase orderUsecase;

        public DistributorController(
                        DistributorUseCase distributorUseCase,
                        StoreBindingUseCase storeBindingUseCase,
                        StoreAssignmentUseCase storeAssignmentUseCase,
                        OrderUsecase orderUsecase) {
                this.distributorUseCase = distributorUseCase;
                this.storeBindingUseCase = storeBindingUseCase;
                this.storeAssignmentUseCase = storeAssignmentUseCase;
                this.orderUsecase = orderUsecase;
        }

        @PostMapping
        public ResponseEntity<RegisterDistributorResponseDto> newDistributor(
                        @RequestBody @Valid RegisterDistributorRequestDto dtoDistributor) {
                RegisterDistributorCommand cmd = new RegisterDistributorCommand(dtoDistributor.userId(),
                                dtoDistributor.NIT(),
                                dtoDistributor.name(), dtoDistributor.phoneNumber(), dtoDistributor.email(),
                                dtoDistributor.direction());

                RegisterDistributorCommandResult result = distributorUseCase.registerDistributor(cmd);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new RegisterDistributorResponseDto(result.distributorId(), result.userId(),
                                                result.message()));
        }

        @PutMapping("/{id}")
        public ResponseEntity<EditDistributorResponseDto> editDistributor(@PathVariable UUID id,
                        @RequestBody @Valid EditDistributorRequestDto dtoDistributor) {
                EditDistributorCommand cmd = new EditDistributorCommand(id, dtoDistributor.name(),
                                dtoDistributor.phoneNumber(),
                                dtoDistributor.direction());

                EditDistributorCommandResult result = distributorUseCase.editDistributor(cmd);

                return ResponseEntity.status(HttpStatus.CREATED).body(new EditDistributorResponseDto(result.message()));
        }

        @GetMapping
        public ResponseEntity<ListDistributorResponseDto> listDistributors(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "10") Integer size,
                        @RequestParam(required = false, defaultValue = "name") String sortBy,
                        @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction) {

                RequestParams params = new RequestParams(name, page, size, sortBy, direction);
                ListAllDistributorsCommand cmd = new ListAllDistributorsCommand(params);

                ListAllDistributorsCommandResult result = distributorUseCase.listAllDistributors(cmd);

                return ResponseEntity.ok()
                                .body(new ListDistributorResponseDto(result.distributors(), result.meta(),
                                                result.message()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<GetDistributorResponseDto> showDistributor(@PathVariable UUID id) {
                GetDistributorByIdCommandResult result = distributorUseCase.getDistributorById(id);
                return ResponseEntity.ok().body(new GetDistributorResponseDto(result.distributor(), result.message()));
        }

        @GetMapping("/nit/{NIT}")
        public ResponseEntity<GetDistributorResponseDto> showDistributorByNIT(@PathVariable String NIT) {
                GetDistributorByNITCommandResult result = distributorUseCase.getDistributorByNIT(NIT);
                return ResponseEntity.ok().body(new GetDistributorResponseDto(result.distributor(), result.message()));
        }

        // ROL DISTRIBUTOR
        @PreAuthorize("hasRole('DISTRIBUTOR')")
        @GetMapping("/bindings-request")
        public ResponseEntity<ListAllBindingStoreResponseDto> listAllBindingStoresRequest(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "10") Integer size,
                        @RequestParam(required = false, defaultValue = "name") String sortBy,
                        @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
                        Authentication authentication) {

                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                RequestParams params = new RequestParams(name, page, size, sortBy, direction);

                ListAllBindingCommand cmd = new ListAllBindingCommand(details.getUserId(), params);
                ListAllBindingCommandResult result = storeBindingUseCase.listAllBindings(cmd);

                return ResponseEntity.ok()
                                .body(new ListAllBindingStoreResponseDto(result.bindings(), result.meta(),
                                                result.message()));
        }

        // ROL DISTRIBUTOR
        @PreAuthorize("hasRole('DISTRIBUTOR')")
        @PostMapping("/{bindingId}/status")
        public ResponseEntity<ChangeStatusBindingStoreResponseDto> changeStatusByBindingRequest(
                        @PathVariable UUID bindingId,
                        @RequestBody ChangeStatusBindingStoreRequestDto dto,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                ChangeStatusBindingCommand cmd = new ChangeStatusBindingCommand(
                                details.getUserId(),
                                bindingId,
                                dto.status());

                ChangeStatusBindingCommandResult result = storeBindingUseCase.changeStatusBindingByDistributor(cmd);

                return ResponseEntity.ok()
                                .body(new ChangeStatusBindingStoreResponseDto(result.bindingId(), result.status(),
                                                result.code(),
                                                result.message()));
        }

        @PreAuthorize("hasRole('DISTRIBUTOR')")
        @GetMapping("/get-stores")
        public ResponseEntity<GetAllStoresAssignmentResponseDto> getMyStores(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "10") Integer size,
                        @RequestParam(required = false, defaultValue = "name") String sortBy,
                        @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
                        Authentication authentication) {

                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
                RequestParams params = new RequestParams(name, page, size, sortBy, direction);
                GetAllAsignmentStoresCommand cmd = new GetAllAsignmentStoresCommand(
                                details.getUserId(),
                                null,
                                params);
                GetAllAsignmentStoresCommandResult result = storeAssignmentUseCase.getAllStoresByDistributor(cmd);

                return ResponseEntity.ok()
                                .body(new GetAllStoresAssignmentResponseDto(result.stores(), result.meta(),
                                                result.message()));
        }

        @PreAuthorize("hasRole('DISTRIBUTOR')")
        @GetMapping("/get-store/{storeId}")
        public ResponseEntity<GetAllAssignmentsStoreByIdResponseDto> getMyStoresById(
                        @PathVariable UUID storeId,
                        Authentication authentication) {

                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                GetAllAssignmentStoreByIdCommand cmd = new GetAllAssignmentStoreByIdCommand(
                                details.getUserId(),
                                null,
                                storeId);

                GetAllAssignmentStoreByIdCommandResult result = storeAssignmentUseCase.getStoreByIdWithAssignment(cmd);

                return ResponseEntity.ok()
                                .body(new GetAllAssignmentsStoreByIdResponseDto(result.store(), result.message()));
        }

        @PreAuthorize("hasRole('DISTRIBUTOR')")
        @GetMapping("/get-order/{orderId}")
        public ResponseEntity<GetOrderByIdResponseDto> getOrderById(
                        @PathVariable UUID orderId,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                String role = details.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .findFirst()
                                .orElseThrow(() -> new UserNotFoundException());

                GetOrderByIdCommand cmd = new GetOrderByIdCommand(orderId, details.getUserId(), null, role);

                GetOrderByIdCommandResult result = orderUsecase.getOrderById(cmd);

                return ResponseEntity.ok().body(new GetOrderByIdResponseDto(result.order(), result.message()));
        }

        @PreAuthorize("hasRole('DISTRIBUTOR')")
        @GetMapping("/get-order")
        public ResponseEntity<GetAllOrderResponseDto> getAllOrders(
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

                RequestParams params = new RequestParams(null, page, size, sortBy, direction);

                GetAllOrderCommand cmd = new GetAllOrderCommand(
                                details.getUserId(),
                                null,
                                role,
                                params);

                GetAllOrderCommandResult result = orderUsecase.getAllOrders(cmd);

                return ResponseEntity.ok().body(new GetAllOrderResponseDto(
                                result.orders(),
                                result.meta(),
                                result.message()));
        }
}
