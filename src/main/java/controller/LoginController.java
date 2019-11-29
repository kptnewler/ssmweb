package controller;

import javafx.util.Pair;
import org.apache.commons.fileupload.DefaultFileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/register.action/{userName}")
    public ResponseEntity register(@ModelAttribute(name = "userName") int username, HttpServletResponse httpServletResponse) {
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
            filename = System.currentTimeMillis()+filename;
            File uploadFile = new File(path + "/"+filename);
            System.out.println(webRequest.getContextPath());
            System.out.println("filename:" + uploadFile.getPath());
            file.transferTo(uploadFile);
        }

        return user;
    }

    @RequestMapping(path = "/download/{filename}", method = {RequestMethod.GET}, headers = "Range")
    public ResponseEntity<byte[]> download(@PathVariable("filename") String filename, HttpServletRequest request,
                                           @RequestHeader("User-Agent") String userAgent,
                                           RequestMethod requestMethod,
                                           @RequestHeader("Range")String range) throws IOException {

        String path = request.getServletContext().getRealPath("/upload")+File.separator+filename;

        String[] startAndEnd = range.trim().replaceAll("bytes=", "").split("-");
        int start = -1;
        int end = Integer.MAX_VALUE;
        if (startAndEnd.length == 2) {
            start = Integer.parseInt(startAndEnd[0]);
            end = Integer.parseInt(startAndEnd[1]);
        }
        File downloadFile = new File(path);
        if (!downloadFile.exists() || start < 0 || end > downloadFile.length()) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).build();
        }

        FileInputStream fileInputStream = new FileInputStream(downloadFile);
        byte[] fileData = new byte[end - start + 1];
        int length = fileInputStream.read(fileData, start , fileData.length);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(length);
        headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");
        headers.set(HttpHeaders.CONTENT_RANGE,
                "bytes " + start + "-" + end + "/" + downloadFile.length());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setConnection("keep-alive");

        filename = URLEncoder.encode(filename, "UTF-8");
        if (userAgent.contains("MSIE")) {
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());
        } else  {
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename(filename, StandardCharsets.UTF_8).build());
        }
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).headers(headers).body(fileData);
    }

    @RequestMapping(path = "/download/{filename}", method = RequestMethod.HEAD)
    public ResponseEntity downloadHeaders(@PathVariable("filename") String fileName,
                                       HttpServletRequest httpServletRequest) {
        File file = new File(httpServletRequest.getServletContext().getRealPath("/upload")+File.separator+fileName);

        if (file.exists()) {
            System.out.println(file.length());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.ACCEPT_RANGES, "bytes");
            httpHeaders.setContentLength(file.length());
            httpHeaders.setConnection("keep-alive");
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).build();
        } else  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/download/chucked/{filename}")
    public ResponseEntity<byte[]> downloadFileChucked(@PathVariable("filename") String fileName,
                                                      @RequestHeader("User-Agent") String userAgent,
                                                      HttpServletRequest httpServletRequest) throws IOException {
        File file = new File(httpServletRequest.getServletContext().getRealPath("/upload")+File.separator+fileName);

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
//            headers.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
            headers.setConnection("keep-alive");
            headers.set("Keep-alive", "timeout=5, max=2");

            fileName = URLEncoder.encode(fileName, "UTF-8");
            if (userAgent.contains("MSIE")) {
                headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
            } else  {
                headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName)
                       .build());
            }
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(FileUtils.readFileToByteArray(file));
        }
    }

    @GetMapping(path = "/download/mulit/range/{filename}", headers = "Range")
    public ResponseEntity<byte[]> downloadFileMutliRange(@PathVariable("filename") String fileName,
                                                      @RequestHeader("User-Agent") String userAgent,
                                                      HttpServletRequest httpServletRequest,
                                                         @RequestHeader("Ranage") String rangeHeader) throws IOException {
        File file = new File(httpServletRequest.getServletContext().getRealPath("/upload")+File.separator+fileName);

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String[] ranges = rangeHeader.trim().replaceAll("bytes=", "").split(",");
        List<Pair<Long, Long>> startAndEndList = new ArrayList<>(ranges.length);

        for (String range : ranges) {
            long x = -1L;
            long y = Long.MAX_VALUE;
            String start = range.substring(0, range.indexOf("-"));
            String end = range.substring(range.indexOf(("-"), range.length()));
            if (start.isEmpty() && end.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            if (start.isEmpty()) {
                x = file.length() - Long.parseLong(end);
                y = file.length() -1;
            }else if (end.isEmpty()) {
                x = Long.parseLong(start);
                y = file.length() -1;
            } else {
                x = Long.parseLong(start);
                y = Long.parseLong(end);
            }

            startAndEndList.add(new Pair<>(x, y));


            for (Pair<Long, Long> startAndEnd : startAndEndList) {
                if (startAndEnd.getKey() < 0 || startAndEnd.getValue() >= file.length()) {
                    return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).build();
                }

            }
        }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("multipart/byteranges; boundary=00000000001"));
            headers.setConnection("keep-alive");
            headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");

            Long length = 0L;
            fileName = URLEncoder.encode(fileName, "UTF-8");
            if (userAgent.contains("MSIE")) {
                headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
            } else  {
                headers.setContentDisposition(ContentDisposition.builder("attachment")
                        .filename(fileName, StandardCharsets.UTF_8).build());
            }
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(FileUtils.readFileToByteArray(file));
        }
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
//}
