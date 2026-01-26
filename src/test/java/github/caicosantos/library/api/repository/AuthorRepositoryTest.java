package github.caicosantos.library.api.repository;

import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.model.enums.GenderBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    void saveTest() {
        Author author = new Author();
        author.setName("Jose");
        author.setBirthDate(LocalDate.of(1995, 10, 2));
        author.setNationality("Brazilian");

        authorRepository.save(author);
    }

    @Test
    void updateTest() {
        Optional<Author> optional = authorRepository.findById(UUID.fromString("7832870e-3925-4405-ad4f-b296fb3566d9"));
        if(optional.isPresent()) {
            Author authorExist = optional.get();
            authorExist.setBirthDate(LocalDate.of(1997, 10, 30));

            authorRepository.save(authorExist);
        }
    }

    @Test
    void findAllTest() {
        List<Author> list = authorRepository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    void countTest() {
        System.out.println("Count test..." + authorRepository.count());
    }

    @Test
    void deleteByIdTest() {
        var id = UUID.fromString("7832870e-3925-4405-ad4f-b296fb3566d9");
        authorRepository.findById(id);

    }

    @Test
    void deleteObjTest() {
        var id = UUID.fromString("7832870e-3925-4405-ad4f-b296fb3566d9");
        var obj = authorRepository.findById(id).get();
        authorRepository.delete(obj);
    }

    @Test
    void saveAuthorWithBookTest() {
        Author author = new Author();
        author.setName("Carl");
        author.setBirthDate(LocalDate.of(1975, 10, 25));
        author.setNationality("American");

        Book book = new Book();
        book.setIsbn("532-123");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.MYSTERY);
        book.setTitle("The robbery of the haunted house");
        book.setDatePublication(LocalDate.of(1999, 1, 2));
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setDatePublication(LocalDate.of(2002, 1, 20));
        book2.setGender(GenderBook.MYSTERY);
        book2.setIsbn("523-123");
        book2.setTitle("The robbery of the haunted house 2");
        book2.setPrice(BigDecimal.valueOf(150));
        book2.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        authorRepository.save(author);
        bookRepository.saveAll(author.getBooks());
    }
}