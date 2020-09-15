package jfang.games.baohuang.common.exception;

/**
 * @author jfang
 */
public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(String card) {
        super(card + " not found");
    }
}
