package com.tecno_comfenalco.pa.shared.enums.orders;

import com.tecno_comfenalco.pa.application.orders.exceptions.InvalidOrderRequestTransitionException;

public enum RequestStatusOrderEnum {
    PENDING {
        @Override
        public RequestStatusOrderEnum accept() {
            return ACCEPT;
        }

        @Override
        public RequestStatusOrderEnum rejected() {
            return REJECTED;
        }

        @Override
        public RequestStatusOrderEnum problem() {
            return PROBLEM;
        }
    },

    PROBLEM {
        @Override
        public RequestStatusOrderEnum accept() {
            return ACCEPT;
        }

        @Override
        public RequestStatusOrderEnum rejected() {
            return REJECTED;
        }
    },

    REJECTED,
    ACCEPT;

    public RequestStatusOrderEnum accept() {
        throw new InvalidOrderRequestTransitionException(this);
    }

    public RequestStatusOrderEnum rejected() {
        throw new InvalidOrderRequestTransitionException(this);
    }

    public RequestStatusOrderEnum problem() {
        throw new InvalidOrderRequestTransitionException(this);
    }
}
