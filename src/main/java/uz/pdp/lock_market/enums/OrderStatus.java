package uz.pdp.lock_market.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    IN_PROGRESS("in.progress"),
    CONFIRMED("confirmed"),
    DELIVERED("delivered"),
    PAID("paid"),
    ARCHIVED("archived"),
    CANCELLED("cancelled");

    private final String key;

    OrderStatus(String key) {
        this.key = key;
    }
}
