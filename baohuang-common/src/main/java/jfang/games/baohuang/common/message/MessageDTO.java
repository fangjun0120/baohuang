package jfang.games.baohuang.common.message;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jun
 * @date 2020/4/30
 */
@Data
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = -6541241324242043317L;

    private Long timestamp;

    private String source;

    /**
     * game stage
     */
    private Integer stage;

    private Integer index;

    /**
     * player info update
     */
    private List<PlayerInfo> playerInfo;

    /**
     * report from player
     */
    private PlayerAction playerAction;

    public MessageDTO() {
    }

    public MessageDTO(String source) {
        this.timestamp = System.currentTimeMillis();
        this.source = source;
    }
}