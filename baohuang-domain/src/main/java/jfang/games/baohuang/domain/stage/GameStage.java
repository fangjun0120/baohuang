package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.entity.Game;

/**
 * @author jfang
 */
public interface GameStage {

    /**
     * 执行当前阶段
     * @param game 上下文
     */
    void run(Game game);

    /**
     * 接收消息
     * @param game 上下文
     * @param messageDTO 消息
     */
    void onPlayerMessage(Game game, MessageDTO messageDTO);

    /*×
     * 执行下一阶段
     */
    void nextStage(Game game);

    /**
     * 当前阶段序列号
     * @return 序列号
     */
    int getValue();
}
