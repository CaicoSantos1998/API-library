package github.caicosantos.library.api.repository;

import github.caicosantos.library.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryUser extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);
}
