package jfang.games.baohuang.domain.message;

import lombok.Getter;

/**
 * @author Jun
 * @date 2020/4/30
 */
public enum MessageType {

    SYSTEM(1),
    USER(2),
    PLAYER(3);

    @Getter
    private int value;

    MessageType(int value) {
        this.value = value;
    }
}
