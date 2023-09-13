package learn.gamer.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ConditionalOnWebApplication
public class SecurityConfig {
    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/refresh_token").authenticated()
                .antMatchers("/create_account").permitAll()
                // gamer
                .antMatchers(HttpMethod.GET,
                        "/gamer",
                        "/gamer/*",
                        "/gamer/game/*",
                        "/gamer/user/*").permitAll()
                .antMatchers(HttpMethod.POST,
                        "/gamer").permitAll()
                .antMatchers(HttpMethod.PUT,
                        "/gamer/*").permitAll()
                // gamer game
                .antMatchers(HttpMethod.POST,
                        "/gamer/game").permitAll()
                .antMatchers(HttpMethod.DELETE,
                        "/gamer/game/*/*").permitAll()
                // match sent
                .antMatchers(HttpMethod.POST,
                        "/gamer/match").permitAll()
                .antMatchers(HttpMethod.DELETE,
                        "/gamer/match/*/*").permitAll()
                // game
                .antMatchers(HttpMethod.GET,
                        "/game",
                        "/game/*", "/game/id/*").permitAll()
                .antMatchers(HttpMethod.POST,
                        "/game").permitAll()
                .antMatchers(HttpMethod.DELETE,
                        "/game/*").permitAll()
//                .antMatchers(HttpMethod.POST,
//                        "/game").hasAnyAuthority("USER", "ADMIN")
//                .antMatchers(HttpMethod.DELETE,
//                        "/game/*").hasAnyAuthority("USER","ADMIN")
                // post
                .antMatchers(HttpMethod.GET,
                        "/posting",
                        "/posting/*",
                        "/posting/player/*",
                        "/posting/game/title/*",
                        "/posting/game/id/*",
                        "/posting/date/*").permitAll()
                .antMatchers(HttpMethod.POST,
                        "/posting").permitAll()
                .antMatchers(HttpMethod.PUT,
                        "/posting/*").permitAll()
                .antMatchers(HttpMethod.DELETE,
                        "/posting/*").permitAll()
//                .antMatchers(HttpMethod.POST,
//                        "/posting").hasAnyAuthority("USER", "ADMIN")
//                .antMatchers(HttpMethod.PUT,
//                        "/posting/*").hasAnyAuthority("USER", "ADMIN")
//                .antMatchers(HttpMethod.DELETE,
//                        "/posting/*").hasAnyAuthority("ADMIN")

                .antMatchers("/**").denyAll()

                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(authConfig), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
