package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.config.ApiConflictErrors;
import github.caicosantos.library.api.config.ApiStandardErrors;
import github.caicosantos.library.api.controller.dto.BookRegistrationDTO;
import github.caicosantos.library.api.controller.dto.BookResultSearchDTO;
import github.caicosantos.library.api.controller.mappers.BookMapper;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.model.enums.GenderBook;
import github.caicosantos.library.api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Books")
public class BookController implements GenericController {
    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'OPERATOR')")
    @Operation(summary = "Save", description = "Register the new book!")
    @ApiStandardErrors
    @ApiConflictErrors
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Record created successfully!"),
            @ApiResponse(responseCode = "422", description = "Please check the information provided!")
    })
    public ResponseEntity<Void> save(@RequestBody @Valid BookRegistrationDTO dto) {
        Book book = mapper.toEntity(dto);
        service.save(book);
        return ResponseEntity
                .created(generateHeaderLocation(book.getId()))
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by Id", description = "Find a specific book using its unique ID!")
    @ApiStandardErrors
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request completed successfully!"),
            @ApiResponse(responseCode = "404", description = "We couldn't find the requested book!")
    })
    public ResponseEntity<BookResultSearchDTO> getById(@PathVariable UUID id) {
        return service.getById(id)
                .map(book -> {
                    BookResultSearchDTO dto = mapper.toDTO(book);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'OPERATOR')")
    @Operation(summary = "Delete", description = "Delete the book!")
    @ApiStandardErrors
    @ApiConflictErrors
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Record removed successfully!"),
            @ApiResponse(responseCode = "404", description = "We couldn't find the requested book!")
    })
    public ResponseEntity<Object> deleteById(@PathVariable UUID id) {
        return service.getById(id)
                .map(book -> {
                    service.deleteById(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Search", description = "Perform book searches using parameters!")
    @ApiStandardErrors
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request completed successfully!"),
            @ApiResponse(responseCode = "404", description = "We couldn't find the requested book!")
    })
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
    @PreAuthorize("hasAnyRole('MANAGER', 'OPERATOR')")
    @Operation(summary = "Update", description = "Updated the data for a book!")
    @ApiStandardErrors
    @ApiConflictErrors
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Record updated successfully!"),
            @ApiResponse(responseCode = "404", description = "We couldn't find the requested book!")
    })
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
