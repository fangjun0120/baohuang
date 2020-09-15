package jfang.games.baohuang.domain.entity;

import jfang.games.baohuang.common.message.CardInfo;
import jfang.games.baohuang.domain.constant.Rank;
import jfang.games.baohuang.common.exception.CardNotFoundException;

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
        return countByRank(Rank.SEVEN);
    }

    public int countByRank(Rank rank) {
        int count = 0;
        for (Card card : cardList) {
            if (card.getRank() == rank) {
                count++;
            }
        }
        return count;
    }

    public int countJoker(Boolean isRed) {
        int count = 0;
        for (Card card : cardList) {
            if (isRed == null) {
                if (card.isRedJoker() || card.isBlackJoker()) {
                    count++;
                }
            } else if (isRed) {
                if (card.isRedJoker()) {
                    count++;
                }
            } else {
                if (card.isBlackJoker()) {
                    count++;
                }
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
            throw new CardNotFoundException(card.toDisplayString());
        }
    }

    // TODO: 可优化
    public void popCards(List<Card> cardList) {
        for (Card card: cardList) {
            popCard(card);
        }
    }

    public int count() {
        return this.cardList.size();
    }

    public List<CardInfo> toCardInfo() {
        return this.cardList.stream().map(Card::toCardInfo).collect(Collectors.toList());
    }
}
