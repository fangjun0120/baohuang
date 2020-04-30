package jfang.games.baohuang.domain.dto;

import jfang.games.baohuang.domain.message.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Jun
 * @date 2020/4/30
 */
@Getter
@Setter
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = -6541241324242043317L;

    private LocalDateTime timestamp;
    private MessageType type;
    private String source;
    private String systemMessage;
    private String userMessage;
    private GameMessageDTO gameMessage;

}
