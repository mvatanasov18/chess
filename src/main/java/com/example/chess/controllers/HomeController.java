package com.example.chess.controllers;

import com.example.chess.models.User;
import com.example.chess.repositories.UserRepository;
import com.example.chess.services.CookiesService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CookiesService service;
    @GetMapping(value = "/")
    public String home(HttpServletRequest request) {
        if(service.checkCookie(request.getCookies()))
        {
            return "index";
        }
        return "Home";
    }

    @GetMapping(value = "/makeGame")
    public String makeGame(HttpServletRequest request) {

        if(service.checkCookie(request.getCookies()))
        {
            return "Error";
        }
        return "MakeGame";
    }

    @GetMapping(value = "/getGame")
    public String getGame(HttpServletRequest request) {

        if(service.checkCookie(request.getCookies()))
        {
            return "Error";
        }
        return "chess_get_id";
    }
    @GetMapping(value = "/register")
    public String register(Model model,HttpServletRequest request){

        if(service.checkCookie(request.getCookies()))
        {
            model.addAttribute("user",new User());
            return "/register";
        }
        return "Error";
    }
    @PostMapping(value = "/login")
    public String postLogin(@ModelAttribute("user") User user, Model model, HttpServletResponse response){

        User temp = userRepository.findByUsername(user.getUsername());
        if(temp!=null) {
            String id = UUID.randomUUID().toString();
            Cookie cookie=new Cookie("session",id);
            cookie.setMaxAge(7*24*60*60);
            response.addCookie(cookie);
            user.setSession(id);
            userRepository.saveSessionByUsername(user.getSession(), user.getUsername());
            return "/chess";
        }else{
            return "Error";
        }
    }
    @GetMapping(value = "/login")
    public String login(Model model,HttpServletRequest request){
        System.out.println("vlizam v login");
        if(service.checkCookie(request.getCookies()))
        {
            model.addAttribute("user",new User());
            System.out.println("izlizam v login");
            return "login";

        }
        System.out.println("greshka");
        return "Error";
    }
    @PostMapping(value = "/register")
    public String postRegister(@ModelAttribute("user") User user, Model model){

        User temp = userRepository.findByUsername(user.getUsername());
        if(temp==null){
            try {
                int res = userRepository.save(user);
                System.out.println(res);
                if(res==0){
                    return "login";
                }
                else{
                    return "errorRegistration";
                }
            }catch(Exception e){
                return "errorRegistration";
            }
        }else{
            return "Error";
        }
    }
}
