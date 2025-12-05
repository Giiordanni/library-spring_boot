package com.giordanni.libraryapi.repository;

import com.giordanni.libraryapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    User findByLogin(String login);
}
