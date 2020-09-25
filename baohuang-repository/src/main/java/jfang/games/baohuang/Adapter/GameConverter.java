package jfang.games.baohuang.Adapter;

import jfang.games.baohuang.common.util.DateUtil;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.po.GamePO;

/**
 * @author jfang
 */
public class GameConverter {

    public static GamePO convertGameToPO(Game game) {
        GamePO gamePO = new GamePO();
        gamePO.setId(game.getId());
        gamePO.setCreated(DateUtil.localDateTimeToLong(game.getCreatedTime()));
        gamePO.setUpdated(DateUtil.localDateTimeToLong(game.getUpdatedTime()));
        return gamePO;
    }
}
