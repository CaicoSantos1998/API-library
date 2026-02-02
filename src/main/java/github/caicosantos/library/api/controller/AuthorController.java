package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.AuthorDTO;
import github.caicosantos.library.api.controller.mappers.AuthorMapper;
import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO dto) {
        Author author = mapper.toEntity(dto);
        service.save(author);
        return ResponseEntity
                .created(generateHeaderLocation(author.getId()))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable UUID id) {
        return service
                .getById(id)
                .map(author -> {
                    AuthorDTO dto = mapper.toDTO(author);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<Author> obj = service.getById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(obj.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> search(@RequestParam(required = false) String name, @RequestParam(required = false) String nationality) {
        List<Author> resultSearch = service.search(name, nationality);
        List<AuthorDTO> authorList =
                resultSearch
                        .stream()
                        .map(mapper::toDTO)
                        .toList();
        return ResponseEntity.ok(authorList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid AuthorDTO authorDTO, @PathVariable UUID id) {
        Optional<Author> obj = service.getById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var author = obj.get();
        author.setName(authorDTO.name());
        author.setNationality(authorDTO.nationality());
        author.setBirthDate(authorDTO.birthDate());
        service.update(author);
        return ResponseEntity.noContent().build();
    }
}
