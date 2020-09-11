package jfang.games.baohuang.domain.event;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.entity.Player;

import java.util.ArrayList;

/**
 * @author jfang
 */
public class PlayerJoinEvent extends PlayerEvent {

    private Long roomId;

    public PlayerJoinEvent(Long roomId, Player player) {
        this.roomId = roomId;
        this.source = player;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Player getSource() {
        return source;
    }

    public String toChatMessage() {
        return String.format("[系统] 玩家 %s 加入房间", this.source.getDisplayName());
    }

    public MessageDTO toJoinMessage() {
        MessageDTO messageDTO = new MessageDTO("server");
        messageDTO.setStage(GameStageEnum.INIT.getValue());
        messageDTO.setPlayerInfo(new ArrayList<>());
        messageDTO.getPlayerInfo().add(source.toPlayerInfo(false));
        return messageDTO;
    }
}
