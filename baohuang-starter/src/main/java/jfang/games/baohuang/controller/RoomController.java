package jfang.games.baohuang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jfang
 */
@Slf4j
@RequestMapping("/room")
@Controller
public class RoomController {


    @GetMapping("/room/{id}")
    public String room(@PathVariable Long id, Model model) {
        model.addAttribute("roomId", id);

        return "/room";
    }
}
