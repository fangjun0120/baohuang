package jfang.games.baohuang.domain.repo;

import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Hand;
import jfang.games.baohuang.domain.entity.Player;

/**
 * @author jfang
 */
public interface GameRepo {

    void createGame(Game game);

    void saveGame(Game game);

    void saveInitHand(Game game);

    void saveHand(Hand hand, Long userId, Long gameId);
}
