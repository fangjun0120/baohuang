package jfang.games.baohuang.domain.stage;

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
                System.out.println(player.getPlayerCards().toCardInfo());
            }
        }
    }
}
