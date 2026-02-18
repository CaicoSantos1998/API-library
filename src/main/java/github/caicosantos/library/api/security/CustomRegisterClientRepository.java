package github.caicosantos.library.api.security;

import github.caicosantos.library.api.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomRegisterClientRepository implements RegisteredClientRepository {
    private final ClientService service;
    private final TokenSettings token;
    private final ClientSettings settings;

    @Override
    public void save(RegisteredClient registeredClient) {}

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var client = service.getByClientId(clientId);
        if(client==null) {
            return null;
        }
        return RegisteredClient
                .withId(client.get().getId().toString())
                .clientId(client.get().getClientId())
                .clientSecret(client.get().getClientSecret())
                .redirectUri(client.get().getRedirectURI())
                .scope(client.get().getScope())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(token)
                .clientSettings(settings)
                .build();

    }
}
