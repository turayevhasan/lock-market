package uz.pdp.lock_market.enums;

import lombok.Getter;

@Getter
public enum LockType {
    WITH_APP("with.app"),
    WITHOUT_APP("without.app");

    private final String key;

    LockType(String key){
        this.key = key;
    }
}
