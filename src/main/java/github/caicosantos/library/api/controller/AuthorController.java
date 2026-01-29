package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.AuthorDTO;
import github.caicosantos.library.api.controller.dto.ErrorResponse;
import github.caicosantos.library.api.exceptions.DuplicateRegisterException;
import github.caicosantos.library.api.exceptions.OperationNotPermittedException;
import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AuthorDTO authorDTO) {
        try {
            Author obj = authorDTO.mappingToAuthor();
            service.save(obj);
            return ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(obj.getId())
                    .toUri()).build();
        } catch (DuplicateRegisterException e) {
            var dtoError = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable UUID id) {
        Optional<Author> obj = service.getById(id);
        if(obj.isPresent()) {
            Author author = obj.get();
            AuthorDTO dto = new AuthorDTO(author.getId(), author.getName(), author.getBirthDate(), author.getNationality());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            Optional<Author> obj = service.getById(id);
            if(obj.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            service.deleteById(obj.get());
            return ResponseEntity.noContent().build();
        } catch (OperationNotPermittedException e) {
            var errorResponse = ErrorResponse.responseStandard(e.getMessage());
            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> search(@RequestParam(required = false) String name, @RequestParam(required = false) String nationality) {
        List<Author> resultSearch = service.search(name, nationality);
        List<AuthorDTO> authorList =
                resultSearch
                .stream()
                .map(
                        author -> new AuthorDTO(author.getId(), author.getName(), author.getBirthDate(), author.getNationality())
                ).toList();
        return ResponseEntity.ok(authorList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody AuthorDTO authorDTO, @PathVariable UUID id) {
        try {
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
        } catch (DuplicateRegisterException e) {
            var dtoError = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }
}
