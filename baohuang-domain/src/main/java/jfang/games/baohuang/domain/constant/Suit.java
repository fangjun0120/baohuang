package jfang.games.baohuang.domain.constant;

import lombok.Getter;

/**
 * @author Jun
 * @date 2020/4/29
 */
public enum Suit {

    HEART(1, "♥", Color.RED, "h"),
    SPADE(2, "♠", Color.BLACK, "s"),
    DIAMOND(3, "♦", Color.RED, "d"),
    CLUB(4, "♣", Color.BLACK, "c");

    @Getter
    private int value;
    @Getter
    private String displayString;
    @Getter
    private Color color;
    @Getter
    private String key;

    Suit(int value, String displayString, Color color, String key) {
        this.value = value;
        this.displayString = displayString;
        this.color = color;
        this.key = key;
    }

    public static Suit of(String key) {
        for (Suit suit: Suit.values()) {
            if (key.equals(suit.getKey())) {
                return suit;
            }
        }
        throw new IllegalArgumentException("Invalid suit " + key);
    }
}
