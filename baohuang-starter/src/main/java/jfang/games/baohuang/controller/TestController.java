package jfang.games.baohuang.controller;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.repo.MessageRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jfang
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Resource
    private MessageRepo messageRepository;

    @GetMapping("/system")
    public String testSystemMessage(@RequestParam String user, @RequestParam String message) {
        messageRepository.systemMessage(user, new MessageDTO("test"));
        return "OK";
    }
}
