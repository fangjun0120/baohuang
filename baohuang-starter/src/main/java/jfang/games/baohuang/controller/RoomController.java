package jfang.games.baohuang.controller;

import jfang.games.baohuang.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author jfang
 */
@Slf4j
@RequestMapping("/room")
@Controller
public class RoomController {

    @Resource
    private RoomService roomService;

    @GetMapping("/room/{id}")
    public String room(@PathVariable Long id, Model model) {
        roomService.entryRoom(id);
        model.addAttribute("roomId", id);
        return "/room";
    }
}
