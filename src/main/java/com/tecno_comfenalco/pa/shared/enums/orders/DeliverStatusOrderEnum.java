package com.tecno_comfenalco.pa.shared.enums.orders;

import com.tecno_comfenalco.pa.application.delivery.exceptions.InvalidDeliveryStatusOrderTransitionException;

// esta funcionalidad sera un proceso entre deliveries y stores
public enum DeliverStatusOrderEnum {

    PENDING {
        @Override
        public DeliverStatusOrderEnum delivered() {
            return DELIVERED;
        }

        @Override
        public DeliverStatusOrderEnum rejected() {
            return REJECTED;
        }

        @Override
        public DeliverStatusOrderEnum problem() {
            return PROBLEM;
        }

        @Override
        public DeliverStatusOrderEnum notPaid() {
            return NOTPAID;
        }

        @Override
        public DeliverStatusOrderEnum postponed() {
            return POSPONED;
        }
    },

    PROBLEM {
        @Override
        public DeliverStatusOrderEnum pending() {
            return PENDING;
        }
    },

    DELIVERED,
    REJECTED,
    NOTPAID,
    POSPONED;

    public DeliverStatusOrderEnum pending() {
        throw new InvalidDeliveryStatusOrderTransitionException(this);
    }

    public DeliverStatusOrderEnum problem() {
        throw new InvalidDeliveryStatusOrderTransitionException(this);
    }

    public DeliverStatusOrderEnum delivered() {
        throw new InvalidDeliveryStatusOrderTransitionException(this);
    }

    public DeliverStatusOrderEnum rejected() {
        throw new InvalidDeliveryStatusOrderTransitionException(this);
    }

    public DeliverStatusOrderEnum notPaid() {
        throw new InvalidDeliveryStatusOrderTransitionException(this);
    }

    public DeliverStatusOrderEnum postponed() {
        throw new InvalidDeliveryStatusOrderTransitionException(this);
    }
}
