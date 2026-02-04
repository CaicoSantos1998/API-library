package github.caicosantos.library.api.repository;

import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.model.enums.GenderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {
    List<Book> findByAuthor(Author author);
    Optional<Book> findByTitle(String title);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitleAndPrice(String title, BigDecimal price);
    List<Book> findByTitleOrIsbn(String title, String isbn);
    @Query("""
        SELECT bk 
        FROM Book AS bk 
        ORDER BY bk.title, bk.price
    """)
    List<Book> listAllOrderByTitleAndPrice();

    @Query("""
        SELECT at
        FROM Book bk
        JOIN bk.author at
       """)
    List<Author> listAuthorBooks();
    @Query("""
        SELECT DISTINCT bk.title
        FROM Book bk
    """)
    List<String> listNamesDifferentBooks();
    @Query("""
        SELECT DISTINCT bk.gender
        FROM Book AS bk
        JOIN bk.author AS at
        WHERE at.nationality = 'Brazilian'
        ORDER BY bk.gender
    """)
    List<String> listBooksByGenderAuthor();
    @Query("""
        SELECT bk
        FROM Book AS bk
        WHERE bk.gender = ?1
        ORDER BY ?2
    """)
    List<Book> findByGender(GenderBook gender, String orderBy);
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Book
        WHERE gender = ?1
    """)
    void deleteByGender(GenderBook gender);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Book
        SET datePublication = ?1
        WHERE id = ?2
    """)
    void updateDatePublication(LocalDate newDate, UUID id);

    boolean existsByAuthor(Author author);
}
