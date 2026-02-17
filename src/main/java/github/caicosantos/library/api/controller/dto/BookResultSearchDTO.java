package github.caicosantos.library.api.controller.dto;

import github.caicosantos.library.api.model.enums.GenderBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookResultSearchDTO(
    UUID id,
    String isbn,
    String title,
    LocalDate datePublication,
    GenderBook gender,
    BigDecimal price,
    AuthorResultSearchDTO author) {

}
