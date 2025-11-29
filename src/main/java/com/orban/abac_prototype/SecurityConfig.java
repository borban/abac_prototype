package com.orban.abac_prototype;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        // Resource server (JWT) with custom converter
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
            jwt.decoder(jwtDecoder());
            jwt.jwtAuthenticationConverter((Converter<Jwt, ? extends AbstractAuthenticationToken>) jwtAuthConverter());
        }));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers(HttpMethod.GET, "/documents/**").hasAnyRole("doc_reader","admin")
                .anyRequest().authenticated());

        return http.build();
    }

    // === Map `roles` claim -> Spring authorities ===
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthConverter() {
        JwtAuthenticationConverter conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(new RoleConverter()); // maps "roles" -> ROLE_*
        return conv; // JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken>
    }

    // === DEV decoder (load your RSA public key) ===
    @Bean
    JwtDecoder jwtDecoder() {
        // Use the robust loader you already added (not repeated here to keep this snippet focused)
        return NimbusJwtDecoder.withPublicKey(PemKeyLoader.loadPublicKey("keys/dev.pub")).build();
    }
}