package github.caicosantos.library.api.validator;

import github.caicosantos.library.api.exceptions.DuplicateRegisterException;
import github.caicosantos.library.api.exceptions.InvalidRecordException;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private final BookRepository repository;

    public void validate(Book book) {
        if(existBookIsbn(book)) {
            throw new DuplicateRegisterException("This ISBN is already registered!");
        }
        if(book.getAuthor()==null || book.getAuthor().getId()==null) {
            throw new InvalidRecordException("Author is required to register a book!");
        }
    }

    private boolean existBookIsbn(Book book) {
        Optional<Book> findIsbn = repository.findByIsbn(book.getIsbn());
        if(book.getId()==null) {
            return findIsbn.isPresent();
        }
        return findIsbn
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }
}
