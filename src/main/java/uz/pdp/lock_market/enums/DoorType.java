package uz.pdp.lock_market.enums;

import lombok.Getter;

@Getter
public enum DoorType {
    WOODEN("wooden"),
    METAL("metal");

    private final String key;

    DoorType(String key) {
        this.key = key;
    }
}
