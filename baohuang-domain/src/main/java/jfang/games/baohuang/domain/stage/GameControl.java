package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.domain.entity.Player;
import lombok.Data;

/**
 * @author jfang
 */
@Data
public class GameControl {

    private Boolean restart;
    private Player playerToRemove;
}
