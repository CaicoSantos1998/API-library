package github.caicosantos.library.api.service;

import github.caicosantos.library.api.model.User;
import github.caicosantos.library.api.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RepositoryUser repository;
    private final PasswordEncoder encoder;

    public void save(User user) {
        var password = user.getPassword();
        user.setPassword(encoder.encode(password));
        repository.save(user);
    }

    public Optional<User> getByLogin(String login) {
        return repository.findByLogin(login);
    }
}
