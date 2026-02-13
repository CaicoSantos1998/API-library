package github.caicosantos.library.api.config;

import github.caicosantos.library.api.security.CustomUserDetailsService;
import github.caicosantos.library.api.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity hs) throws Exception {
        return hs
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.POST, "/tb_users/**").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/books").hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers(HttpMethod.GET, "/authors").hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers("/books/**").hasRole("ADMIN");
                    authorize.requestMatchers("/authors/**").hasRole("ADMIN");
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public UserDetailsService detailsService(UserService userService) {
        return new CustomUserDetailsService(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
