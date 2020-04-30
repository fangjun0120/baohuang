package jfang.games.baohuang.domain.card;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jun
 * @date 2020/4/29
 */
@Getter
public class Player {

    private Long userId;

    private String displayName;

    private List<Card> cards = new ArrayList<>();

    private PlayerStatus status = PlayerStatus.WAITING;

    public Player(Long userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public void dealCards(List<Card> cards) {
        getCards().clear();
        getCards().addAll(cards);
    }
}
