package jfang.games.baohuang.domain.message;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jun
 * @date 2020/4/29
 */
@Getter
@Setter
public class ChatMessage extends Message {

    private Long userId;

    private String userName;

    private String message;

}
