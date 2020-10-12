package jfang.games.baohuang.common.message;

import lombok.Data;

/**
 * @author jfang
 */
@Data
public class PlayerRank {

    private String username;
    private Integer score;

    public String toDisplayString() {
        return username + " " + score;
    }
}
