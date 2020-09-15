package jfang.games.baohuang.common.util;

import jfang.games.baohuang.common.exception.HandInvalidException;

/**
 * @author jfang
 */
public class CheckUtil {

    public static void checkHand(boolean condition, String message) {
        if (!condition) {
            throw new HandInvalidException(message);
        }
    }
}
