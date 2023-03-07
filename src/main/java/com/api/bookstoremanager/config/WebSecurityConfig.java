package com.api.bookstoremanager.config;

import com.api.bookstoremanager.users.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig {

  private static final String USERS_API_URL = "/api/v1/users/**";
  private static final String PUBLISHERS_API_URL = "/api/v1/publishers/**";
  private static final String AUTHORS_API_URL = "/api/v1/authors/**";
  private static final String BOOKS_API_URL = "/api/v1/books/**";
  private static final String H2_CONSOLE_URL = "/h2-console/**";
  private static final String SWAGGER_URL = "/swagger-ui.html";
  private static final String ROLE_ADMIN = Role.ADMIN.getDescription();
  private static final String ROLE_USER = Role.USER.getDescription();

  private static final String[] SPRINGDOC_RESOURCES = {
          // -- swagger ui
          "/v2/api-docs",
          "/swagger-resources",
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui.html",
          "/webjars/**",
          "/v3/api-docs/**",
          "/swagger-ui/**"
  };

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @Autowired
  private JwtRequestFilter jwtRequestFilter;
  @Autowired
  private PasswordEncodingConfig passwordEncodingConfig;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  protected  SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests().requestMatchers(USERS_API_URL,H2_CONSOLE_URL, SWAGGER_URL).permitAll()
            .requestMatchers(PUBLISHERS_API_URL,AUTHORS_API_URL).hasAnyRole(ROLE_ADMIN)
            .requestMatchers(BOOKS_API_URL).hasAnyRole(ROLE_ADMIN, ROLE_USER)
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .headers().frameOptions().disable()
            .and()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer () throws Exception {
    return web -> web.ignoring().requestMatchers(SPRINGDOC_RESOURCES);
  }
}