package jfang.games.baohuang.domain.entity;

import com.google.common.base.Preconditions;
import jfang.games.baohuang.common.message.CardInfo;
import jfang.games.baohuang.domain.card.Card;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 一手牌
 *
 * @author Jun
 * @date 2020/4/29
 */
public class Hand {

    @Getter
    protected List<Card> cards;

    public Hand(List<Card> cards) {
        Preconditions.checkArgument(cards != null && cards.size() > 0, "card list cant be null");
        this.cards = cards;
    }

    public static Hand of(List<CardInfo> cardInfoList) {
        return new Hand(cardInfoList.stream().map(Card::of).collect(Collectors.toList()));
    }

    public String toDisplayString() {
        return cards.stream().map(Card::toDisplayString).collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                '}';
    }
}
