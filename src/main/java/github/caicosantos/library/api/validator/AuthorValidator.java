package github.caicosantos.library.api.validator;

import github.caicosantos.library.api.exceptions.DuplicateRegisterException;
import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    @Autowired
    private AuthorRepository repository;

    public void validate(Author author) {
        if(authorExist(author)) {
            throw new DuplicateRegisterException("The author already exist in the database!");
        }
    }

    private boolean authorExist(Author author) {
        Optional<Author> authorFound = repository.findByNameAndBirthDateAndNationality(author.getName(), author.getBirthDate(), author.getNationality());
        if(author.getId()==null) {
            return authorFound.isPresent();
        }
        return !author.getId().equals(authorFound.get().getId()) && authorFound.isPresent();
    }
}
