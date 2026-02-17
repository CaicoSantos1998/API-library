package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.AuthorRegistrationDTO;
import github.caicosantos.library.api.controller.dto.AuthorResultSearchDTO;
import github.caicosantos.library.api.controller.mappers.AuthorMapper;
import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorRegistrationDTO dto) {
        Author author = mapper.toEntity(dto);
        service.save(author);
        return ResponseEntity
                .created(generateHeaderLocation(author.getId()))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResultSearchDTO> getById(@PathVariable UUID id) {
        return service
                .getById(id)
                .map(author -> {
                    AuthorResultSearchDTO dto = mapper.toDTO(author);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        return service.getById(id)
                .map(author -> {
                    service.deleteById(author);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AuthorResultSearchDTO>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nationality) {
        List<Author> resultSearch = service.search(name, nationality);
        List<AuthorResultSearchDTO> authorList =
                resultSearch
                        .stream()
                        .map(mapper::toDTO)
                        .toList();
        return ResponseEntity.ok(authorList);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> update(
            @RequestBody @Valid AuthorRegistrationDTO dto,
            @PathVariable UUID id) {
        Optional<Author> obj = service.getById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var author = obj.get();
        author.setName(dto.name());
        author.setNationality(dto.nationality());
        author.setBirthDate(dto.birthDate());
        service.update(author);
        return ResponseEntity.noContent().build();
    }
}
