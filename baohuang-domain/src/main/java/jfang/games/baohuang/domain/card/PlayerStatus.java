package jfang.games.baohuang.domain.card;

import lombok.Getter;

/**
 * 玩家的状态
 * 1. 等待其他玩家出牌
 * 2. 玩家出牌中
 * 3. 玩家出完了牌
 * 4. 被闷了
 *
 * @author Jun
 * @date 2020/4/29
 */
public enum PlayerStatus {

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
