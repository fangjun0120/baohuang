package jfang.games.baohuang.domain.repo;

import jfang.games.baohuang.common.message.MessageDTO;

/**
 * @author jfang
 */
public interface MessageRepo {

    void broadcastRoom(Long roomId, String message);

    void sendMessage(String username, String message);

    void systemMessage(String username, MessageDTO message);

}
