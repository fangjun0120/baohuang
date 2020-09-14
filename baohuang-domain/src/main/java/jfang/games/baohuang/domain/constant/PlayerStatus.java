package jfang.games.baohuang.domain.constant;

import lombok.Getter;

/**
 * 玩家的状态
 * 0. 等待开始
 * 1. 游戏准备
 * 2. 等待其他玩家出牌
 * 3. 玩家出牌中
 * 4. 玩家出完了牌
 * 5. 被闷了
 *
 * @author Jun
 * @date 2020/4/29
 */
public enum PlayerStatus {

    INIT(0),
    READY(1),
    WAITING(2),
    PLAYING(3),
    COMPLETED(4),
    DEAD(5);

    @Getter
    private int value;

    PlayerStatus(int value) {
        this.value = value;
    }
}
