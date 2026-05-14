package com.tecno_comfenalco.pa.infrastructure.delivery.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.application.delivery.command.actions.GetDeliveryByIdCommand;
import com.tecno_comfenalco.pa.application.delivery.command.actions.ListAllDeliveryCommand;
import com.tecno_comfenalco.pa.application.delivery.command.actions.RegisterDeliveryCommand;
import com.tecno_comfenalco.pa.application.delivery.command.actions.UpdateDeliveryCommand;
import com.tecno_comfenalco.pa.application.delivery.command.response.GetDeliveryByIdCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.ListAllDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.MeDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.RegisterDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.command.response.UpdateDeliveryCommandResult;
import com.tecno_comfenalco.pa.application.delivery.dto.request.RegisterDeliveryRequestDto;
import com.tecno_comfenalco.pa.application.delivery.dto.request.UpdateDeliveryRequestDto;
import com.tecno_comfenalco.pa.application.delivery.dto.response.GetDeliveryByIdResponseDto;
import com.tecno_comfenalco.pa.application.delivery.dto.response.ListAllDeliveryResponseDto;
import com.tecno_comfenalco.pa.application.delivery.dto.response.MeDeliveryResponseDto;
import com.tecno_comfenalco.pa.application.delivery.dto.response.RegisterDeliveryResponseDto;
import com.tecno_comfenalco.pa.application.delivery.dto.response.UpdateDeliveryResponseDto;
import com.tecno_comfenalco.pa.application.delivery.usecases.DeliveryUseCase;
import com.tecno_comfenalco.pa.infrastructure.security.CustomUserDetails;
import com.tecno_comfenalco.pa.shared.utils.http.DirectionEnum;
import com.tecno_comfenalco.pa.shared.utils.http.RequestParams;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('DISTRIBUTOR')")
@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

        private DeliveryUseCase deliveryUseCase;

        public DeliveryController(DeliveryUseCase deliveryUseCase) {
                this.deliveryUseCase = deliveryUseCase;
        }

        @PostMapping
        public ResponseEntity<RegisterDeliveryResponseDto> registerDelivery(
                        @RequestBody @Valid RegisterDeliveryRequestDto dto, Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                RegisterDeliveryCommand cmd = new RegisterDeliveryCommand(details.getUserId(), dto.username(),
                                dto.password(), dto.email(), dto.name(),
                                dto.documentType(),
                                dto.documentNumber(), dto.phoneNumber(), dto.licenseNumber(), dto.licenseType(),
                                dto.vehicles());

                RegisterDeliveryCommandResult result = deliveryUseCase.registerDelivery(cmd);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new RegisterDeliveryResponseDto(result.deliveryId(), result.distributorId(),
                                                result.message()));
        }

        @GetMapping
        public ResponseEntity<ListAllDeliveryResponseDto> listAllDeliveries(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "10") Integer size,
                        @RequestParam(required = false, defaultValue = "name") String sortBy,
                        @RequestParam(required = false, defaultValue = "DESC") DirectionEnum direction,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                RequestParams params = new RequestParams(name, page, size, sortBy, direction);
                ListAllDeliveryCommand cmd = new ListAllDeliveryCommand(details.getUserId(), params);
                ListAllDeliveryCommandResult result = deliveryUseCase.listAllDeliveries(cmd);

                return ResponseEntity.ok()
                                .body(new ListAllDeliveryResponseDto(result.deliveries(), result.meta(),
                                                result.message()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<GetDeliveryByIdResponseDto> getDeliveryById(@PathVariable UUID id,
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                GetDeliveryByIdCommand cmd = new GetDeliveryByIdCommand(details.getUserId(), id);
                GetDeliveryByIdCommandResult result = deliveryUseCase.getDeliveryById(cmd);

                return ResponseEntity.ok().body(new GetDeliveryByIdResponseDto(result.delivery(), result.message()));
        }

        @PutMapping("/{id}")
        public ResponseEntity<UpdateDeliveryResponseDto> updateDeliveryById(@PathVariable UUID id,
                        Authentication authentication, @RequestBody UpdateDeliveryRequestDto dto) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

                UpdateDeliveryCommand cmd = new UpdateDeliveryCommand(details.getUserId(), id, dto.name(),
                                dto.phoneNumber(),
                                dto.licenseType(), dto.vehicles());

                UpdateDeliveryCommandResult result = deliveryUseCase.updateDelivery(cmd);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new UpdateDeliveryResponseDto(result.deliveryId(), result.message()));
        }

        @PreAuthorize("hasRole('DELIVERY')")
        @GetMapping("/me")
        public ResponseEntity<MeDeliveryResponseDto> me(
                        Authentication authentication) {
                CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
                MeDeliveryCommandResult result = deliveryUseCase.me(details.getUserId(), details.getDistributorId());

                return ResponseEntity.ok().body(new MeDeliveryResponseDto(result.delivery(), result.message()));
        }
}
