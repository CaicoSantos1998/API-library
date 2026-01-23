package github.caicosantos.library.api.repository;

import github.caicosantos.library.api.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

    @Test
    void saveTest() {
        Author author = new Author();
        author.setName("Jose");
        author.setBirthDate(LocalDate.of(1995, 10, 2));
        author.setNationality("Brazilian");

        repository.save(author);
    }

    @Test
    void updateTest() {
        Optional<Author> optional = repository.findById(UUID.fromString("7832870e-3925-4405-ad4f-b296fb3566d9"));
        if(optional.isPresent()) {
            Author authorExist = optional.get();
            authorExist.setBirthDate(LocalDate.of(1997, 10, 30));

            repository.save(authorExist);
        }
    }

    @Test
    void findAllTest() {
        List<Author> list = repository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    void countTest() {
        System.out.println("Count test..." + repository.count());
    }

    @Test
    void deleteByIdTest() {
        var id = UUID.fromString("7832870e-3925-4405-ad4f-b296fb3566d9");
        repository.findById(id);

    }

    @Test
    void deleteObjTest() {
        var id = UUID.fromString("7832870e-3925-4405-ad4f-b296fb3566d9");
        var obj = repository.findById(id).get();
        repository.delete(obj);
    }
}