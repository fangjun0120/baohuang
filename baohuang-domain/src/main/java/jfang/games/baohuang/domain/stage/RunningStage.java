package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Player;

/**
 * @author jfang
 */
public class RunningStage implements GameStage {

    @Override
    public void run(Game game) {
        Player current = game.getPlayers().get(game.getCurrentPlayer());
        current.setStatus(PlayerStatus.PLAYING);
        game.updatePlayerInfo();
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {

    }

    private boolean checkCompleted(Game game) {
        return false;
    }

    @Override
    public int getValue() {
        return GameStageEnum.RUNNING.getValue();
    }
}
