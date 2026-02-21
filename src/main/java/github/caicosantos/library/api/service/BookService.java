package github.caicosantos.library.api.service;

import github.caicosantos.library.api.exceptions.SearchCombinationNotFoundException;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.model.User;
import github.caicosantos.library.api.model.enums.GenderBook;
import github.caicosantos.library.api.repository.BookRepository;
import github.caicosantos.library.api.repository.UserRepository;
import github.caicosantos.library.api.repository.specs.BookSpecs;
import github.caicosantos.library.api.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator validator;
    private final UserRepository userRepository;

    public Book save(Book book) {
        validator.validate(book);
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(currentLogin).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        book.setUser(user);
        return bookRepository.save(book);
    }

    public Optional<Book> getById(UUID id) {
        return bookRepository.findById(id);
    }

    public void deleteById(Book book) {
        bookRepository.delete(book);
    }

    public Page<Book> search(String isbn, String title, String nameAuthor, GenderBook gender, Integer yearPublication, Integer page, Integer pageSize) {

        Specification<Book> spec = Specification.where((root, cq, cb) -> cb.conjunction());

        if(isbn != null && !isbn.isBlank()) {
            spec = spec.and(BookSpecs.isbnEqual(isbn));
        }

        if(title != null && !title.isBlank()) {
            spec = spec.and(BookSpecs.titleLike(title));
        }

        if(nameAuthor != null && !nameAuthor.isBlank()) {
            spec = spec.and(BookSpecs.nameAuthorLike(nameAuthor));
        }

        if(gender != null) {
            spec = spec.and(BookSpecs.genderEqual(gender));
        }

        if(yearPublication != null) {
            spec = spec.and(BookSpecs.yearPublicationEqual(yearPublication));
        }

        Pageable pageRequest = PageRequest.of(page, pageSize);

        Page<Book> list = bookRepository.findAll(spec, pageRequest);
        if(list.isEmpty()) {
            throw new SearchCombinationNotFoundException("There is not Book with that combination!");
        }
        return list;
    }

    public Book update(Book book) {
        if(book.getId()!=null) {
            validator.validate(bookRepository.save(book));
        }
        throw new IllegalArgumentException("To update, the book must exist in the database!");
    }
}