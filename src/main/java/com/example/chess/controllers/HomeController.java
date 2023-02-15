package com.example.chess.controllers;

import com.example.chess.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chess")
public class HomeController {
    @GetMapping()
    public String home() {
        return "Home";
    }

    @GetMapping("/makeGame")
    public String makeGame() {
        return "MakeGame";
    }

    @GetMapping("/getGame")
    public String getGame() {
        return "chess_get_id";
    }
    @GetMapping("/register")
    public String register(Model model){
model.addAttribute("user",new User());
        return "register";
    }
    @PostMapping("register")
    public String postRegister(@ModelAttribute("user") User user, Model model){

        return "";
    }
}
