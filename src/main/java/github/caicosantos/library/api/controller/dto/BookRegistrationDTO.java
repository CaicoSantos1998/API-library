package github.caicosantos.library.api.controller.dto;

import github.caicosantos.library.api.model.enums.GenderBook;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookRegistrationDTO(
        @ISBN
        @NotBlank(message = "This field is required!")
        String isbn,
        @NotBlank(message = "This field is required!")
        String title,
        @NotNull(message = "This field is required!")
        @Past(message = "This date cannot be future date!")
        LocalDate datePublication,
        GenderBook gender,
        BigDecimal price,
        @NotNull(message = "This field is required!")
        UUID idAuthor) {

}
