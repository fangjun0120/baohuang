package jfang.games.baohuang.domain.event;

import jfang.games.baohuang.domain.entity.Player;

/**
 * @author jfang
 */
public class PlayerLeftEvent extends PlayerEvent {

    private Long roomId;

    public PlayerLeftEvent(Long roomId, Player player) {
        this.roomId = roomId;
        this.source = player;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Player getSource() {
        return source;
    }
    
    public String toMessage() {
        return String.format("[系统] 玩家 %s 离开房间", this.source.getDisplayName());
    }
}
