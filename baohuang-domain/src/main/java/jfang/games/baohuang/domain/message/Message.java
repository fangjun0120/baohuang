package jfang.games.baohuang.domain.message;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jun
 * @date 2020/4/30
 */
@Getter
@Setter
public abstract class Message {

    protected LocalDateTime timestamp;

}
