package jfang.games.baohuang.domain.card;

import lombok.Getter;

/**
 * @author Jun
 * @date 2020/4/29
 */
public enum Suit {

    HEART(1, "♥", Color.RED),
    SPADE(2, "♠", Color.BLACK),
    DIAMOND(3, "♦", Color.RED),
    CLUB(4, "♣", Color.BLACK);

    @Getter
    private int value;
    @Getter
    private String displayString;
    @Getter
    private Color color;

    Suit(int value, String displayString, Color color) {
        this.value = value;
        this.displayString = displayString;
        this.color = color;
    }
}
