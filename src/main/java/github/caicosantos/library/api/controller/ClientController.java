package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.ClientRegistrationDTO;
import github.caicosantos.library.api.controller.dto.ClientResultSearchDTO;
import github.caicosantos.library.api.controller.mappers.ClientMapper;
import github.caicosantos.library.api.exceptions.DuplicateRegisterException;
import github.caicosantos.library.api.model.Client;
import github.caicosantos.library.api.repository.ClientRepository;
import github.caicosantos.library.api.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController implements GenericController {
    private final ClientService service;
    private final ClientMapper mapper;
    private final ClientRepository repository;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> save(@RequestBody @Valid ClientRegistrationDTO dto) {
        Client client = mapper.toEntity(dto);
        service.save(client);
        return ResponseEntity
                .created(generateHeaderLocation(client.getId()))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResultSearchDTO> getById(@PathVariable String id) {
        return service
                .getByClientId(id)
                .map(client -> {
                    ClientResultSearchDTO dto = mapper.toDTO(client);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        return service
                .getById(id)
                .map(clientId -> {
                    service.deleteById(clientId);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> update(
            @RequestBody @Valid ClientResultSearchDTO dto,
            @PathVariable UUID id) {
        Optional<Client> obj = service.getById(id);
        if(obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if(repository.existsByClientIdAndIdNot(dto.clientId(), id)) {
            throw new DuplicateRegisterException("This client is already in the database!");
        }
        var client = obj.get();
        client.setClientId(dto.clientId());
        client.setRedirectURI(dto.redirectURI());
        client.setScope(dto.scope());
        service.save(client);
        return ResponseEntity.noContent().build();
    }
}
