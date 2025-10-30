package com.chrishield.sec_sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(CustomAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // The PasswordEncoder is responsible for hashing, encoding,
                                       // and verifying passwords securely.
    }

    @Bean // Setting Up In-Memory Users
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("user")
                .password(passwordEncoder.encode("user123")) // or pre-encoded
                .roles("USER")
                .build());

        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("admin123")) // or pre-encoded
                .roles("ADMIN")
                .build());

        return manager;
    }

    @Bean // All incoming HTTP requests pass through a chain of filters before reaching our application logic.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // It defines the security rules for our application by configuring
        // a SecurityFilterChain using the HttpSecurity object.
        // We are telling Spring Security:
        //  - What authentication and authorization rules to apply
        //  - Which endpoints are public or restricted
        //  - What type of login/authentication method to use
        //  - What protections like CSRF or HTTPS you want to enable or disable
        //
        // At the end, you call http.build() to turn your configuration into an
        // actual SecurityFilterChain bean that Spring Boot will use at runtime.
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        // Allow anyone to access this endpoint
                        .requestMatchers("/api/one/hello").permitAll()

                        // Only Users with USER role can access this endpoint
                        .requestMatchers("/api/one/bighello").hasRole("USER")

                        // Only Users with USER role can access this endpoint
                        .requestMatchers("/api/two/hello").hasRole("USER")

                        // Only Users with ADMIN role can access this endpoint
                        .requestMatchers("/api/two/bighello").hasRole("ADMIN")

                        // ADMIN and USER roles can access these endpoints
                        .requestMatchers("/three/**").hasAnyRole("USER", "ADMIN")

                        // Any other endpoint can be accessed by any authenticated user
                        .anyRequest().authenticated())

                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler))
                .httpBasic(Customizer.withDefaults());
        // This last line enables HTTP Basic authentication with default settings.
        // (i.e., Use the standard setup for HTTP Basic without needing me to configure extra settings.)

        return http.build();
    }
}
