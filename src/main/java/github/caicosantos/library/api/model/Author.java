package github.caicosantos.library.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "nationality", length = 50, nullable = false)
    private String nationality;

    /*@OneToMany(mappedBy = "author")*/
    @Transient
    private List<Book> books;

}