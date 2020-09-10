package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.card.*;
import jfang.games.baohuang.domain.entity.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author jfang
 */
public class ShuffleStage implements GameStage {

    @Override
    public void run(Game game) {
        dealCards(game);
        game.setGameStage(new SelectStage());
        game.getGameStage().run(game);
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {

    }

    @Override
    public int getValue() {
        return GameStageEnum.SHUFFLE.getValue();
    }

    /**
     * 发牌，随机选出一个拿到标记的大王
     * TODO: 确认能叫出主公，否则重发
     * TODO: 确认1打4
     */
    public void dealCards(Game game) {
        List<Card> deck = buildDeck();
        Collections.shuffle(deck);
        int size = (5 + 7 + 8 * 4 * 4) / 4;
        int index = new Random().nextInt(5);
        for (int i = 0; i < 5; i++) {
            List<Card> playerCard = deck.subList(i * size, (i+1) * size);
            game.getPlayers().get(i).setStatus(PlayerStatus.WAITING);
            if (index == i) {
                game.setCurrentPlayer(i);
                playerCard.add(Card.RED_JOKER);
                game.getPlayers().get(i).setStatus(PlayerStatus.PLAYING);
            }
            game.getPlayers().get(i).dealCards(playerCard);
        }
        game.updatePlayerInfo();
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
