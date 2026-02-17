package github.caicosantos.library.api.controller.dto;

public record ClientResultSearchDTO(
        String clientId,
        String redirectURI,
        String scope
                                    ) {
}
