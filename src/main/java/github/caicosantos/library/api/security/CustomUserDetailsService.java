package github.caicosantos.library.api.security;

import github.caicosantos.library.api.model.User;
import github.caicosantos.library.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService service;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = service.getByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[user.getRoles().size()]))
                .build();
    }
}
