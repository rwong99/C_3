package com.nowcoder.controller;

import com.nowcoder.model.User;
import com.nowcoder.service.ToutiaoService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by WCZ on 2016/6/27.
 */
@Controller
public class IndexController {
    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping(path = {"/","/index"})
    @ResponseBody
    public String index(HttpSession session) {

        return  "Hello "+session.getAttribute("msg")+"<br>"+toutiaoService.say();
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type",defaultValue = "1") int type,
                          @RequestParam(value = "key",defaultValue = "nowcoder") String key
                          ) {
        return String.format("%d","%s","%s","%d",groupId,userId,type,key);
    }

    @RequestMapping(path = {"/vm"})
    public String news(Model model) {
        model.addAttribute("value1","vv1");
        List<String> colors = Arrays.asList(new String[] {"RED","BLUE","GREEN"});
        Map<String,String> map = new HashMap<String,String>();
        for (int i = 0;i<4;i++) {
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);
        model.addAttribute("colors",colors);
        model.addAttribute("user",new User("Jim"));
        return "news";
    }

    @RequestMapping(path = {"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name+":::::"+request.getHeaderNames()+"<br>");

        }
        for(Cookie cookie : request.getCookies()) {
            sb.append("Cookie::::"+cookie.getName());
            sb.append("--------");
            sb.append(cookie.getValue());
            sb.append("<br>");


        }

        sb.append("getPathInfo:"+request.getPathInfo()+"<br>");
        sb.append("getMethod:"+request.getMethod()+"<br>");
        sb.append("getQueryString:"+request.getQueryString()+"<br>");
        sb.append("getRequestURI:"+request.getRequestURI());

        return sb.toString();
    }

    @RequestMapping(path = {"/response"})
    @ResponseBody
    public String response(@CookieValue(value = "nowcoderid",defaultValue = "cz") String nowcoderid,
                           @RequestParam(value = "key",defaultValue = "key") String key,
                           @RequestParam(value = "value",defaultValue = "value") String value,
                           HttpServletResponse response) {
        response.addCookie(new Cookie(key, value));
        response.addHeader(key, value);
        return "nowcoderId From Cookie" + "  "+nowcoderid;
    }

    @RequestMapping("/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession session) {
       /* RedirectView red = new RedirectView("/",true);
        if(code == 301) red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return red;*/

        session.setAttribute("msg","Jump From Redirect.");
        return "redirect:/";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key",required = true) String key) {
        if("admin".equals(key))
            return "hello admin";
        throw new IllegalArgumentException("key 错误");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
       return "<br>"+"<br>"+"error:"+e.getMessage();
    }

}
