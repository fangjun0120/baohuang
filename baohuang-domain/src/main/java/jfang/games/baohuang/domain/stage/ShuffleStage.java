package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.GuideMessage;
import jfang.games.baohuang.domain.constant.Rank;
import jfang.games.baohuang.domain.constant.Suit;
import jfang.games.baohuang.domain.entity.Card;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.PlayerCards;
import jfang.games.baohuang.domain.repo.RepoUtil;

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
        RepoUtil.messageRepo.broadcastRoom(game.getRoomId(), GuideMessage.SHUFFLE_START);
        boolean goodToGo = dealCards(game);
        // 都不能立，重发
        while (!goodToGo) {
            RepoUtil.messageRepo.broadcastRoom(game.getRoomId(), GuideMessage.SHUFFLE_AGAIN);
            goodToGo = dealCards(game);
        }
        game.updatePlayerInfo();
        game.setGameStage(new BuySevenStage());
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
     * 标记选到的人
     */
    public boolean dealCards(Game game) {
        List<Card> deck = buildDeck();
        Collections.shuffle(deck);
        int size = (5 + 7 + 8 * 4 * 4) / 4;
        int index = new Random().nextInt(5);
        boolean anyoneCanBeKing = false;
        for (int i = 0; i < 5; i++) {
            List<Card> playerCard = deck.subList(i * size, (i+1) * size);
            if (index == i) {
                game.setCurrentPlayer(i);
                playerCard.add(Card.RED_JOKER);
            }
            PlayerCards playerCards = new PlayerCards(playerCard);
            game.getPlayers().get(i).setPlayerCards(playerCards);
            if (playerCards.canBeKing()) {
                anyoneCanBeKing = true;
            }
        }
        return anyoneCanBeKing;
    }

    /**
     * 少加了一张大王，决定先手时分配
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
