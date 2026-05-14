package com.tecno_comfenalco.pa.shared.enums;

import com.tecno_comfenalco.pa.application.store.exceptions.InvalidTransitionStateBindingException;

public enum BindingStatusEnum {
    PENDING {
        @Override
        public BindingStatusEnum approve() {
            return APPROVED;
        }

        @Override
        public BindingStatusEnum rejected() {
            return REJECTED;
        }
    },

    APPROVED {
        @Override
        public BindingStatusEnum active() {
            return ACTIVE;
        }
    }, // la distribuidora acepto el acuerdo y a la espera que la tienda acepte

    ACTIVE, // la tienda acepto el acuerdo
    REJECTED; // la tienda rechazo el acuerdo o la distribuidora

    public BindingStatusEnum approve() {
        throw invalid("approve");
    }

    public BindingStatusEnum rejected() {
        throw invalid("rejected");
    }

    public BindingStatusEnum active() {
        throw invalid("active");
    }

    private RuntimeException invalid(String action) {
        return new InvalidTransitionStateBindingException(this, action);
    }
}
