package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login.action")
    public ModelAndView login() {
        System.out.println("调用登录页面");
        return new ModelAndView("/login");
    }
}
