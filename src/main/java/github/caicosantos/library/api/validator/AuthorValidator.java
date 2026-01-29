package github.caicosantos.library.api.validator;

import github.caicosantos.library.api.exceptions.DuplicateRegisterException;
import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorValidator {

    private final AuthorRepository repository;

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
        return authorFound.isPresent() && !author.getId().equals(authorFound.get().getId());
    }
}
