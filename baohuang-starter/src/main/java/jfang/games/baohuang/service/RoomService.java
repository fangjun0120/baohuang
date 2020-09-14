package jfang.games.baohuang.service;

import jfang.games.baohuang.adapter.UserDetailAdapter;
import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.entity.Room;
import jfang.games.baohuang.domain.repo.RoomRepo;
import jfang.games.baohuang.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jfang
 */
@Slf4j
@Component
public class RoomService {

    @Resource
    private RoomRepo roomCache;

    /**
     * 用户进入房间
     */
    public void entryRoom(Long id) {
        Room room = roomCache.joinRoom(id);
        UserDetailAdapter userDetailAdapter = SecurityUtil.getCurrentUser();
        Player player = new Player(userDetailAdapter.getUser());
        room.addPlayer(player);
    }

    public void onPlayerLeft(Long userId) {
        Long roomId = roomCache.findRoomIdByUserId(userId);
        Room room = roomCache.getRoomById(roomId);
        room.removePlayer(userId);
    }

    public void onPlayerMessage(Long id, MessageDTO message) {
        Room room = roomCache.getRoomById(id);
        if (!message.getGameId().equals(room.getGame().getId())) {
            log.warn("game not match {} from source {}", message.getGameId(), message.getSource());
            return;
        }
        if (room.getGame().isCompleted()) {
            for (Player player: room.getPlayerList()) {
                player.setStatus(PlayerStatus.INIT);
                room.resetGame();
            }
            room.getGame().updatePlayerInfo();
        }
    }
}
