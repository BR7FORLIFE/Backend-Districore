package com.tecno_comfenalco.pa.infrastructure.presales.controller;

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
import com.tecno_comfenalco.pa.application.orders.command.actions.GetAllOrderCommand;
import com.tecno_comfenalco.pa.application.orders.command.actions.GetOrderByIdCommand;
import com.tecno_comfenalco.pa.application.orders.command.response.GetAllOrderCommandResult;
import com.tecno_comfenalco.pa.application.orders.command.response.GetOrderByIdCommandResult;
import com.tecno_comfenalco.pa.application.orders.dto.response.GetAllOrderResponseDto;
import com.tecno_comfenalco.pa.application.orders.dto.response.GetOrderByIdResponseDto;
import com.tecno_comfenalco.pa.application.orders.usecases.OrderUsecase;
import com.tecno_comfenalco.pa.application.presales.command.actions.EditPresalesCommand;
import com.tecno_comfenalco.pa.application.presales.command.actions.GetPresalesInfoCommand;
import com.tecno_comfenalco.pa.application.presales.command.actions.ListPresalesCommand;
import com.tecno_comfenalco.pa.application.presales.command.actions.RegisterPresalesCommand;
import com.tecno_comfenalco.pa.application.presales.command.response.EditPresalesCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.GetPresalesByIdCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.GetPresalesInfoCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.ListPresalesCommandResult;
import com.tecno_comfenalco.pa.application.presales.command.response.PresalesBindingResponseDto;
import com.tecno_comfenalco.pa.application.presales.command.response.RegisterPresalesCommandResult;
import com.tecno_comfenalco.pa.application.presales.dto.request.EditPresalesRequestDto;
import com.tecno_comfenalco.pa.application.presales.dto.request.RegisterPresalesRequestDto;
import com.tecno_comfenalco.pa.application.presales.dto.response.EditPresalesResponseDto;
import com.tecno_comfenalco.pa.application.presales.dto.response.GetPresaleInfoResponseDto;
import com.tecno_comfenalco.pa.application.presales.dto.response.GetPresalesByIdResponseDto;
import com.tecno_comfenalco.pa.application.presales.dto.response.ListPresalesResponseDto;
import com.tecno_comfenalco.pa.application.presales.dto.response.RegisterPresalesResponseDto;
import com.tecno_comfenalco.pa.application.presales.usecases.PresalesUseCase;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.actions.SendBindingCommand;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.response.SendBindingCommandResult;
import com.tecno_comfenalco.pa.application.store.dto.storeBinding.request.SendBindingStoreRequestDto;
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

@PreAuthorize("hasRole('DISTRIBUTOR')")
@RestController
@RequestMapping("/presales")
public class PresalesController {

        private final PresalesUseCase presalesUseCase;
        private final StoreBindingUseCase storeBindingUseCase;
        private final OrderUsecase orderUsecase;
        private final StoreAssignmentUseCase storeAssignmentUseCase;

        public PresalesController(
                        PresalesUseCase presalesUseCase,
                        StoreBindingUseCase storeBindingUseCase,
                        OrderUsecase orderUsecase,
                        StoreAssignmentUseCase storeAssignmentUseCase) {
                this.presalesUseCase = presalesUseCase;
                this.storeBindingUseCase = storeBindingUseCase;
                this.orderUsecase = orderUsecase;
                this.storeAssignmentUseCase = storeAssignmentUseCase;
        }

        @PostMapping
        public ResponseEntity<RegisterPresalesResponseDto> newPresales(
                        @RequestBody @Valid RegisterPresalesRequestDto dtoPresales, Authentication authentication) {

                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                RegisterPresalesCommand cmd = new RegisterPresalesCommand(details.getUserId(), dtoPresales.username(),
                                dtoPresales.password(), dtoPresales.name(),
                                dtoPresales.phoneNumber(),
                                dtoPresales.email(), dtoPresales.documentType(), dtoPresales.documentNumber());

                RegisterPresalesCommandResult result = presalesUseCase.registerPresales(cmd);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new RegisterPresalesResponseDto(result.presalesId(), result.distributorId(),
                                                result.message()));
        }

        @PutMapping("/{id}")
        public ResponseEntity<EditPresalesResponseDto> editPresales(@PathVariable UUID id,
                        @RequestBody @Valid EditPresalesRequestDto dtoPresales, Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                EditPresalesCommand cmd = new EditPresalesCommand(details.getUserId(), id, dtoPresales.name(),
                                dtoPresales.phoneNumber());

                EditPresalesCommandResult result = presalesUseCase.editPresales(cmd);

                return ResponseEntity.status(HttpStatus.OK)
                                .body(new EditPresalesResponseDto(result.distributorId(), result.presalesId(),
                                                result.message()));
        }

        @GetMapping
        public ResponseEntity<ListPresalesResponseDto> listPresales(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "10") Integer size,
                        @RequestParam(required = false, defaultValue = "name") String sortBy,
                        @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                RequestParams params = new RequestParams(name, page, size, sortBy, direction);
                ListPresalesCommand cmd = new ListPresalesCommand(details.getUserId(), params);

                ListPresalesCommandResult result = presalesUseCase.listPresales(cmd);

                return ResponseEntity.ok()
                                .body(new ListPresalesResponseDto(result.presalesModels(), result.meta(),
                                                result.message()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<GetPresalesByIdResponseDto> showPresales(@PathVariable UUID id,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
                GetPresalesByIdCommandResult result = presalesUseCase.showPresales(details.getUserId(), id);

                return ResponseEntity.ok().body(new GetPresalesByIdResponseDto(result.presales(), result.message()));
        }

        @PreAuthorize("hasRole('PRESALES')")
        @GetMapping("/info")
        public ResponseEntity<GetPresaleInfoResponseDto> getAuthenticatedPresalesId(Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
                GetPresalesInfoCommand cmd = new GetPresalesInfoCommand(details.getUserId(),
                                details.getDistributorId());

                GetPresalesInfoCommandResult result = presalesUseCase.getPresalesInfo(cmd);

                return ResponseEntity.ok().body(new GetPresaleInfoResponseDto(result.presale(), result.message()));
        }

        @PreAuthorize("hasRole('PRESALES')")
        @PostMapping("/send-binding")
        public ResponseEntity<PresalesBindingResponseDto> sendBindingStoreRequest(Authentication authentication,
                        @RequestBody @Valid SendBindingStoreRequestDto dto) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                SendBindingCommand cmd = new SendBindingCommand(details.getUserId(), details.getDistributorId(),
                                dto.nit(), dto.tempName());

                SendBindingCommandResult result = storeBindingUseCase.sendBindingStoreRequest(cmd);

                return ResponseEntity.ok().body(new PresalesBindingResponseDto(result.bindingId(),
                                result.distributorId(), result.status(), result.createAt(), result.message()));
        }

        @PreAuthorize("hasRole('PRESALES')")
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

                GetOrderByIdCommand cmd = new GetOrderByIdCommand(orderId, details.getUserId(),
                                details.getDistributorId(), role);

                GetOrderByIdCommandResult result = orderUsecase.getOrderById(cmd);

                return ResponseEntity.ok().body(new GetOrderByIdResponseDto(result.order(), result.message()));
        }

        @PreAuthorize("hasRole('PRESALES')")
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
                                details.getDistributorId(),
                                role,
                                params);

                GetAllOrderCommandResult result = orderUsecase.getAllOrders(cmd);

                return ResponseEntity.ok().body(new GetAllOrderResponseDto(
                                result.orders(),
                                result.meta(),
                                result.message()));
        }

        @PreAuthorize("hasRole('PRESALES')")
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
                                null,
                                details.getDistributorId(),
                                params);

                GetAllAsignmentStoresCommandResult result = storeAssignmentUseCase.getAllStoresByDistributor(cmd);

                return ResponseEntity.ok()
                                .body(new GetAllStoresAssignmentResponseDto(result.stores(), result.meta(),
                                                result.message()));
        }

        @PreAuthorize("hasRole('PRESALES')")
        @GetMapping("/get-store/{storeId}")
        public ResponseEntity<GetAllAssignmentsStoreByIdResponseDto> getMyStoresById(
                        @PathVariable UUID storeId,
                        Authentication authentication) {

                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                GetAllAssignmentStoreByIdCommand cmd = new GetAllAssignmentStoreByIdCommand(
                                null,
                                details.getDistributorId(),
                                storeId);

                GetAllAssignmentStoreByIdCommandResult result = storeAssignmentUseCase.getStoreByIdWithAssignment(cmd);

                return ResponseEntity.ok()
                                .body(new GetAllAssignmentsStoreByIdResponseDto(result.store(), result.message()));
        }
}
