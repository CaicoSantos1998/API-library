package github.caicosantos.library.api.service;

import github.caicosantos.library.api.exceptions.OperationNotPermittedException;
import github.caicosantos.library.api.exceptions.SearchCombinationNotFoundException;
import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.repository.AuthorRepository;
import github.caicosantos.library.api.repository.BookRepository;
import github.caicosantos.library.api.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorValidator authorValidator;

    public Author save(Author author) {
        authorValidator.validate(author);
        return authorRepository.save(author);
    }
    
    public Optional<Author> getById(UUID id) {
        return authorRepository.findById(id);
    }

    public void deleteById(Author author) {
        if(authorHaveBook(author)) {
            throw new OperationNotPermittedException("Operation not permitted! The author has registered books in the database!");
        }
        authorRepository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "birthDate")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> authorExample = Example.of(author, matcher);
        List<Author> authors = authorRepository.findAll(authorExample);
        if(authors.isEmpty()) {
            throw new SearchCombinationNotFoundException("There is no author with that combination!");
        }
        return authors;
    }

    public Author update(Author author) {
        if(author.getId()!=null){
            authorValidator.validate(author);
            return authorRepository.save(author);
        }
        throw new IllegalArgumentException("To update, the author must exist in the database");
    }

    private boolean authorHaveBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
