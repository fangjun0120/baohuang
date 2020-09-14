package jfang.games.baohuang.Adapter;

import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.po.GamePO;

/**
 * @author jfang
 */
public class GameConverter {

    public static GamePO convertGameToPO(Game game) {
        long timestamp = System.currentTimeMillis();
        GamePO gamePO = new GamePO();
        gamePO.setId(game.getId());
        if (gamePO.getCreated() == null) {
            gamePO.setCreated(timestamp);
        }
        gamePO.setUpdated(timestamp);
        return gamePO;
    }
}
