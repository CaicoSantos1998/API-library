package github.caicosantos.library.api.controller.dto;

import github.caicosantos.library.api.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "This field is required!")
        String name,
        @NotNull(message = "This field is required!")
        LocalDate birthDate,
        @NotBlank(message = "This field is required!")
        String nationality) {

    public Author mappingToAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setBirthDate(this.birthDate);
        author.setNationality(this.nationality);
        return author;
    }
}
