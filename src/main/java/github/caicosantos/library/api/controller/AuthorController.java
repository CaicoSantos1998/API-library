package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.AuthorDTO;
import github.caicosantos.library.api.controller.dto.ErrorResponse;
import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService service;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AuthorDTO authorDTO) {
        Author obj = authorDTO.mappingToAuthor();
        service.save(obj);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri()).build();
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
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<Author> obj = service.getById(id);
        if(obj.isEmpty()) {
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
                .map(
                        author -> new AuthorDTO(author.getId(), author.getName(), author.getBirthDate(), author.getNationality())
                ).toList();
        return ResponseEntity.ok(authorList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody AuthorDTO authorDTO, @PathVariable UUID id) {
        Optional<Author> obj = service.getById(id);
        if(obj.isEmpty()) {
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
