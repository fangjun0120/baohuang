package jfang.games.baohuang.domain.card;

import jfang.games.baohuang.domain.BaseEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Jun
 * @date 2020/4/29
 */
@Getter
public class Game extends BaseEntity {

    private static final long serialVersionUID = 3142574777412945362L;

    /**
     * 所有玩家
     */
    private List<Player> players = new ArrayList<>(5);

    /**
     * 游戏阶段
     */
    private GameStage stage = GameStage.INIT;

    /**
     * 主公
     */
    private Player king;

    /**
     * 保镖
     */
    private Player agent;

    /**
     * 当前轮次玩家
     */
    private Player currentPlayer;

    /**
     * 保镖牌
     */
    private Card agentCard;

    /**
     * 是否起义
     */
    private boolean hasRevolution;

    /**
     * 是否主公1打4
     */
    private boolean isKingSingleGame;

    /**
     * 是否主公明1打4
     */
    private boolean isKingSingleGamePublic;

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.removeIf(element -> element.getUserId().equals(player.getUserId()));
    }

    /**
     * 发牌，随机选出一个拿到标记的大王
     */
    public void dealCards() {
        List<Card> deck = buildDeck();
        Collections.shuffle(deck);
        int size = (5 + 7 + 8 * 4 * 4) / 4;
        int index = new Random().nextInt(5);
        for (int i = 0; i < 5; i++) {
            List<Card> playerCard = deck.subList(i * size, (i+1) * size);
            if (index == i) {
                this.currentPlayer = getPlayers().get(i);
                playerCard.add(Card.RED_JOKER);
            }
            getPlayers().get(i).dealCards(playerCard);
        }
    }

    /**
     * 少加了一张大王，决定先手时分配
     * @return
     */
    private List<Card> buildDeck() {
        List<Card> deck = new ArrayList<>();
        addCard(deck, new Card(Suit.HEART, Rank.SEVEN), 4);
        addCard(deck, new Card(Suit.SPADE, Rank.SEVEN), 1);
        addCard(deck, Card.RED_JOKER, 3);
        addCard(deck, Card.BLACK_JOKER, 4);
        addCardSuit(deck, Rank.EIGHT, 4);
        addCardSuit(deck, Rank.NINE, 4);
        addCardSuit(deck, Rank.TEN, 4);
        addCardSuit(deck, Rank.JACK, 4);
        addCardSuit(deck, Rank.QUEEN, 4);
        addCardSuit(deck, Rank.KING, 4);
        addCardSuit(deck, Rank.TWO, 4);
        addCardSuit(deck, Rank.ACE, 4);
        return deck;
    }

    private void addCardSuit(List<Card> deck, Rank rank, int num) {
        List<Card> cards = Card.buildFullSetCards(rank);
        cards.forEach(card -> addCard(deck, card, num));
    }

    private void addCard(List<Card> deck, Card card, int num) {
        for (int i = 0; i < num; i++) {
            deck.add(card);
        }
    }
}
