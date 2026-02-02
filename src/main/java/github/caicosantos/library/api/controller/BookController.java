package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.BookRegistrationDTO;
import github.caicosantos.library.api.controller.dto.BookResultSearchDTO;
import github.caicosantos.library.api.controller.mappers.BookMapper;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController implements GenericController {
    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid BookRegistrationDTO dto) {
        Book book = mapper.toEntity(dto);
        service.save(book);
        return ResponseEntity
                .created(generateHeaderLocation(book.getId()))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResultSearchDTO> getById(@PathVariable UUID id) {
        return service.getById(id)
                .map(book -> {
                    BookResultSearchDTO dto = mapper.toDTO(book);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id) {
        return service.getById(id)
                .map(book -> {
                    service.deleteById(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
