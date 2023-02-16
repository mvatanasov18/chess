package com.example.chess.services;

import com.example.chess.models.User;
import com.example.chess.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CookiesService {
    @Autowired
    private UserRepository repository;

    public Boolean checkCookie(Cookie[] cookies){
        if (cookies != null) {
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("session")){
                    User u=repository.findBySession(cookie.getValue());
                    System.out.println(u);
                    return u.getUsername().isEmpty();
                }
            }
        }
        System.out.println("vrushtam tru");
        return true;
    }
}
