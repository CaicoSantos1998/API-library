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
import java.util.Optional;
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
        book.setDatePublication(LocalDate.of(2010, 5, 10));
        book.setGender(GenderBook.FICTION);
        book.setIsbn("414-552");
        book.setTitle("UFO 3");
        book.setPrice(BigDecimal.valueOf(100));

        Author author = authorRepository
                .findById(UUID.fromString("7832870e-3925-4405-ad4f-b296fb3566d9"))
                .orElse(null);
        book.setAuthor(author);

        bookRepository.save(book);
    }

    @Test
    void saveCascadeTest() {
        Book book = new Book();
        book.setDatePublication(LocalDate.of(2005, 12, 10));
        book.setGender(GenderBook.FICTION);
        book.setIsbn("413-112");
        book.setTitle("UFO 2");
        book.setPrice(BigDecimal.valueOf(120));

        Author author = new Author();
        author.setName("Maria");
        author.setBirthDate(LocalDate.of(1998, 10, 2));
        author.setNationality("Brazilian");

        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    void saveAuthorAndBookTest() {
        Book book = new Book();
        book.setDatePublication(LocalDate.of(2008, 12, 25));
        book.setGender(GenderBook.FICTION);
        book.setIsbn("413-222");
        book.setTitle("UFO 3");
        book.setPrice(BigDecimal.valueOf(120));

        Author author = new Author();
        author.setName("Maria");
        author.setBirthDate(LocalDate.of(1998, 10, 2));
        author.setNationality("Brazilian");

        authorRepository.save(author);
        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    void updateAuthorOfBookTest() {
        var bookToUpdate = bookRepository
                .findById(UUID.fromString("31a8dd4a-7570-469a-8a8c-c404c3c84b56"))
                .orElse(null);
        var updateAuthor = authorRepository
                .findById(UUID.fromString("93f2da5a-d603-4cf3-88ad-eb78b9453cb7"))
                .orElse(null);

        if(bookToUpdate != null) {
            bookToUpdate.setAuthor(updateAuthor);
            bookRepository.save(bookToUpdate);
        }
        throw new NullPointerException("The Book ID is invalid");
    }

    @Test
    void deleteByIdTest() {
        bookRepository.deleteById(UUID.fromString("31a8dd4a-7570-469a-8a8c-c404c3c84b56"));
    }

    @Test
    void deleteObjTest() {
        bookRepository.findById(UUID.fromString("087ff46c-76ee-4d06-8c80-a6e74767d93f"))
                .ifPresent(bookFound -> bookRepository.delete(bookFound));
    }

    @Test
    void findAllTest() {
        List<Book> list = bookRepository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    void listBooksAuthorTest() {
        var author = authorRepository.findById(UUID.fromString("b110dc09-145a-4ccf-8908-3f39cbeaf4a7")).get();
        List<Book> bookList = bookRepository.findByAuthor(author);
        author.setBooks(bookList);
        author.getBooks().forEach(System.out::println);
    }

    @Test
    void queryForTitleTest() {
        Optional<Book> list = bookRepository.findByTitle("The robbery of the haunted house");
        if(list.isEmpty()) {
            System.out.println("List empty");
        }
        System.out.println(list);
    }

    @Test
    void queryForIsbnTest() {
        Book book = bookRepository.findByIsbn("523-123");
        System.out.println(book);
    }

    @Test
    void queryForTitleAndPriceTest() {
        List<Book> list = bookRepository.findByTitleAndPrice("The robbery of the haunted house 2", BigDecimal.valueOf(150.00));
        list.forEach(System.out::println);
    }

}