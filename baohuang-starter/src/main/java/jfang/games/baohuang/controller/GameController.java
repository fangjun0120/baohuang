package jfang.games.baohuang.controller;

import jfang.games.baohuang.service.SingleSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import javax.annotation.Resource;

/**
 * @author Jun
 * @date 2020/4/30
 */
@Slf4j
@Controller("/app")
public class GameController {

    @Resource
    private SingleSessionService singleSess6ionService;

    private SimpMessagingTemplate template;

    @MessageMapping("/chat")
    @SendTo("/topic/player")
    public String chatMessage(String message) {
        log.info("{} {}", message);
        return String.format("[%s] %s", "name", message);
    }


    @MessageExceptionHandler
    public String handleException(Exception exception) {

        return "";
    }

    @EventListener
    public void onConnectionBuild(SessionConnectedEvent event) {

        log.info("connection built " + event);
    }

    @EventListener
    public void onConnectionDestroyed(SessionDisconnectEvent event) {
        log.info("connection destroyed " + event);
    }

    @EventListener
    public void onSubscribed(SessionSubscribeEvent event) {
        log.info("subscribed " + event);
    }

}
