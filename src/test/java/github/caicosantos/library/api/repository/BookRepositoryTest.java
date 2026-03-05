package github.caicosantos.library.api.repository;

import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.model.enums.GenderBook;
import org.junit.jupiter.api.BeforeEach;
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

    private Author authorForTests;
    private Book bookForTests;

    @BeforeEach
    void dataForTest(){
        Author author = new Author();
        author.setName("Author of Tests");
        author.setBirthDate(LocalDate.of(1990, 1, 1));
        author.setNationality("Brazilian");
        this.authorForTests = authorRepository.save(author);

        Book book = new Book();
        book.setIsbn("978-3-16-148410-0");
        book.setTitle("BOOK TEST");
        book.setGender(GenderBook.SCIENCE);
        book.setDatePublication(LocalDate.of(2017, 5, 10));
        book.setPrice(BigDecimal.valueOf(100));
        book.setAuthor(this.authorForTests);
        this.bookForTests = bookRepository.save(book);
    }


    @Test
    void saveTest() {
        Book book = new Book();
        book.setDatePublication(LocalDate.of(2017, 5, 10));
        book.setGender(GenderBook.SCIENCE);
        book.setIsbn("414-552");
        book.setTitle("The Cigarette's good? Vol.1");
        book.setPrice(BigDecimal.valueOf(100));

        Author author = authorRepository
                .findById(authorForTests.getId())
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
        Author author = new Author();
        author.setName("Author for Test");
        author.setBirthDate(LocalDate.of(1998, 10, 2));
        author.setNationality("Brazilian");
        Author authorSaved = authorRepository.save(author);

        Book book = new Book();
        book.setIsbn("413-222");
        book.setTitle("UFO 3");
        book.setDatePublication(LocalDate.of(2008, 12, 25));
        book.setGender(GenderBook.FICTION);
        book.setPrice(BigDecimal.valueOf(120));
        book.setAuthor(authorSaved);
        bookRepository.save(book);
    }

    @Test
    void updateAuthorOfBookTest() {
        var bookToUpdate = bookRepository
                .findById(bookForTests.getId())
                .orElse(null);
        var updateAuthor = authorRepository
                .findById(authorForTests.getId())
                .orElse(null);

        Book book = bookRepository.findById(bookForTests.getId())
                .orElseThrow(() -> new RuntimeException("The Book not found!"));
    }

    @Test
    void deleteByIdTest() {
        bookRepository.deleteById(bookForTests.getId());
    }

    @Test
    void deleteObjTest() {
        bookRepository.delete(bookForTests);
    }

    @Test
    void findAllTest() {
        List<Book> list = bookRepository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    void listBooksAuthorTest() {
        var author = authorRepository.findById(authorForTests.getId()).get();
        List<Book> bookList = bookRepository.findByAuthor(author);
        author.setBooks(bookList);
        author.getBooks().forEach(System.out::println);
    }

    @Test
    void queryForTitleTest() {
        Optional<Book> list = bookRepository.findByTitle("BOOK TEST");
        if(list.isEmpty()) {
            System.out.println("List empty");
        }
        System.out.println(list);
    }

    @Test
    void queryForIsbnTest() {
        Optional<Book> book = bookRepository.findByIsbn("523-123");
        book.ifPresent(System.out::println);
    }

    @Test
    void queryForTitleAndPriceTest() {
        List<Book> list = bookRepository.findByTitleAndPrice("The robbery of the haunted house 2", BigDecimal.valueOf(150.00));
        list.forEach(System.out::println);
    }

    @Test
    void queryForTitleOrIsbnTest() {
        List<Book> list = bookRepository.findByTitleOrIsbn("The robbery of the haunted house 2", "523-123");
        list.forEach(System.out::println);
    }

    @Test
    void listBookQueryJPQL() {
        var result = bookRepository.listAllOrderByTitleAndPrice();
        result.forEach(System.out::println);
    }

    @Test
    void listAuthorBooksTest() {
        var result = bookRepository.listAuthorBooks();
        result.forEach(System.out::println);
    }

    @Test
    void listNamesDifferentBookTest() {
        var result = bookRepository.listNamesDifferentBooks();
        result.forEach(System.out::println);
    }

    @Test
    void listBookByGenderAuthorTest() {
        var result = bookRepository.listBooksByGenderAuthor();
        result.forEach(System.out::println);
    }

    @Test
    void listBookByGenderTest() {
        var result = bookRepository.findByGender(GenderBook.MYSTERY, "datePublication");
        result.forEach(System.out::println);
    }

    @Test
    void deleteByGenderTest() {
        bookRepository.deleteByGender(GenderBook.FICTION);
    }

    @Test
    void updateDatePublicationBookTest() {
        bookRepository.updateDatePublication(LocalDate.of(2002, 10, 11),
                UUID.fromString(bookForTests.getId().toString()));
    }

}