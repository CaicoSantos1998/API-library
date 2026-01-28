package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.AuthorDTO;
import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
}
