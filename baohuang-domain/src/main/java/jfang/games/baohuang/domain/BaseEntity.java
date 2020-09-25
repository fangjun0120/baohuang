package jfang.games.baohuang.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Jun
 * @date 2020/4/29
 */
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    protected Long id;

    protected LocalDateTime createdTime;

    protected LocalDateTime updatedTime;
}
