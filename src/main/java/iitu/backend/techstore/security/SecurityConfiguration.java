package iitu.backend.techstore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;

    /*
    asulanma@gmail.com
    Password123
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeHttpRequests().requestMatchers("/", "/basket", "/items/*/basket", "/items/*/basket-remove").hasAnyRole("USER", "ADMIN", "MODERATOR");
        http.authorizeHttpRequests().requestMatchers("/404").permitAll();

        http
                .authorizeHttpRequests()
                .requestMatchers("/auth/**", "/items")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(regexMatcher("/items/[0-9]+"))
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/items/new")
                .hasAnyRole("ADMIN", "MODERATOR")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/items/*/edit", "/items/*/delete")
                .hasAnyRole("ADMIN", "MODERATOR")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/users", "/users/*", "/users/*/edit", "/users/*/delete")
                .hasAnyRole("ADMIN", "MODERATOR")
        ;

        http.authenticationProvider(authenticationProvider);

        http.formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/items", true)
                .failureUrl("/auth/login?error");

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");

        return http.build();
    }
}
