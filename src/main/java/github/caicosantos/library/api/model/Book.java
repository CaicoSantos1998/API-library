package github.caicosantos.library.api.model;

import github.caicosantos.library.api.model.enums.GenderBook;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "book")
@Data
@ToString(exclude = "author")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;
    @Column(name = "title", length = 150, nullable = false)
    private String title;
    @Column(name = "date_publication", nullable = false)
    private LocalDate datePublication;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 30, nullable = false)
    private GenderBook gender;
    @Column(name = "price", precision = 18, scale = 2, nullable = false)
    private BigDecimal price;
    @CreatedDate
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;
    @LastModifiedDate
    @Column(name = "date_update", nullable = false)
    private LocalDateTime dateUpdate;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private Author author;

}
