package uz.pdp.lock_market.enums;

import lombok.Getter;

@Getter
public enum UnlockType {
    PIN_CODE("pin.code"),
    CARD_BRACELET("card.bracelet"),
    FINGERPRINT("fingerprint");

    private final String key;

    UnlockType(String key) {
        this.key = key;
    }
}
