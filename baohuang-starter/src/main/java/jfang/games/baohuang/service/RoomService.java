package jfang.games.baohuang.service;

import jfang.games.baohuang.adapter.UserDetailAdapter;
import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.constant.GuideMessage;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.entity.Room;
import jfang.games.baohuang.domain.repo.MessageRepo;
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
    @Resource
    private MessageRepo messageRepo;

    /**
     * 用户进入房间
     */
    public void entryRoom(Long id) {
        Room room = roomCache.joinRoom(id);
        UserDetailAdapter userDetailAdapter = SecurityUtil.getCurrentUser();
        roomCache.saveUserRoomInfo(userDetailAdapter.getUser().getId(), id);
        Player player = new Player(userDetailAdapter.getUser());
        room.addPlayer(player);
    }

    /**
     * 用户离开房间
     *
     * @param id 房间id
     */
    public void leftRoom(Long id) {
        UserDetailAdapter userDetailAdapter = SecurityUtil.getCurrentUser();
        Room room = roomCache.getRoomById(id);
        room.removePlayer(userDetailAdapter.getUser().getId());
    }

    /**
     * 玩家加入时加载玩家信息
     */
    public void onPlayerJoin(Long userId) {
        Long roomId = roomCache.findRoomIdByUserId(userId);
        Room room = roomCache.getRoomById(roomId);
        Player player = room.getGame().getPlayerByUserId(userId);
        room.getGame().updatePlayerInfo(player.getIndex(), null);
    }

    /**
     * 玩家离开
     */
    public void onPlayerLeft(Long userId) {
        Long roomId = roomCache.findRoomIdByUserId(userId);
        Room room = roomCache.getRoomById(roomId);
        room.removePlayer(userId);
    }

    /**
     * 接收玩家消息
     */
    public void onPlayerMessage(Long id, MessageDTO message) {
        Room room = roomCache.getRoomById(id);
        if (!message.getGameId().equals(room.getGame().getId())) {
            log.warn("game not match {} from source {}", message.getGameId(), message.getSource());
            // 有可能是上一局游戏的结束，刷新下这个玩家
            if (message.getPlayerCallback() != null && message.getPlayerCallback().getUserId() != null) {
                Long userId = message.getPlayerCallback().getUserId();
                room.getGame().updatePlayerInfo(room.getGame().getPlayerByUserId(userId).getIndex(), null);
            }
            return;
        }
        // 重置游戏
        if (room.getGame().isCompleted()) {
            for (Player player: room.getPlayerList()) {
                player.reset();
            }
            room.resetGame();
            room.getGame().updatePlayerInfo();
        }
        try {
            room.getGame().getGameStage().onPlayerMessage(room.getGame(), message);
        } catch (Exception e) {
            log.error("Error on message", e);
            if (message.getPlayerCallback() != null) {
                Player player = room.getGame().getPlayerByUserId(message.getPlayerCallback().getUserId());
                if (player != null) {
                    messageRepo.sendMessage(player.getDisplayName(), String.format(GuideMessage.ERROR, e.getMessage()));
                }
            }
        }
    }
}
