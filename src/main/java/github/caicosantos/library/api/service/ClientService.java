package github.caicosantos.library.api.service;

import github.caicosantos.library.api.controller.GenericController;
import github.caicosantos.library.api.model.Client;
import github.caicosantos.library.api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements GenericController {
    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public void save(Client client) {
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        repository.save(client);
    }

    public Optional<Client> getByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }
}
