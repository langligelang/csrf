package com.maoge.csrf.ctl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Controller
public class LoginHtmlController {

    @RequestMapping("/loginHtml")
    public String loginHtml() {
        return "loginHtml";
    }





}
