package jfang.games.baohuang.common.message;

import lombok.Data;

/**
 * @author jfang
 */
@Data
public class PlayerOptions {

    private String message;
    private Object data;
    private Integer value;

    public static PlayerOptions of(PlayerOptionEnum playerOptionEnum, Object data) {
        PlayerOptions action = new PlayerOptions();
        action.setMessage(playerOptionEnum.getMessage());
        action.setValue(playerOptionEnum.getValue());
        action.setData(data);
        return action;
    }
}
