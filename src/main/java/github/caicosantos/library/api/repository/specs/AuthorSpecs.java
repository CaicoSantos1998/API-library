package github.caicosantos.library.api.repository.specs;

import github.caicosantos.library.api.model.Author;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecs {

    public static Specification<Author> nameLike(String name) {
        return (root, cq, cb) ->
                cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }

    public static Specification<Author> nationalityEqual(String nationality) {
        return (root, cq, cb) ->
                cb.equal(cb.upper(root.get("nationality")), nationality.toUpperCase());
    }
}
