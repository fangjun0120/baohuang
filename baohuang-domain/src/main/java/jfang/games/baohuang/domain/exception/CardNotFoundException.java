package jfang.games.baohuang.domain.exception;

import jfang.games.baohuang.domain.entity.Card;

/**
 * @author jfang
 */
public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(Card card) {
        super(card.toDisplayString());
    }
}
