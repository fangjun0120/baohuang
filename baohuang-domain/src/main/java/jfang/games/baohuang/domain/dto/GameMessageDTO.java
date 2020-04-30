package jfang.games.baohuang.domain.dto;

import jfang.games.baohuang.domain.card.GameStage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Jun
 * @date 2020/4/30
 */
@Getter
@Setter
public class GameMessageDTO implements Serializable {

    private static final long serialVersionUID = 5070508970610614810L;

    private GameStage stage;


}
