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
}
