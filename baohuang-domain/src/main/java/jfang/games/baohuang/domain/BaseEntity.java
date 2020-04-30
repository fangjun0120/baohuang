package jfang.games.baohuang.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Jun
 * @date 2020/4/29
 */
public abstract class BaseEntity implements Serializable {

    @Getter
    @Setter
    private Long id;
}
