package github.caicosantos.library.api.service;

import github.caicosantos.library.api.exceptions.OperationNotPermittedException;
import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.repository.AuthorRepository;
import github.caicosantos.library.api.repository.BookRepository;
import github.caicosantos.library.api.validator.AuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorValidator authorValidator;

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
        if(name!=null && nationality!=null) {
            return authorRepository.findByNameOrNationality(name, nationality);
        }
        if(name!=null) {
            return authorRepository.findByName(name);
        }
        if(nationality!=null) {
            return authorRepository.findByNationality(nationality);
        }
        return authorRepository.findAll();
    }

    public Author update(Author author) {
        if(author.getId()!=null){
            authorValidator.validate(author);
            return authorRepository.save(author);
        }
        throw new IllegalArgumentException("To update, the author must exist in the database");
    }

    public boolean authorHaveBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
