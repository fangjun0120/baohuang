package jfang.games.baohuang.domain.repo;

import jfang.games.baohuang.domain.entity.Room;

/**
 * @author jfang
 */
public interface RoomRepo {

    Room getRoomById(Long id);

    /**
     * 获取房间，不存在的时候创建
     *
     * @param id id
     * @return room
     */
    Room joinRoom(Long id);

    void deleteRoom(Long id);

    Long findRoomIdByUserId(Long userId);

    void saveUserRoomInfo(Long userId, Long roomId);
}
