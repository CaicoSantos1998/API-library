package github.caicosantos.library.api.repository;

import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAuthor(Author author);
    Optional<Book> findByTitle(String title);
    Book findByIsbn(String isbn);
    List<Book> findByTitleAndPrice(String title, BigDecimal price);
    List<Book> findByTitleOrIsbn(String title, String isbn);
}
