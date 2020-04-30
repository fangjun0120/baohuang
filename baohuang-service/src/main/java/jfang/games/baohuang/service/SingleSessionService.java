package jfang.games.baohuang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单个游戏房间的实现
 *
 * @author Jun
 * @date 2020/4/30
 */
@Slf4j
@Component
public class SingleSessionService {

    private Map<String, String> sessionMap = new ConcurrentHashMap<>();

    public void putUser(String sessionId, String name) {
        sessionMap.put(sessionId, name);
    }

    public String getUser(String sessionId) {
        return sessionMap.get(sessionId);
    }
}
