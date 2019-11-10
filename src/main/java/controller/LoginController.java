package controller;

import exception.UserException;
import org.apache.commons.io.FileUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pojo.User;
import pojo.convert.DateEditor;

import javax.jws.WebResult;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.event.TextEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user", method = RequestMethod.POST)
//@SessionAttributes(names = {"username"}, types = {String.class})
public class LoginController {

    @PostMapping(value = "/login.action")
    public ModelAndView login(User user) {
        System.out.println("user: "+ user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/main");
        return modelAndView;
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(Date.class, new DateEditor());
//    }

    @GetMapping("/main")
    public void main(HttpServletRequest request, @RequestParam("a") String params) {
        System.out.println(params);
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());
        System.out.println("main");
    }

    @GetMapping("/register.action/{username}")
    public ResponseEntity register(@ModelAttribute(name = "username") int username, HttpServletResponse httpServletResponse) {
        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).eTag("1").body("213");
    }

    @PostMapping("/upload")
    @ResponseBody
    public User uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest webRequest, User user) throws IOException {
        String path = webRequest.getServletContext().getRealPath("/upload");
        System.out.println("user:" + user);
        File fileContainer = new File(path);
        if (!fileContainer.exists()) {
            fileContainer.mkdirs();
        }
        if (file!=null && !file.isEmpty()) {
            String filename = file.getOriginalFilename();
            filename += System.currentTimeMillis();
            File uploadFile = new File(path + "/"+filename);
            System.out.println(webRequest.getContextPath());
            System.out.println("filename:" + uploadFile.getPath());
            file.transferTo(uploadFile);
        }

        return user;
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable("filename") String filename, HttpServletRequest request,
                                           @RequestHeader("User-Agent") String userAgent) throws IOException {
        String path = request.getServletContext().getRealPath("/upload")+File.separator+filename;
        File downloadFile = new File(path);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
//        header("Transfer-Encoding", "chunked")
        Cookie cookie = new Cookie("", "");
        bodyBuilder
                .contentType(MediaType.APPLICATION_OCTET_STREAM);
        filename = URLEncoder.encode(filename, "UTF-8");
        if (userAgent.contains("MSIE")) {
            bodyBuilder.header("Content-Disposition", "attachment;filename=''"+ filename);
        } else  {
            bodyBuilder.header("Content-Disposition", "attachment;filename*=UTF-8''"+ filename);
        }

        return bodyBuilder.body(FileUtils.readFileToByteArray(downloadFile));
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
