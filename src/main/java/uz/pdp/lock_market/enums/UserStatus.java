package uz.pdp.lock_market.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("active"),
    INACTIVE("in.active"),
    DELETED("deleted");

    private final String key;

    UserStatus(String key) {
        this.key = key;
    }
}
