package github.caicosantos.library.api.service;

import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author save(Author author) {
        return repository.save(author);
    }
    
    public Optional<Author> getById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(Author author) {
        repository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
        if(name!=null && nationality!=null) {
            return repository.findByNameOrNationality(name, nationality);
        }
        if(name!=null) {
            return repository.findByName(name);
        }
        if(nationality!=null) {
            return repository.findByNationality(nationality);
        }
        return repository.findAll();
    }

    public Author update(Author author) {
        if(author.getId()!=null){
            return repository.save(author);
        }
        throw new IllegalArgumentException("To update, the author must exist in the database");
    }
}
