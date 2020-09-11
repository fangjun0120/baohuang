package jfang.games.baohuang.common.message;

import lombok.Data;

/**
 * @author jfang
 */
@Data
public class CardInfo {

    private String suit;
    private String rank;
    private Boolean agent;

    public CardInfo() {
    }

    public CardInfo(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }
}
