package aca.demo.movierating.config;

import aca.demo.movierating.user.UserNotFoundException;
import aca.demo.movierating.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@Primary
public class MovieUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Finding user by username: {}", username);
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with this username not found."));

        return  User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .authorities("USER_AUTHORITIES")
                .build();
    }
}