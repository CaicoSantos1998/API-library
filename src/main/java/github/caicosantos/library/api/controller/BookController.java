package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.BookRegistrationDTO;
import github.caicosantos.library.api.controller.dto.BookResultSearchDTO;
import github.caicosantos.library.api.controller.mappers.BookMapper;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.model.enums.GenderBook;
import github.caicosantos.library.api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<BookResultSearchDTO>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String nameAuthor,
            @RequestParam(required = false) GenderBook gender,
            @RequestParam(required = false) Integer yearPublication) {
        var result = service.search(name, title, nameAuthor, gender, yearPublication);
        var list = result.stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }
}
