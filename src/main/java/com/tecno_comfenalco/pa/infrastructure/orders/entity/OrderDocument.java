package com.tecno_comfenalco.pa.infrastructure.orders.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.tecno_comfenalco.pa.shared.enums.orders.DeliverStatusOrderEnum;
import com.tecno_comfenalco.pa.shared.enums.orders.OrderFormPaid;
import com.tecno_comfenalco.pa.shared.enums.orders.RequestStatusOrderEnum;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDocument {
    @Id
    private UUID id;
    private UUID numberOrder;
    private UUID distributorId;
    private UUID storeId;
    private UUID presalesId;
    private List<OrderProductDocument> orderProducts;
    private OrderFormPaid paidForm;
    private DeliverStatusOrderEnum deliveryStatus;
    private RequestStatusOrderEnum statusOrder;
    private Double discount;
    private Double totalGeneralIva;
    private Double total;
    private Instant createAt;
    private Instant expiration;
    private Instant updateAt;
}
