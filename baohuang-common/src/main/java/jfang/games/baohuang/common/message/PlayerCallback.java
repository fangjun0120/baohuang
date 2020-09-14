package jfang.games.baohuang.common.message;

import lombok.Data;

import java.util.List;

/**
 * @author jfang
 */
@Data
public class PlayerCallback {

    private Long userId;
    private Boolean ready;
    private Boolean pass;
    private List<CardInfo> selectedCards;
}
