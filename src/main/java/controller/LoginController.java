package controller;

import exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import pojo.User;
import pojo.convert.DateEditor;

import javax.jws.WebResult;
import javax.servlet.http.HttpSession;
import java.awt.event.TextEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user", method = RequestMethod.POST)
//@SessionAttributes(names = {"username"}, types = {String.class})
public class LoginController {

    @PostMapping("/login.action")
    public ResponseEntity<User> login(User user) {
        System.out.println("user: "+ user);
        return ResponseEntity.ok(user);
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(Date.class, new DateEditor());
//    }

    @GetMapping("/register.action/{username}")
    public String register(@ModelAttribute(name = "username") int username, Model model) {
        return "register";
    }

//    @ModelAttribute
//    public String userModel1(String password,
//                          Model model) {
//        System.out.println("密码："+password);
//        return password;
//    }
//
//    @ModelAttribute("name")
//    public String userModel2(@RequestParam(value = "name", required = false) String name) {
//        System.out.println("用户名："+name);
//        return name;
//    }
}
