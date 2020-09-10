package jfang.games.baohuang.service;

import jfang.games.baohuang.adapter.UserDetailAdapter;
import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.entity.Room;
import jfang.games.baohuang.domain.repo.RoomRepo;
import jfang.games.baohuang.util.SecurityUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jfang
 */
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

    public void onPlayerMessage(Long id, MessageDTO message) {
        Room room = roomCache.getRoomById(id);
        // TODO: check stage
        room.getGame().getGameStage().onPlayerMessage(room.getGame(), message);
    }
}
