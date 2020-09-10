package jfang.games.baohuang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Jun
 * @date 2020/4/30
 */
@Slf4j
@RequestMapping("/")
@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/home");
        return mav;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            log.info(error);
            model.addAttribute( "loginError"  , true);
        }
        return "/login";
    }

}
