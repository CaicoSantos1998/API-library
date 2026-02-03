package github.caicosantos.library.api.repository.specs;

import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.model.enums.GenderBook;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn) {
        return (root, cq, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, cq, cb) -> cb.like(cb.upper(root.get("title")),"%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> nameAuthorLike(String name) {
        return (root, cq, cb) -> {
            Join<Object, Object> joinAuthor = root.join("author", JoinType.INNER);
            return cb.like(cb.upper(joinAuthor.get("name")), "%" + name.toUpperCase() + "%");
        };
    }

    public static Specification<Book> genderEqual(GenderBook gender) {
        return (root, cq, cb) -> cb.equal(root.get("gender"), gender);
    }

    public static Specification<Book> yearPublicationEqual(Integer year) {
        return (root, cq, cb) -> cb.equal(cb.function("to_char", String.class, root.get("datePublication"), cb.literal("YYYY")), year.toString());
    }
}
