package jfang.games.baohuang.domain.event;

import jfang.games.baohuang.domain.entity.Player;

/**
 * @author Jun
 * @date 2020/4/30
 */
public abstract class PlayerEvent {

    /**
     * 触发的玩家
     */
    protected Player source;

    /**
     * 目标玩家，广播的时候为null
     */
    protected Player target;

}
