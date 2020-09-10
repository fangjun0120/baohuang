package jfang.games.baohuang.repo;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.repo.MessageRepo;
import jfang.games.baohuang.domain.user.User;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jfang
 */
@Component
public class MessageRepository implements MessageRepo {

    @Resource
    private SimpMessagingTemplate template;

    @Override
    public void broadcastRoom(Long roomId, String message) {
        template.convertAndSend("/topic/player/" + roomId, message);
    }

    /**
     * /user/message
     *
     * @param username
     * @param message
     */
    @Override
    public void sendMessage(String username, String message) {
        template.convertAndSendToUser(username, "/message", message);
    }

    @Override
    public void systemMessage(String username, MessageDTO message) {
        template.convertAndSendToUser(username, "/system", message);
    }
}
