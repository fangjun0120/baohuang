package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.entity.Game;

/**
 * @author jfang
 */
public class InitStage implements GameStage {

    @Override
    public void run(Game game) {
        game.setGameStage(new ShuffleStage());
        game.getGameStage().run(game);
    }

    @Override
    public GameControl onPlayerMessage(Game game, MessageDTO messageDTO) {
        return new GameControl();
    }

    @Override
    public int getValue() {
        return GameStageEnum.INIT.getValue();
    }
}
