package jfang.games.baohuang.domain.card;

import lombok.Getter;

/**
 * @author Jun
 * @date 2020/4/29
 */
public enum Rank {

    TWO(15, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "T"),
    JACK(11, "J"),
    QUEEN(12, "Q"),
    KING(13, "K"),
    ACE(14, "A");

    @Getter
    private int value;
    @Getter
    private String symbol;

    Rank(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }
}
