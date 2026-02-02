package github.caicosantos.library.api.controller.dto;

import java.time.LocalDate;

public record AuthorSearchDTO(
        String name,
        LocalDate birthDate,
        String nationality) {
}
