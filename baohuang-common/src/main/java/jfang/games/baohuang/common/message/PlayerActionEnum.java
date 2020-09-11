package jfang.games.baohuang.common.message;

import lombok.Getter;

/**
 * @author jfang
 */
public enum PlayerActionEnum {

    ONE_OVER_FOUR("是否明牌一打四", 0),
    REVOLUTION("是否起义", 1),
    ENDING("本局结束，是否继续", 2);

    PlayerActionEnum(String message, int value) {
        this.message = message;
        this.value = value;
    }

    @Getter
    private String message;
    @Getter
    private int value;
}
