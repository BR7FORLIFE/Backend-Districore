package com.tecno_comfenalco.pa.infrastructure.store.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.tecno_comfenalco.pa.application.store.command.actions.GetAllCatalogByDistributorCommand;
import com.tecno_comfenalco.pa.application.store.command.actions.GetMyCatalogCommand;
import com.tecno_comfenalco.pa.application.store.command.actions.ListAllStoresCommand;
import com.tecno_comfenalco.pa.application.store.command.actions.RegisterStoreCommand;
import com.tecno_comfenalco.pa.application.store.command.actions.UpdateStoreCommand;
import com.tecno_comfenalco.pa.application.store.command.response.DisabledStoreCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.GetAllCatalogByDistributorCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.GetMyCatalogCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.GetStoreByIdCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.ListAllStoresCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.MeStoreCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.RegisterStoreCommandResult;
import com.tecno_comfenalco.pa.application.store.command.response.UpdateStoreCommandResult;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.actions.ReceiveAceptationByStoreCommand;
import com.tecno_comfenalco.pa.application.store.command.storeBinding.response.ReceiveAceptationByStoreCommandResult;
import com.tecno_comfenalco.pa.application.store.dto.request.RegisterStoreRequestDto;
import com.tecno_comfenalco.pa.application.store.dto.request.UpdateStoreRequestDto;
import com.tecno_comfenalco.pa.application.store.dto.response.DisabledStoreByIdResponseDto;
import com.tecno_comfenalco.pa.application.store.dto.response.GetAllCatalogByDistributorResponseDto;
import com.tecno_comfenalco.pa.application.store.dto.response.GetMyCatalogResponseDto;
import com.tecno_comfenalco.pa.application.store.dto.response.GetStoreyByIdResponseDto;
import com.tecno_comfenalco.pa.application.store.dto.response.ListAllStoresResponseDto;
import com.tecno_comfenalco.pa.application.store.dto.response.MeStoreResponseDto;
import com.tecno_comfenalco.pa.application.store.dto.response.RegisterStoreResponseDto;
import com.tecno_comfenalco.pa.application.store.dto.response.UpdateStoreResponseDto;
import com.tecno_comfenalco.pa.application.store.dto.storeBinding.response.ReceiveAceptationByStoreResponseDto;
import com.tecno_comfenalco.pa.application.store.usecases.StoreBindingUseCase;
import com.tecno_comfenalco.pa.application.store.usecases.StoreUseCase;
import com.tecno_comfenalco.pa.application.storeAssignment.command.actions.GetAllAsignmentDistributorsCommand;
import com.tecno_comfenalco.pa.application.storeAssignment.command.response.GetAllAsignmentDistributorsCommandResult;
import com.tecno_comfenalco.pa.application.storeAssignment.dto.response.GetAllDistributorsAssignmentResponseDto;
import com.tecno_comfenalco.pa.application.storeAssignment.usecases.StoreAssignmentUseCase;
import com.tecno_comfenalco.pa.infrastructure.security.CustomUserDetails;
import com.tecno_comfenalco.pa.shared.utils.http.DirectionEnum;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/store")
public class StoreController {

        private final StoreBindingUseCase storeBindingUseCase;
        private final StoreUseCase storeUseCase;
        private final StoreAssignmentUseCase storeAssignmentUseCase;
        private final OrderUsecase orderUsecase;

        public StoreController(
                        StoreUseCase storeUseCase,
                        StoreBindingUseCase storeBindingUseCase,
                        StoreAssignmentUseCase storeAssignmentUseCase,
                        OrderUsecase orderUsecase) {
                this.storeUseCase = storeUseCase;
                this.storeBindingUseCase = storeBindingUseCase;
                this.storeAssignmentUseCase = storeAssignmentUseCase;
                this.orderUsecase = orderUsecase;
        }

        @PostMapping
        public ResponseEntity<RegisterStoreResponseDto> registerStore(@RequestBody RegisterStoreRequestDto dto) {
                RegisterStoreCommand cmd = new RegisterStoreCommand(dto.userId(), dto.name(), dto.nit(),
                                dto.phoneNumber(),
                                dto.email(), dto.direction());

                RegisterStoreCommandResult result = storeUseCase.registerStore(cmd);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new RegisterStoreResponseDto(result.storeId(), result.userId(),
                                                result.message()));
        }

        @GetMapping
        public ResponseEntity<ListAllStoresResponseDto> listAllStores(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "10") Integer size,
                        @RequestParam(required = false, defaultValue = "name") String sortBy,
                        @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction) {
                RequestParams params = new RequestParams(name, page, size, sortBy, direction);
                ListAllStoresCommand cmd = new ListAllStoresCommand(params);
                ListAllStoresCommandResult result = storeUseCase.listAllStores(cmd);

                return ResponseEntity.ok()
                                .body(new ListAllStoresResponseDto(result.stores(), result.meta(), result.message()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<GetStoreyByIdResponseDto> getStoreById(@PathVariable UUID id) {
                GetStoreByIdCommandResult result = storeUseCase.getStoreById(id);
                return ResponseEntity.ok().body(new GetStoreyByIdResponseDto(result.store(), result.message()));
        }

        @PatchMapping("/{id}")
        public ResponseEntity<UpdateStoreResponseDto> updateStore(@PathVariable UUID id,
                        @RequestBody UpdateStoreRequestDto dto) {

                UpdateStoreCommand cmd = new UpdateStoreCommand(id, dto.name(), dto.phoneNumber(), dto.direction());
                UpdateStoreCommandResult result = storeUseCase.updateStore(cmd);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new UpdateStoreResponseDto(result.storeId(), result.message()));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<DisabledStoreByIdResponseDto> disabledStore(UUID id) {
                DisabledStoreCommandResult result = storeUseCase.disabledStore(id);
                return ResponseEntity.ok().body(new DisabledStoreByIdResponseDto(result.userId(), result.message()));
        }

        @PreAuthorize("hasRole('STORE')")
        @GetMapping("/verify-binding")
        public ResponseEntity<ReceiveAceptationByStoreResponseDto> acceptBindingByDistributor(
                        @RequestParam(required = true) String token,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                ReceiveAceptationByStoreCommand cmd = new ReceiveAceptationByStoreCommand(details.getUserId(), token);
                ReceiveAceptationByStoreCommandResult result = storeBindingUseCase.receiveAceptationByStore(cmd);

                return ResponseEntity.ok().body(new ReceiveAceptationByStoreResponseDto(
                                result.bindingId(),
                                result.distributorId(),
                                result.nit(),
                                result.status(),
                                result.isConsumed(),
                                result.consumedAt(),
                                result.message()));
        }

        @PreAuthorize("hasRole('STORE')")
        @GetMapping("/get-distributors")
        public ResponseEntity<GetAllDistributorsAssignmentResponseDto> getMyDistributors(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "10") Integer size,
                        @RequestParam(required = false, defaultValue = "name") String sortBy,
                        @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
                RequestParams params = new RequestParams(name, page, size, sortBy, direction);

                GetAllAsignmentDistributorsCommand cmd = new GetAllAsignmentDistributorsCommand(details.getUserId(),
                                params);
                GetAllAsignmentDistributorsCommandResult result = storeAssignmentUseCase.getAllDistributorByStore(cmd);

                return ResponseEntity.ok().body(
                                new GetAllDistributorsAssignmentResponseDto(result.distributors(), result.meta(),
                                                result.message()));
        }

        @PreAuthorize("hasRole('STORE')")
        @GetMapping("/{distributorId}/catalog/{catalogId}")
        public ResponseEntity<GetMyCatalogResponseDto> getMyCatalogByStore(
                        @PathVariable UUID distributorId,
                        @PathVariable UUID catalogId,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                GetMyCatalogCommand cmd = new GetMyCatalogCommand(details.getUserId(), distributorId, catalogId);
                GetMyCatalogCommandResult result = storeUseCase.getMyCatalog(cmd);

                return ResponseEntity.ok().body(new GetMyCatalogResponseDto(result.catalog(), result.message()));
        }

        @PreAuthorize("hasRole('STORE')")
        @GetMapping("/{distributorId}/catalog")
        public ResponseEntity<GetAllCatalogByDistributorResponseDto> getMyCatalogById(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "10") Integer size,
                        @RequestParam(required = false, defaultValue = "name") String sortBy,
                        @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
                        @PathVariable UUID distributorId,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                RequestParams params = new RequestParams(name, page, size, sortBy, direction);

                GetAllCatalogByDistributorCommand cmd = new GetAllCatalogByDistributorCommand(distributorId,
                                details.getUserId(), params);

                GetAllCatalogByDistributorCommandResult result = storeUseCase.getAllCatalogByDistributor(cmd);

                return ResponseEntity.ok().body(new GetAllCatalogByDistributorResponseDto(
                                result.catalogs(),
                                result.meta(),
                                result.message()));
        }

        @PreAuthorize("hasRole('STORE')")
        @GetMapping("/me")
        public ResponseEntity<MeStoreResponseDto> me(
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
                MeStoreCommandResult result = storeUseCase.me(details.getUserId());

                return ResponseEntity.ok().body(new MeStoreResponseDto(result.store(), result.message()));
        }

        @PreAuthorize("hasRole('STORE')")
        @GetMapping("/{distributorId}/order/{orderId}")
        public ResponseEntity<GetOrderByIdResponseDto> getOrderById(
                        @PathVariable UUID orderId,
                        @PathVariable UUID distributorId,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                String role = details.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .findFirst()
                                .orElseThrow(() -> new UserNotFoundException());

                GetOrderByIdCommand cmd = new GetOrderByIdCommand(orderId, details.getUserId(), distributorId, role);
                GetOrderByIdCommandResult result = orderUsecase.getOrderById(cmd);

                return ResponseEntity.ok().body(new GetOrderByIdResponseDto(result.order(), result.message()));
        }

        @PreAuthorize("hasRole('STORE')")
        @GetMapping("/{distributorId}/order")
        public ResponseEntity<GetAllOrderResponseDto> getAllOrders(
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "10") Integer size,
                        @RequestParam(required = false, defaultValue = "name") String sortBy,
                        @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
                        @PathVariable UUID distributorId,
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
                                distributorId,
                                role,
                                params);

                GetAllOrderCommandResult result = orderUsecase.getAllOrders(cmd);

                return ResponseEntity.ok().body(new GetAllOrderResponseDto(
                                result.orders(),
                                result.meta(),
                                result.message()));
        }
}
