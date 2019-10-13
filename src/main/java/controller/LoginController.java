package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebResult;

@Controller
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login.action")
    public String login() {

        return "login";
    }

    @GetMapping("/register.action/{username}")
    public String register(@ModelAttribute("username") String username) {
        System.out.println("注册拼接"+username);
        return "register";
    }

    @ModelAttribute
    public String userModel1(String password,
                          Model model) {
        System.out.println("密码："+password);
        return password;
    }
//
//    @ModelAttribute("name")
//    public String userModel2(@RequestParam(value = "name", required = false) String name) {
//        System.out.println("用户名："+name);
//        return name;
//    }
}
