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

    private CardInfo agentCard;

    private Boolean isOneOverFour;

    private Boolean isRevolution;

    private Integer currentPlayer;

    /**
     * player info update
     */
    private List<PlayerInfo> playerInfo;

    /**
     * report from player
     */
    private PlayerCallback playerCallback;

    /**
     * options for player
     */
    private PlayerAction playerAction;

    private PlayerActionResponse playerActionResponse;

    public MessageDTO() {
    }

    public MessageDTO(String source) {
        this.timestamp = System.currentTimeMillis();
        this.source = source;
    }
}
