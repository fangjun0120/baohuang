package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.CardInfo;
import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.common.message.PlayerCallback;
import jfang.games.baohuang.domain.MockMessageRepo;
import jfang.games.baohuang.domain.constant.Rank;
import jfang.games.baohuang.domain.constant.Suit;
import jfang.games.baohuang.domain.entity.Card;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.entity.PlayerCards;
import jfang.games.baohuang.domain.repo.RepoUtil;
import jfang.games.baohuang.domain.user.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jfang
 */
public class BuySevenStageTest {

    @BeforeClass
    public static void init() {
        RepoUtil.messageRepo = new MockMessageRepo();
    }

    @Test
    public void testBySeven() {
        BuySevenStage stage = new BuySevenStage();
        Game game = new Game();
        List<Player> players = new ArrayList<>();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, Rank.SEVEN));
        cards.add(new Card(Suit.HEART, Rank.SEVEN));
        Player p1 = buildPlayer(0, cards);
        players.add(p1);

        cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, Rank.SEVEN));
        cards.add(new Card(Suit.HEART, Rank.TWO));
        Player p2 = buildPlayer(1, cards);
        players.add(p2);

        cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, Rank.SEVEN));
        cards.add(new Card(Suit.HEART, Rank.TWO));
        Player p3 = buildPlayer(2, cards);
        players.add(p3);

        cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, Rank.SEVEN));
        cards.add(new Card(Suit.HEART, Rank.TWO));
        Player p4 = buildPlayer(3, cards);
        players.add(p4);

        cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, Rank.TWO));
        cards.add(new Card(Suit.HEART, Rank.TWO));
        Player p5 = buildPlayer(4, cards);
        players.add(p5);

        game.setPlayers(players);
        game.setCurrentPlayer(0);
        stage.run(game);

        MessageDTO messageDTO = new MessageDTO();
        PlayerCallback playerCallback = new PlayerCallback();
        playerCallback.setUserId(0L);
        playerCallback.setSelectedCards(Collections.singletonList(new CardInfo("h", "7")));
        messageDTO.setPlayerCallback(playerCallback);
        stage.onPlayerMessage(game, messageDTO);

        messageDTO = new MessageDTO();
        playerCallback = new PlayerCallback();
        playerCallback.setUserId(4L);
        playerCallback.setSelectedCards(Collections.singletonList(new CardInfo("h", "2")));
        messageDTO.setPlayerCallback(playerCallback);
        stage.onPlayerMessage(game, messageDTO);

        for (Player player : players) {
            System.out.println(player.getPlayerCards().count());
            System.out.println(player.getPlayerCards().toCardInfo());
        }
    }

    private Player buildPlayer(long id, List<Card> cards) {
        User user = new User();
        user.setId(id);
        user.setUsername("test" + id);
        user.setPortrait("test.jpg");
        Player player = new Player(user);
        player.setPlayerCards(new PlayerCards(cards));
        player.setIndex((int) id);
        return player;
    }
}
