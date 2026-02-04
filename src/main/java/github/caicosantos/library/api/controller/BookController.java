package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.BookRegistrationDTO;
import github.caicosantos.library.api.controller.dto.BookResultSearchDTO;
import github.caicosantos.library.api.controller.mappers.BookMapper;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.model.enums.GenderBook;
import github.caicosantos.library.api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<BookResultSearchDTO>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String nameAuthor,
            @RequestParam(required = false) GenderBook gender,
            @RequestParam(required = false) Integer yearPublication,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<Book> result = service.search(name, title, nameAuthor, gender, yearPublication, page, pageSize);
        Page<BookResultSearchDTO> resultPage = result.map(mapper::toDTO);
        return ResponseEntity.ok(resultPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody @Valid BookRegistrationDTO dto, @PathVariable UUID id) {
        return service.getById(id)
                .map(book -> {
                    Book entity = mapper.toEntity(dto);
                    book.setIsbn(entity.getIsbn());
                    book.setTitle(entity.getTitle());
                    book.setDatePublication(entity.getDatePublication());
                    book.setGender(entity.getGender());
                    book.setPrice(entity.getPrice());
                    book.setAuthor(entity.getAuthor());
                    service.update(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
