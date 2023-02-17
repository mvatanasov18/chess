package com.example.chess.controllers;

import com.example.chess.models.Event;
import com.example.chess.models.User;
import com.example.chess.repositories.EventRepository;
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

import java.util.Set;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CookiesService service;
    @Autowired
    private EventRepository eventRepository;
    @GetMapping(value = "/")
    public String home(HttpServletRequest request) {
        if(service.checkCookie(request.getCookies()))
        {
            return "index";
        }

        return "redirect:/Home";
    }

    @GetMapping(value = "Home")
    public String bashHome(HttpServletRequest request,Model model){
        if(service.checkCookie(request.getCookies()))
        {
            return "index";
        }
        model.addAttribute("events",eventRepository.findAll());
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
    public String postLogin(@ModelAttribute("user") User user, HttpServletResponse response){

        User temp = userRepository.findByUsername(user.getUsername());
        if(temp!=null) {
            String id = UUID.randomUUID().toString();

            user.setSesiq(id);
            userRepository.saveSessionByUsername(user.getSesiq(), user.getUsername());
            userRepository.setEventsForUser(user);
            Cookie cookie=new Cookie("session",id);
            cookie.setMaxAge(7*24*60*60);
            response.addCookie(cookie);
            return "redirect:/";
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
    public String postRegister(@ModelAttribute("user") User user){

        User temp = userRepository.findByUsername(user.getUsername());
        if(temp==null){

            try {

                int res = userRepository.save(user);

                userRepository.findByUsername(user.getUsername());
                if(res==1){
                    return "login";
                }
                else{
                    return "errorRegistration";
                }
            }catch(Exception e){
                e.printStackTrace();
                return "errorRegistration";
            }
        }else{
            return "Error";
        }
    }

    @GetMapping("/events")
    public String events(HttpServletRequest request,Model model){
        if(service.checkCookie(request.getCookies()))
        {
            return "Error";
        }
        model.addAttribute("event",new Event());
        return "events";
    }

    @PostMapping("/createEvent")
    public String postCreateEvent(@ModelAttribute("event") Event event,HttpServletRequest request){
        System.out.println("nz dali idvam tuk");
        User user=service.getSessionName(request.getCookies());
        System.out.println("test");
        if(user!=null) {
            System.out.println("tuka summm");
            Set<Event> temp=user.getEvents();
            temp.add(event);
            user.setEvents(temp);
            event.setUser(user);
            System.out.println(event);
            eventRepository.save(event);
            userRepository.update(user);
            return "redirect:/";
        }
        else{
            return "Error";
        }
    }
}
