package jfang.games.baohuang.repo;

import jfang.games.baohuang.domain.entity.Room;
import jfang.games.baohuang.domain.repo.RoomRepo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 房间数据储存的内存实现
 *
 * @author jfang
 */
@Component
public class RoomCache implements RoomRepo {

    private final Map<Long, Room> roomMap = new ConcurrentHashMap<>();
    private final Map<Long, Long> userRoomMap = new HashMap<>();

    @Override
    public Room getRoomById(Long id) {
        return roomMap.get(id);
    }

    @Override
    public Room joinRoom(Long id) {
        if (!roomMap.containsKey(id)) {
            synchronized (this) {
                if (!roomMap.containsKey(id)) {
                    roomMap.put(id, new Room(id));
                }
            }
        }
        return roomMap.get(id);
    }

    @Override
    public void deleteRoom(Long id) {
        roomMap.remove(id);
    }

    @Override
    public Long findRoomIdByUserId(Long userId) {
        return userRoomMap.get(userId);
    }

    @Override
    public void saveUserRoomInfo(Long userId, Long roomId) {
        userRoomMap.put(userId, roomId);
    }
}
