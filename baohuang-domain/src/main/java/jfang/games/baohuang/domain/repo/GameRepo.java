package jfang.games.baohuang.domain.repo;

import jfang.games.baohuang.domain.entity.Game;

/**
 * @author jfang
 */
public interface GameRepo {

    void createGame(Game game);

    void saveGame(Game game);
}
