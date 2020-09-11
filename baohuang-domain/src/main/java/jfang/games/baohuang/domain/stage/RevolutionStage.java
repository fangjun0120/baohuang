package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.card.GameStageEnum;
import jfang.games.baohuang.domain.entity.Game;

/**
 * 是否明牌一打四，是否起义
 *
 * @author jfang
 */
public class RevolutionStage implements GameStage {

    @Override
    public void run(Game game) {


    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {

    }

    @Override
    public int getValue() {
        return GameStageEnum.REVOLUTION.getValue();
    }
}
