package github.caicosantos.library.api.repository;

import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.model.enums.GenderBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void saveTest() {
        Book book = new Book();
        book.setDatePublication(LocalDate.of(2001, 8, 10));
        book.setGender(GenderBook.FICTION);
        book.setIsbn("414-232");
        book.setTitle("UFO");
        book.setPrice(BigDecimal.valueOf(100));

        Author author = authorRepository
                .findById(UUID.fromString("7832870e-3925-4405-ad4f-b296fb3566d9"))
                .orElse(null);
        book.setAuthor(author);

        bookRepository.save(book);
    }

    @Test
    void findAllTest() {
        List<Book> list = bookRepository.findAll();
        list.forEach(System.out::println);
    }

}