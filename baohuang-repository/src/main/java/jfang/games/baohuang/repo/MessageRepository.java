package jfang.games.baohuang.repo;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.repo.MessageRepo;
import jfang.games.baohuang.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jfang
 */
@Slf4j
@Component
public class MessageRepository implements MessageRepo {

    @Resource
    private SimpMessagingTemplate template;

    @Override
    public void broadcastRoom(Long roomId, String message) {
        log.info("broadcast {} {}", roomId, message);
        template.convertAndSend("/topic/player/" + roomId, message);
    }

    @Override
    public void sendMessage(String username, String message) {
        log.info("user {} {}", username, message);
        template.convertAndSendToUser(username, "/queue/message", message);
    }

    /**
     * /user/system
     *
     * @param username username
     * @param message message
     */
    @Override
    public void systemMessage(String username, MessageDTO message) {
        log.info("system {} {}", username, message);
        template.convertAndSendToUser(username, "/queue/system", message);
    }
}
