package jfang.games.baohuang.domain.entity;

import jfang.games.baohuang.domain.constant.Rank;
import jfang.games.baohuang.domain.constant.Suit;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jfang
 */
public class HandTest {

    /**
     * QQQQQ ! JJJJ
     * JJJJ ! JJJJ
     */
    @Test
    public void testInvalidHand() {
        List<Card> list = Card.buildFullSetCards(Rank.QUEEN);
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        Hand hand = new Hand(list);
        try {
            hand.isDominateThan(buildBaseHand());
            Assert.fail("should not arrive here");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("throw exception");
        }

        try {
            buildBaseHand().isDominateThan(buildBaseHand());
            Assert.fail("should not arrive here");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("throw exception");
        }
    }

    /**
     * QQQQ > JJJJ
     */
    @Test
    public void testNormalHand() {
        List<Card> list = Card.buildFullSetCards(Rank.QUEEN);
        Hand hand = new Hand(list);
        Assert.assertTrue(hand.isDominateThan(buildBaseHand()));
    }

    /**
     * 2QQQ > JJJJ
     * 2 > J
     */
    @Test
    public void testHandWithTwo() {
        List<Card> list = new ArrayList<>();
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(new Card(Suit.CLUB, Rank.TWO));
        Hand hand = new Hand(list);
        Assert.assertTrue(hand.isDominateThan(buildBaseHand()));

        list = new ArrayList<>();
        list.add(new Card(Suit.CLUB, Rank.TWO));
        hand = new Hand(list);
        list = new ArrayList<>();
        list.add(new Card(Suit.CLUB, Rank.JACK));
        Hand hand2 = new Hand(list);
        Assert.assertTrue(hand.isDominateThan(hand2));
    }

    /**
     * RQQQ > JJJJ
     */
    @Test
    public void testHandWithJoker() {
        List<Card> list = new ArrayList<>();
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(Card.RED_JOKER);
        Hand hand = new Hand(list);
        Assert.assertTrue(hand.isDominateThan(buildBaseHand()));
    }

    /**
     * R2QQ > JJJJ
     */
    @Test
    public void testHandWithTwoAndJoker() {
        List<Card> list = new ArrayList<>();
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(new Card(Suit.CLUB, Rank.TWO));
        list.add(Card.RED_JOKER);
        Hand hand = new Hand(list);
        Assert.assertTrue(hand.isDominateThan(buildBaseHand()));
    }

    /**
     * RR2Q > RJJ
     */
    @Test
    public void testHandWithDoubleJoker() {
        List<Card> list = new ArrayList<>();
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(new Card(Suit.CLUB, Rank.TWO));
        list.add(Card.RED_JOKER);
        list.add(Card.RED_JOKER);
        Hand hand1 = new Hand(list);

        list = new ArrayList<>();
        list.add(new Card(Suit.CLUB, Rank.JACK));
        list.add(new Card(Suit.CLUB, Rank.JACK));
        list.add(Card.RED_JOKER);
        Hand hand2 = new Hand(list);
        Assert.assertTrue(hand1.isDominateThan(hand2));
    }

    /**
     * 8QQQ > JJJJ
     * 8QQQ > RJJJ
     */
    @Test
    public void testHandWithAgent() {
        List<Card> list = new ArrayList<>();
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        list.add(new Card(Suit.CLUB, Rank.QUEEN));
        Card agent = new Card(Suit.CLUB, Rank.EIGHT);
        agent.setAgentCard(true);
        list.add(agent);
        Hand hand = new Hand(list);
        Assert.assertTrue(hand.isDominateThan(buildBaseHand()));

        List<Card> list2 = new ArrayList<>();
        list2.add(new Card(Suit.CLUB, Rank.JACK));
        list2.add(new Card(Suit.CLUB, Rank.JACK));
        list2.add(new Card(Suit.CLUB, Rank.JACK));
        list2.add(Card.RED_JOKER);
        Hand hand2 = new Hand(list2);
        Assert.assertTrue(hand.isDominateThan(hand2));
    }

    /**
     * @return 4 × Jack
     */
    private Hand buildBaseHand() {
        List<Card> list = Card.buildFullSetCards(Rank.JACK);
        return new Hand(list);
    }
}
