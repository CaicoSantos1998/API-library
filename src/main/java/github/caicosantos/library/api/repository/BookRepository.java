package github.caicosantos.library.api.repository;

import github.caicosantos.library.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
