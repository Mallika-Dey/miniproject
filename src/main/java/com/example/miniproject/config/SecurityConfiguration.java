package com.example.miniproject.config;

import com.example.miniproject.auth.JwtAuthenticationFilter;
import com.example.miniproject.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, AppConstant.SIGN_IN, AppConstant.SIGN_UP).permitAll()
                        //.requestMatchers(HttpMethod.GET, AppConstant.USER_DETAILS).hasRole(AppConstant.ADMIN)
                        .requestMatchers(HttpMethod.GET, AppConstant.USER_DETAILS).hasAnyRole(AppConstant.ADMIN, AppConstant.USER)
                        .requestMatchers(HttpMethod.GET, AppConstant.ALL_USER_DETAILS).hasRole(AppConstant.ADMIN)
                        .requestMatchers(HttpMethod.GET, AppConstant.USER_OWNED_BOOK).hasAnyRole(AppConstant.ADMIN, AppConstant.USER)
                        .requestMatchers(HttpMethod.GET, AppConstant.USER_BORROWED_BOOK).hasAnyRole(AppConstant.ADMIN, AppConstant.USER)

                        .requestMatchers(HttpMethod.POST, AppConstant.CREATE_BOOK).hasRole(AppConstant.ADMIN)
                        .requestMatchers(HttpMethod.PUT, AppConstant.UPDATE_BOOK).hasRole(AppConstant.ADMIN)
                        .requestMatchers(HttpMethod.DELETE, AppConstant.DELETE_BOOK).hasRole(AppConstant.ADMIN)
                        .requestMatchers(HttpMethod.GET, AppConstant.GET_BOOK).hasAnyRole(AppConstant.ADMIN, AppConstant.USER)

                        .requestMatchers(HttpMethod.POST, AppConstant.BORROW_BOOK).hasRole(AppConstant.USER)
                        .requestMatchers(HttpMethod.DELETE, AppConstant.RETURN_BOOK).hasRole(AppConstant.USER)

                        .requestMatchers(HttpMethod.GET, AppConstant.RESERVE_BOOK).hasRole(AppConstant.USER)
                        .requestMatchers(HttpMethod.DELETE, AppConstant.CANCEL_RESERVATION).hasRole(AppConstant.USER)

                        .requestMatchers(HttpMethod.POST, AppConstant.GIVE_REVIEW).hasRole(AppConstant.USER)
                        .requestMatchers(HttpMethod.PUT, AppConstant.UPDATE_REVIEW).hasRole(AppConstant.USER)
                        .requestMatchers(HttpMethod.DELETE, AppConstant.DELETE_REVIEW).hasRole(AppConstant.USER)
                        .requestMatchers(HttpMethod.GET, AppConstant.GET_REVIEW).hasAnyRole(AppConstant.ADMIN, AppConstant.USER)

                        .requestMatchers(HttpMethod.GET, AppConstant.GET_USER_HISTORY).hasAnyRole(AppConstant.ADMIN)
                        .requestMatchers(HttpMethod.GET, AppConstant.GET_PERSONAL_HISTORY).hasAnyRole(AppConstant.USER)
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        //config.addAllowedHeader("Authorization");

        config.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "X-Requested-With",
                "Cache-Control"
        ));

        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
