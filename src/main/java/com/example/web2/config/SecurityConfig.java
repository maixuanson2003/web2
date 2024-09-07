package com.example.web2.config;

import com.nimbusds.jose.crypto.MACSigner;
import jakarta.servlet.Filter;
import lombok.experimental.NonFinal;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.JwkSetUriJwtDecoderBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @NonFinal
    private static final String signerKey="NQc7mrnHIwVaDA519Ka3ph/ZdHVjvu5NhWkNMfExmAIHpDtO3PShgPqK4w3Rivq7";
    private final String[] URL={"/api/actors/post","/auth/login","/auth/checkToken"};
    private final String[] swaggerURL={"/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/webjars/**"};
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(swaggerURL).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/checkToken").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/actors").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/books").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/cart/add").hasAuthority("SCOPE_USER")
                        .requestMatchers(HttpMethod.POST, "/api/orders/add").hasAuthority("SCOPE_USER")
                        .requestMatchers(HttpMethod.POST, "/api/cart/create", "/api/librarycard/register","/api/conversations/create").hasAuthority("SCOPE_USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/actors/{id}", "/api/books/{id}").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/actors", "/api/books", "/api/books/{id}", "/api/orders/all", "/api/books/sortByDate", "/api/books/search", "/api/cart/view","/api/conversations/user").hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN")
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
                );
        return http.build();
    }


    @Bean
    public JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec=new SecretKeySpec(signerKey.getBytes(),"HmacSHA512");
        return  NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }


}
