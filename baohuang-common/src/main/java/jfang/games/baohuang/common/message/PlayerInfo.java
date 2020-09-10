package jfang.games.baohuang.common.message;

import lombok.Data;

import java.util.List;

/**
 * @author jfang
 */
@Data
public class PlayerInfo {

    private Long userId;
    private String username;
    private String portrait;

    private Integer index;
    private Boolean isKing;
    private Integer state;
    private List<CardInfo> cardList;
}
