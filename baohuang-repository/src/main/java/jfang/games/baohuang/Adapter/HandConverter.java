package jfang.games.baohuang.Adapter;

import jfang.games.baohuang.domain.entity.Card;
import jfang.games.baohuang.domain.entity.Hand;
import jfang.games.baohuang.po.HandPO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jfang
 */
public class HandConverter {

    public static HandPO convertCardsToHand(List<Card> cards, Long gameId, Long userId) {
        HandPO handPO = new HandPO();
        handPO.setGameId(gameId);
        handPO.setUserId(userId);
        handPO.setHand(cards.stream().map(Card::toDisplayString).collect(Collectors.joining("")));
        handPO.setInit(true);
        long l = System.currentTimeMillis();
        handPO.setCreated(l);
        handPO.setUpdated(l);
        return handPO;
    }

    public static HandPO convertHandToPO(Hand hand, Long gameId, Long userId) {
        HandPO handPO = new HandPO();
        handPO.setGameId(gameId);
        handPO.setUserId(userId);
        handPO.setHand(hand.toDisplayString());
        handPO.setInit(false);
        long l = System.currentTimeMillis();
        handPO.setCreated(l);
        handPO.setUpdated(l);
        return handPO;
    }
}
