package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @AUTHOR dyd71
 * @DATE 2024-11-01
 * @PARAM
 * @VERSION 1.0
 */
@Controller
@Slf4j
public class HomeController {
    @RequestMapping("/")
    public String home(){
        log.info("home controller");
        return "home";
    }
}
