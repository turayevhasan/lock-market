package uz.pdp.lock_market.util;

import java.util.Objects;

public class CoreUtils {
    public static  <E> E getIfExists(E newObj, E oldObj) {
        return Objects.nonNull(newObj) ? newObj : oldObj;
    }
}
