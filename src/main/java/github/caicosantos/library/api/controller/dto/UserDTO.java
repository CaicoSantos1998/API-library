package github.caicosantos.library.api.controller.dto;

import java.util.List;

public record UserDTO(
        String login,
        String password,
        List<String> roles
) {
}
