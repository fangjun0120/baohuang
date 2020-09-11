package jfang.games.baohuang.common.message;

import lombok.Data;

/**
 * @author jfang
 */
@Data
public class PlayerAction {

    private String message;
    private Object data;
    private Integer value;

    public static PlayerAction of(PlayerActionEnum playerActionEnum, Object data) {
        PlayerAction action = new PlayerAction();
        action.setMessage(playerActionEnum.getMessage());
        action.setValue(playerActionEnum.getValue());
        action.setData(data);
        return action;
    }
}
