package github.caicosantos.library.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import github.caicosantos.library.api.model.Author;

import java.time.LocalDate;

public record AuthorDTO(
        String name,
        LocalDate birthDate,
        String nationality) {

    public Author mappingToAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setBirthDate(this.birthDate);
        author.setNationality(this.nationality);
        return author;
    }
}
