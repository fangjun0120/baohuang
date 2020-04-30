package jfang.games.baohuang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Jun
 * @date 2020/4/30
 */
@Slf4j
@Controller("/")
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/home");
        return mav;
    }
}
