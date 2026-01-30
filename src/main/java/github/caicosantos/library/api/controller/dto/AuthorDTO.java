package github.caicosantos.library.api.controller.dto;

import github.caicosantos.library.api.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "This field is required!")
        @Size(min = 2, max = 100, message = "The field is outside the allowed size standard!")
        String name,
        @NotNull(message = "This field is required!")
        @Past(message = "Your birthdate cannot be a future date!")
        LocalDate birthDate,
        @NotBlank(message = "This field is required!")
        @Size(min = 4, max = 50, message = "The field is outside the allowed size standard!")
        String nationality) {

    public Author mappingToAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setBirthDate(this.birthDate);
        author.setNationality(this.nationality);
        return author;
    }
}
