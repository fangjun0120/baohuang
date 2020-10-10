package jfang.games.baohuang.domain;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.repo.MessageRepo;

/**
 * @author jfang
 */
public class MockMessageRepo implements MessageRepo {

    @Override
    public void broadcastRoom(Long roomId, String message) {

    }

    @Override
    public void sendMessage(String username, String message) {

    }

    @Override
    public void systemMessage(String username, MessageDTO message) {

    }
}
