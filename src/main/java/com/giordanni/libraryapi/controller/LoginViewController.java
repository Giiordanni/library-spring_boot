package com.giordanni.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Controller para gerenciar a exibição da página de login
public class LoginViewController {

    @GetMapping("/login")
    public String pageLogin(){
        return "login";
    }
}
