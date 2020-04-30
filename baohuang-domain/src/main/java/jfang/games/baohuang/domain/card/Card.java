package jfang.games.baohuang.domain.card;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author Jun
 * @date 2020/4/29
 */
@Getter
@Setter
public class Card {

    public static final Card RED_JOKER = new Card(true, false);
    public static final Card BLACK_JOKER = new Card(false, true);

    public static final Comparator<Card> SUIT_SENSITIVE_COMPARATOR = new SuitSensitiveComparator();

    /**
     * 大王
     */
    private boolean redJoker;

    /**
     * 小王
     */
    private boolean blackJoker;

    /**
     * 花色
     */
    private Suit suit;

    /**
     * 大小
     */
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    private Card(boolean isRedJoker, boolean isBlackJoker) {
        this.redJoker = isRedJoker;
        this.blackJoker = isBlackJoker;
    }

    public String toDisplayString() {
        if (isRedJoker()) {
            return "RJ";
        } else if (isBlackJoker()) {
            return "BJ";
        } else {
            return suit.getDisplayString() + getRank().getSymbol();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return redJoker == card.redJoker &&
                blackJoker == card.blackJoker &&
                suit == card.suit &&
                rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(redJoker, blackJoker, suit, rank);
    }

    @Override
    public String toString() {
        return "Card{" +
                "redJoker=" + redJoker +
                ", blackJoker=" + blackJoker +
                ", suit=" + suit +
                ", rank=" + rank +
                '}';
    }

    /**
     * 默认红桃 > 黑桃 > 方块 > 梅花
     */
    private static class SuitSensitiveComparator implements Comparator<Card> {

        @Override
        public int compare(Card o1, Card o2) {
            if (o1.isRedJoker()) {
                if (o2.isRedJoker()) {
                    return 0;
                } else {
                    return 1;
                }
            } else if (o1.isBlackJoker()) {
                if (o2.isRedJoker()) {
                    return -1;
                } else if (o2.isBlackJoker()) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (o2.isRedJoker() || o2.isBlackJoker()) {
                    return -1;
                }
                if (o1.getRank().getValue() == o2.getRank().getValue()) {
                    return o2.getSuit().getValue() - o1.getSuit().getValue();
                } else {
                    return o1.getRank().getValue() - o2.getRank().getValue();
                }
            }
        }
    }

    public static List<Card> buildFullSetCards(Rank rank) {
        List<Card> list = new ArrayList<>(4);
        list.add(new Card(Suit.HEART, rank));
        list.add(new Card(Suit.SPADE, rank));
        list.add(new Card(Suit.DIAMOND, rank));
        list.add(new Card(Suit.CLUB, rank));
        return list;
    }
}
