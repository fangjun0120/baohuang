package jfang.games.baohuang.domain.card;

import com.google.common.base.Preconditions;
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

    public String toDisplayString() {
        return cards.stream().sorted(Card.SUIT_SENSITIVE_COMPARATOR).map(Card::toDisplayString).collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                '}';
    }
}
