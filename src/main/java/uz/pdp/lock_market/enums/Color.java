package uz.pdp.lock_market.enums;

import lombok.Getter;

@Getter
public enum Color {
    BLACK("black"),
    CHROME("chrome"),
    DEFAULT("default");

    private final String key;

    Color(String key) {
        this.key = key;
    }
}
