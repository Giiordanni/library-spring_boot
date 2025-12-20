package com.giordanni.libraryapi.controller;

import com.giordanni.libraryapi.model.Client;
import com.giordanni.libraryapi.services.ClientServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientServices clientServices;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void saveClient(@RequestBody Client client){
        clientServices.saveClient(client);
    }
}
