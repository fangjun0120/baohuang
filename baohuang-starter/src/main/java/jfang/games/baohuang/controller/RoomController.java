package jfang.games.baohuang.controller;

import jfang.games.baohuang.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public String room(@PathVariable Long id, Model model) {
        roomService.entryRoom(id);
        model.addAttribute("roomId", id);
        return "/room";
    }

    @ResponseBody
    @PostMapping("/{id}")
    public String leftRoom(@PathVariable Long id) {
        roomService.leftRoom(id);
        return "ok";
    }
}
