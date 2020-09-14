package jfang.games.baohuang.domain.entity;

import com.google.common.base.Preconditions;
import jfang.games.baohuang.common.message.CardInfo;
import jfang.games.baohuang.domain.constant.Rank;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 一手牌
 *
 * @author Jun
 * @date 2020/4/29
 */
@Getter
public class Hand {

    private static final long serialVersionUID = -4719636591412710612L;

    private static final int DUMMY_DOUBLE_RED_JOKER_VALUE = 40;
    private static final int DUMMY_RED_JOKER_VALUE = 30;
    private static final int DUMMY_BLACK_JOKER_VALUE = 20;
    private static final int DUMMY_AGENT_VALUE = 50;

    public Hand(List<Card> cards) {
        Preconditions.checkArgument(cards != null && cards.size() > 0, "card list cant be null");
        this.cards = cards;
    }

    protected List<Card> cards;

    /**
     * 总张数
     */
    private int dimension;

    /**
     * 普通牌的大小
     */
    private int rank;

    /**
     * 大王张数
     */
    private int redJokerNum;

    /**
     * 小王张数
     */
    private int blackJokerNum;

    /**
     * 是否有保镖牌
     */
    private boolean hasAgent;

    public static Hand of(List<CardInfo> cardInfoList) {
        return new Hand(cardInfoList.stream().map(Card::of).collect(Collectors.toList()));
    }

    public List<CardInfo> toCardInfoList() {
        return this.cards.stream().map(Card::toCardInfo).collect(Collectors.toList());
    }

    public String toDisplayString() {
        return cards.stream().map(Card::toDisplayString).collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                ", dimension=" + dimension +
                ", rank=" + rank +
                ", redJokerNum=" + redJokerNum +
                ", blackJokerNum=" + blackJokerNum +
                ", hasAgent=" + hasAgent +
                '}';
    }

    /**
     * 解析成保皇手牌
     *
     * @param agentCard 保镖牌
     */
    public void parse(Card agentCard) {
        List<Card> cards = getCards();
        Set<Integer> set = cards.stream()
                .filter(card -> !card.equals(agentCard))
                .filter(card -> card.getRank() != null)
                .map(card -> card.getRank().getValue())
                .collect(Collectors.toSet());
        set.remove(2);
        Preconditions.checkArgument(set.size() < 2, "invalid BaoHuang Hand");
        this.dimension = cards.size();
        for (Card card: cards) {
            if (card.equals(agentCard)) {
                this.hasAgent = true;
            } else if (card.isRedJoker()) {
                this.redJokerNum++;
            } else if (card.isBlackJoker()) {
                this.blackJokerNum++;
            } else if (this.rank == 0 || card.getRank() != Rank.TWO) {
                // 如果没有别的牌，2当作2用；如果有别的，2当作别的
                this.rank = card.getRank().getValue();
            }
        }
    }

    /**
     * 是否比另一手牌大，值数组里每个序号上都大于
     *
     * @param base 另一手牌
     * @return 是否大于
     */
    public boolean isDominateThan(Hand base) {
        Preconditions.checkArgument(base != null && base.dimension > 0, "base Baohuang Hand is invalid");
        Preconditions.checkArgument(this.dimension > 0, "parse first");

        List<Integer> thisValueList = toValueList(isJoker2Over1(base));
        List<Integer> baseValueList = toValueList(base.isJoker2Over1(this));

        Preconditions.checkArgument(thisValueList.size() == baseValueList.size(), "dimension invalid");
        for (int i = 0; i < thisValueList.size(); i++) {
            if (thisValueList.get(i) <= baseValueList.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 生成一个升序的值数组，用来比较大小，把王和保镖牌转成特殊的值
     * 大王二杀一的时候，两个大王转成一个值，这个值应该比大王大，比保镖小
     *
     * @param isJoker2Over1 大王二杀一
     * @return 值数组
     */
    private List<Integer> toValueList(boolean isJoker2Over1) {
        LinkedList<Integer> valueList = new LinkedList<>();
        for (int i = 0; i < dimension; i++) {
            valueList.add(this.rank);
        }
        for (int i = 0; i < this.getBlackJokerNum(); i++) {
            valueList.removeFirst();
            valueList.addLast(DUMMY_BLACK_JOKER_VALUE);
        }
        if (isJoker2Over1) {
            valueList.removeFirst();
            valueList.removeFirst();
            valueList.addLast(DUMMY_DOUBLE_RED_JOKER_VALUE);
            for (int i = 2; i < this.getRedJokerNum(); i++) {
                valueList.removeFirst();
                valueList.addLast(DUMMY_RED_JOKER_VALUE);
            }
        } else {
            for (int i = 0; i < this.getRedJokerNum(); i++) {
                valueList.removeFirst();
                valueList.addLast(DUMMY_RED_JOKER_VALUE);
            }
        }
        if (this.hasAgent) {
            valueList.removeFirst();
            valueList.addLast(DUMMY_AGENT_VALUE);
        }
        return valueList;
    }

    /**
     * 大王二杀一
     *
     * @param base
     * @return 是否
     */
    public boolean isJoker2Over1(Hand base) {
        return this.getDimension() == base.getDimension() + 1
                && this.getRedJokerNum() == 2
                && base.getRedJokerNum() == 1;
    }
}
