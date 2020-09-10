package jfang.games.baohuang.common.message;

import lombok.Data;

/**
 * @author jfang
 */
@Data
public class CardInfo {

    private String suit;
    private String rank;
    private boolean isAgent;

    public CardInfo() {
    }

    public CardInfo(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }
}
