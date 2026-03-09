package github.caicosantos.library.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "API-LIBRARY",
        version = "v1.0",
        contact = @Contact(
                name = "Caíco Santos",
                email = "caico_fc@hotmail.com"
        )
    )
)
public class OpenAPIConfiguration {
}
