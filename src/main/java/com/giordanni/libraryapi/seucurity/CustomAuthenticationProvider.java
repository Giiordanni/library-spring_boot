package com.giordanni.libraryapi.seucurity;

import com.giordanni.libraryapi.model.User;
import com.giordanni.libraryapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String passwordForms = authentication.getCredentials().toString();

        User userFound = userService.getByLogin(login);

        if(userFound == null) {
            throw getErrorUserNotFound();
        }

        String passwordEncrypted = userFound.getPassword();
        boolean passwordMatches = passwordEncoder.matches(passwordForms, passwordEncrypted); // passa primiero a senha digitada e depois a senha encontrada

        if(passwordMatches) {
            return new CustomAuthentication(userFound);
        }

        throw getErrorUserNotFound();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

    private UsernameNotFoundException getErrorUserNotFound() {
        return new UsernameNotFoundException("User and/or password invalid");
    }
}
