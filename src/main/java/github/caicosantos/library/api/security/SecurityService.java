package github.caicosantos.library.api.security;

import github.caicosantos.library.api.model.User;
import github.caicosantos.library.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    public Optional<User> getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        return userService.getByLogin(login);
    }
}
