package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.CardInfo;
import jfang.games.baohuang.domain.constant.Rank;
import jfang.games.baohuang.domain.constant.Suit;
import jfang.games.baohuang.domain.entity.Card;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.user.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jfang
 */
public class ShuffleStageTest {

    @Test
    public void testDealCard() {
        ShuffleStage stage = new ShuffleStage();
        Game game = new Game();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId((long) i);
            user.setUsername("test" + i);
            user.setPortrait("test.jpg");
            Player player = new Player(user);
            players.add(player);
        }
        game.setPlayers(players);
        if (stage.dealCards(game)) {
            for (Player player : players) {
                System.out.println(player.getPlayerCards().count());
                StringBuilder sb = new StringBuilder();
                sb.append(player.getPlayerCards().countByRank(Rank.SEVEN));
                sb.append(" ");
                sb.append(player.getPlayerCards().countByRank(Rank.EIGHT));
                sb.append(" ");
                sb.append(player.getPlayerCards().countByRank(Rank.NINE));
                sb.append(" ");
                sb.append(player.getPlayerCards().countByRank(Rank.TEN));
                sb.append(" ");
                sb.append(player.getPlayerCards().countByRank(Rank.JACK));
                sb.append(" ");
                sb.append(player.getPlayerCards().countByRank(Rank.QUEEN));
                sb.append(" ");
                sb.append(player.getPlayerCards().countByRank(Rank.KING));
                sb.append(" ");
                sb.append(player.getPlayerCards().countByRank(Rank.ACE));
                sb.append(" ");
                sb.append(player.getPlayerCards().countByRank(Rank.TWO));
                sb.append(" ");
                sb.append(player.getPlayerCards().countJoker(true));
                sb.append(" ");
                sb.append(player.getPlayerCards().countJoker(false));
                System.out.println(sb.toString());
                List<CardInfo> info = player.getPlayerCards().toCardInfo();
                System.out.println(info.stream().filter(c -> c.getRank().equals("O")).count());
            }
        }
    }

    @Test
    public void testCardInfo() {
        for (Rank rank: Rank.values()) {
            for (Suit suit: Suit.values()) {
                Card card = new Card(suit, rank);
                System.out.print(card.toString());
                System.out.print(" ");
                System.out.println(card.toCardInfo().toString());
            }
        }
        Card redJoker = Card.RED_JOKER;
        System.out.print(redJoker.toString());
        System.out.print(" ");
        System.out.println(redJoker.toCardInfo().toString());

        Card blackJoker = Card.BLACK_JOKER;
        System.out.print(blackJoker.toString());
        System.out.print(" ");
        System.out.println(blackJoker.toCardInfo().toString());
    }
}
