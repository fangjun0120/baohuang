package jfang.games.baohuang.domain.constant;

import lombok.Getter;

/**
 * @author Jun
 * @date 2020/4/29
 */
public enum Rank {

    TWO(15, "2", "2"),
    THREE(3, "3", "3"),
    FOUR(4, "4", "4"),
    FIVE(5, "5", "5"),
    SIX(6, "6", "6"),
    SEVEN(7, "7", "7"),
    EIGHT(8, "8", "8"),
    NINE(9, "9", "9"),
    TEN(10, "T", "10"),
    JACK(11, "J", "J"),
    QUEEN(12, "Q", "Q"),
    KING(13, "K", "K"),
    ACE(14, "A", "A");

    @Getter
    private int value;
    @Getter
    private String symbol;
    @Getter
    private String key;

    Rank(int value, String symbol, String key) {
        this.value = value;
        this.symbol = symbol;
        this.key = key;
    }

    public static Rank of(String key) {
        for (Rank rank: Rank.values()) {
            if (key.equals(rank.getKey())) {
                return rank;
            }
        }
        throw new IllegalArgumentException("invalid rank " + key);
    }
}
