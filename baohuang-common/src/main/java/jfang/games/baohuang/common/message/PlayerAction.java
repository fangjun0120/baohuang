package jfang.games.baohuang.common.message;

import lombok.Data;

import java.util.List;

/**
 * @author jfang
 */
@Data
public class PlayerAction {

    private Long userId;
    private CardInfo agentCard;
    private Boolean pass;
    private List<CardInfo> selectedCards;
}
