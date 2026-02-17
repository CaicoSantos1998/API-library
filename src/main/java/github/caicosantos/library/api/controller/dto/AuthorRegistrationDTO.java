package github.caicosantos.library.api.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorRegistrationDTO(
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

}
