package com.giordanni.libraryapi.controller;

import com.giordanni.libraryapi.seucurity.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // Controller para gerenciar a exibição da página de login
public class LoginViewController {

    @GetMapping("/login")
    public String pageLogin(){
        return "login";
    }

    @GetMapping("/home")
    @ResponseBody
    public String pageHome(Authentication authentication){
        // if(authentication instanceof CustomAuthentication customAuth) System.out.println(customAuth.getUser());
        return "Olá " + authentication.getName();
    }
}
