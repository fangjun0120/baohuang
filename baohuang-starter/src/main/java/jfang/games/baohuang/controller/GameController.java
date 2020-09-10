package jfang.games.baohuang.controller;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.common.util.JsonUtil;
import jfang.games.baohuang.domain.repo.MessageRepo;
import jfang.games.baohuang.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.Principal;

/**
 * @author Jun
 * @date 2020/4/30
 */
@Slf4j
@Controller("/app")
public class GameController {

    @Resource
    private MessageRepo messageRepo;

    @Resource
    private RoomService roomService;

    @MessageMapping("/chat/{id}")
    public void chatMessage(@DestinationVariable Long id, String message, Principal principal) {
        log.info("{}", message);
        String msg = String.format("[%s] %s", principal.getName(), message);
        messageRepo.broadcastRoom(id, msg);
    }

    @MessageMapping("/system/{id}")
    public void systemMessage(@DestinationVariable Long id, String message) {
        MessageDTO messageDTO;
        try {
            messageDTO = JsonUtil.parse(message, MessageDTO.class);
        } catch (IOException e) {
            log.error("invalid json {}", message, e);
            return;
        }
        roomService.onPlayerMessage(id, messageDTO);
    }

    @MessageExceptionHandler
    public String handleException(Exception exception) {
        log.error("Exception", exception);
        return "error";
    }

    @EventListener
    public void onConnectionBuild(SessionConnectedEvent event) {
        Principal principal = event.getUser();
        log.info("user {} connected", principal == null ? "NA" : principal.getName());
    }

    @EventListener
    public void onConnectionDestroyed(SessionDisconnectEvent event) {
        Principal principal = event.getUser();
        log.info("User {} disconnected", principal == null ? "NA" : principal.getName());
    }

    @EventListener
    public void onSubscribed(SessionSubscribeEvent event) {
        Principal principal = event.getUser();
        MessageHeaders headers = event.getMessage().getHeaders();
        String topic = (String) headers.get(SimpMessageHeaderAccessor.DESTINATION_HEADER);
        log.info("user {} subscribed on {}", principal == null ? "NA" : principal.getName(), topic);
    }

}
