package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.BookRegistrationDTO;
import github.caicosantos.library.api.controller.dto.ErrorResponse;
import github.caicosantos.library.api.controller.mappers.BookMapper;
import github.caicosantos.library.api.exceptions.DuplicateRegisterException;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController implements GenericController {
    private final BookService bookService;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid BookRegistrationDTO dto) {
        try {
            Book book = mapper.toEntity(dto);
            bookService.save(book);
            return ResponseEntity
                    .created(generateHeaderLocation(book.getId()))
                    .build();
        } catch (DuplicateRegisterException e) {
            var errorDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity
                    .status(errorDTO.status())
                    .body(errorDTO);
        }
    }
}
