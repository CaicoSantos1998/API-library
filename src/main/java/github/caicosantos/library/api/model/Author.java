package github.caicosantos.library.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
@Data
@ToString(exclude = "books")
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;
    @LastModifiedDate
    @Column(name = "date_update", nullable = false)
    private LocalDateTime dateUpdate;
    @Column(name = "id_user")
    private UUID idUser;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;

}