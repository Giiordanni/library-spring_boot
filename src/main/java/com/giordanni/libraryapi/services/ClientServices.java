package com.giordanni.libraryapi.services;

import com.giordanni.libraryapi.model.Client;
import com.giordanni.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServices {


    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client saveClient(Client client) {
        // poderia validar para não salvar dois clients com o mesmo clientId
        var passwordCryptographic = encoder.encode(client.getClientSecret());
        client.setClientSecret(passwordCryptographic);
        return repository.save(client);
    }

    public Client getByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }
}
