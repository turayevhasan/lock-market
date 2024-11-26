package uz.pdp.lock_market.enums;

import lombok.Getter;

@Getter
public enum PaymentType {
    WITH_CARD("with.card"),
    WITH_CASH("with.cash");

    private final String key;

    PaymentType(String key) {
        this.key = key;
    }
}
