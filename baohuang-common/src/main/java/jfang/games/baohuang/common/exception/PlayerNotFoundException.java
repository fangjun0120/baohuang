package jfang.games.baohuang.common.exception;

/**
 * @author jfang
 */
public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(int index) {
        super("player with index " + index);
    }
}
