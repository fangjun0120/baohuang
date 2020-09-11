package jfang.games.baohuang.domain.entity;

import jfang.games.baohuang.common.message.CardInfo;
import jfang.games.baohuang.domain.card.Card;
import jfang.games.baohuang.domain.card.Rank;
import jfang.games.baohuang.domain.exception.CardNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 玩家手牌
 *
 * @author jfang
 */
public class PlayerCards {

    private final List<Card> cardList;

    public PlayerCards(List<Card> cardList) {
        this.cardList = cardList;
        this.cardList.sort(Card.SUIT_SENSITIVE_COMPARATOR);
    }

    /**
     * 是否可以当主公
     */
    public boolean canBeKing() {
        for (int i = 0; i < this.cardList.size() - 2; i++) {
            Card card = this.cardList.get(i);
            if (!card.isJoker() && card.equals(this.cardList.get(i+1)) && card.equals(this.cardList.get(i+2))) {
                return true;
            }
        }
        return false;
    }

    public int countSameCard(Card card) {
        return (int) this.cardList.stream().filter(c -> c.equals(card)).count();
    }

    public int countSeven() {
        int count = 0;
        for (Card card : cardList) {
            if (card.getRank() == Rank.SEVEN) {
                count++;
            }
        }
        return count;
    }

    public void addCards(List<Card> cardList) {
        this.cardList.addAll(cardList);
        this.cardList.sort(Card.SUIT_SENSITIVE_COMPARATOR);
    }

    public Card getCard(Card card) {
        int index = this.cardList.indexOf(card);
        if (index > 0) {
            return this.cardList.get(index);
        }
        return null;
    }

    public void popCard(Card card) {
        int index = this.cardList.indexOf(card);
        if (index > 0) {
            this.cardList.remove(index);
        } else {
            throw new CardNotFoundException(card);
        }
    }

    // TODO: 可优化
    public void popCards(List<Card> cardList) {
        for (Card card: cardList) {
            popCard(card);
        }
    }

    public List<CardInfo> toCardInfo() {
        return this.cardList.stream().map(Card::toCardInfo).collect(Collectors.toList());
    }
}
