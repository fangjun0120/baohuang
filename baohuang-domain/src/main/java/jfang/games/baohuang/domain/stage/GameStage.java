package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.entity.Game;

/**
 * @author jfang
 */
public interface GameStage {

    void run(Game game);

    void onPlayerMessage(Game game, MessageDTO messageDTO);

    int getValue();
}
