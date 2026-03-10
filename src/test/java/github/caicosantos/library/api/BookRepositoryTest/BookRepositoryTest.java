package github.caicosantos.library.api.BookRepositoryTest;

import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.model.enums.GenderBook;
import github.caicosantos.library.api.repository.AuthorRepository;
import github.caicosantos.library.api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("TEST")
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    private Book bookForTests;
    private Author authorForTests;

    @BeforeEach
    void dataForTests() {
        Author author = new Author();
        author.setName("AUTHOR TEST");
        author.setBirthDate(LocalDate.of(2026, 3, 6));
        author.setNationality("Brazilian");
        this.authorForTests = authorRepository.save(author);
        Book book = new Book();
        book.setTitle("BOOK TEST");
        book.setIsbn("123-456-789");
        book.setPrice(BigDecimal.valueOf(89.90));
        book.setGender(GenderBook.FICTION);
        book.setDatePublication(LocalDate.of(2026,3,6));
        book.setAuthor(this.authorForTests);
        this.bookForTests = bookRepository.save(book);
    }

    @Test
    void saveBookTest() {
        Book book = new Book();
        book.setTitle("BOOK SAVED");
        book.setIsbn("999-888");
        book.setPrice(BigDecimal.valueOf(50.00));
        book.setGender(GenderBook.FICTION);
        book.setDatePublication(LocalDate.of(2026,3,6));
        book.setAuthor(this.authorForTests);
        Book save = bookRepository.save(book);
        assertNotNull(save.getId(), "The ID cannot be null!");
        assertEquals(this.authorForTests.getId(), save.getAuthor().getId(), "This author's ID is different!");
    }

    @Test
    void findByTest() {
        Optional<Book> bookFound = bookRepository.findById(bookForTests.getId());
        assertTrue(bookFound.isPresent(), "The book should be founded!");
        assertEquals("BOOK TEST", bookFound.get().getTitle());
    }
}
