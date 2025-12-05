package com.giordanni.libraryapi.seucurity;


import com.giordanni.libraryapi.model.User;
import com.giordanni.libraryapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    public User getUserLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String login = userDetails.getUsername();

        return userService.getByLogin(login);

    }
}
