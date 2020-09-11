package jfang.games.baohuang.domain.constant;

import lombok.Getter;

/**
 * 玩家的状态
 * 0. 等待开始
 * 1. 等待其他玩家出牌
 * 2. 玩家出牌中
 * 3. 玩家出完了牌
 * 4. 被闷了
 *
 * @author Jun
 * @date 2020/4/29
 */
public enum PlayerStatus {

    INIT(0),
    WAITING(1),
    PLAYING(2),
    COMPLETED(3),
    DEAD(4);

    @Getter
    private int value;

    PlayerStatus(int value) {
        this.value = value;
    }
}