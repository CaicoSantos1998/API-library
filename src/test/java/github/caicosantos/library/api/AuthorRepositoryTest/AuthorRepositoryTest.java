package github.caicosantos.library.api.AuthorRepositoryTest;

import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("TEST")
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;

    private Author authorForTests;

    @BeforeEach
    void dataForTests() {
        Author author = new Author();
        author.setName("AUTHOR TEST");
        author.setBirthDate(LocalDate.of(2026, 3, 6));
        author.setNationality("Brazilian");
        this.authorForTests = authorRepository.save(author);
    }

    @Test
    void saveAuthorTest() {
        Author newAuthor = new Author();
        newAuthor.setName("AUTHOR SAVED");
        newAuthor.setBirthDate(LocalDate.of(2026,3, 5));
        newAuthor.setNationality("Brazilian");
        Author save = authorRepository.save(newAuthor);

        assertNotNull(save.getId(), "The ID cannot be null");
        assertEquals("AUTHOR SAVED", save.getName(), "Incorrect name!");
        assertNotNull(save.getRegistrationDate(), "The registration date should be generated");
    }

    @Test
    void findByIdTest() {
        Optional<Author> authorFound = authorRepository.findById(authorForTests.getId());
        assertTrue(authorFound.isPresent(), "The author should be founded!");
        assertEquals("AUTHOR TEST", authorFound.get().getName());
        assertEquals("Brazilian", authorFound.get().getNationality());
    }

    @Test
    void deleteByIdTest() {
        authorRepository.deleteById(authorForTests.getId());
        boolean exists = authorRepository.existsById(authorForTests.getId());
        assertFalse(exists, "The author should have been removed from the database!");
    }

    @Test
    void updateAuthorTest() {
        Author authorFound = authorRepository.findById(authorForTests.getId()).orElseThrow();
        authorFound.setName("AUTHOR TEST UPDATED");
        authorRepository.save(authorFound);
    }
}
