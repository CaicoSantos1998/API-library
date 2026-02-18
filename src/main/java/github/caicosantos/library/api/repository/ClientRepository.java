package github.caicosantos.library.api.repository;

import github.caicosantos.library.api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByClientId(String clientId);
    boolean existsByClientIdAndIdNot(String clientId, UUID id);
}
