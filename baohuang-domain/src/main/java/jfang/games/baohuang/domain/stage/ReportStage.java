package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.card.GameStageEnum;
import jfang.games.baohuang.domain.entity.Game;

/**
 * @author jfang
 */
public class ReportStage implements GameStage {

    @Override
    public void run(Game game) {

    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {

    }

    @Override
    public int getValue() {
        return GameStageEnum.SELECT.getValue();
    }
}
