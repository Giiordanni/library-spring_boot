package com.giordanni.libraryapi.services;

import com.giordanni.libraryapi.model.User;
import com.giordanni.libraryapi.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder encoder;


    public void saveUser(User user){
        var password = user.getPassword();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    public User getByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
