package github.caicosantos.library.api.controller.dto;

public record ClientRegistrationDTO(
        String clientId,
        String clientSecret,
        String redirectURI,
        String scope
        ) {
}
