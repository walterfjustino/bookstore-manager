package com.api.bookstoremanager.config;
import com.api.bookstoremanager.users.enums.Role;
import com.api.bookstoremanager.users.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//
//import com.api.bookstoremanager.users.enums.Role;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@AllArgsConstructor(onConstructor = @__(@Autowired))
//public class WebSecurityConfig  { //extends WebSecurityConfigurerAdapter
//
//  private static final String USERS_API_URL = "/api/v1/users/**";
//  private static final String PUBLISHERS_API_URL = "/api/v1/publishers/**";
//  private static final String AUTHORS_API_URL = "/api/v1/authors/**";
//  private static final String BOOKS_API_URL = "/api/v1/books/**";
//  private static final String H2_CONSOLE_URL = "/h2-console/**";
//  private static final String SWAGGER_URL = "/swagger-ui.html";
//  private static final String ROLE_ADMIN = Role.ADMIN.getDescription();
//  private static final String ROLE_USER = Role.USER.getDescription();
//
//  private static final String[] SWAGGER_RESOURCES = {
//          // -- swagger ui
//          "/v2/api-docs",
//          "/swagger-resources",
//          "/swagger-resources/**",
//          "/configuration/ui",
//          "/configuration/security",
//          "/swagger-ui.html",
//          "/webjars/**"
//  };
//
//
//  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//  private UserDetailsService userDetailsService;
//  private PasswordEncoder passwordEncoder;
//
//
//  @Bean
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//  }
//
//  @Bean
////  @Override
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return authenticationManagerBean();
//  }
//
////  @Override
////  protected void configure(HttpSecurity httpSecurity) throws Exception {
////    httpSecurity.csrf().disable()
////            .authorizeRequests().antMatchers(USERS_API_URL,H2_CONSOLE_URL,SWAGGER_URL).permitAll()
////            .antMatchers(PUBLISHERS_API_URL,AUTHORS_API_URL).hasAnyRole(ROLE_ADMIN)
////            .antMatchers(BOOKS_API_URL).hasAnyRole(ROLE_ADMIN, ROLE_USER)
////            .anyRequest().authenticated()
////            .and()
////            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
////            .and().sessionManagement()
////            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////    httpSecurity.headers().frameOptions().disable();
////  }
//
//  protected void configure(HttpSecurity httpSecurity) throws Exception {
//    httpSecurity.csrf().disable()
//            .authorizeRequests().requestMatchers(USERS_API_URL,H2_CONSOLE_URL,SWAGGER_URL).permitAll()
//            .requestMatchers(PUBLISHERS_API_URL,AUTHORS_API_URL).hasAnyRole(ROLE_ADMIN)
//            .requestMatchers(BOOKS_API_URL).hasAnyRole(ROLE_ADMIN, ROLE_USER)
//            .anyRequest().authenticated()
//            .and()
//            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//            .and().sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    httpSecurity.headers().frameOptions().disable();
//  }
//
////  @Override
//  public void configure(WebSecurity web) throws Exception {
//    web.ignoring().requestMatchers(SWAGGER_RESOURCES);
//  }
//}
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

  private static final String[] SWAGGER_RESOURCES = {
          // -- swagger ui
          "/v2/api-docs",
          "/swagger-resources",
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui.html",
          "/webjars/**"
  };

  @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @Autowired private JwtRequestFilter jwtRequestFilter;
  @Autowired private PasswordEncodingConfig passwordEncodingConfig;
  @Autowired private UserDetailsService userDetailsService;


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  protected  SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests().requestMatchers(USERS_API_URL,H2_CONSOLE_URL,SWAGGER_URL).permitAll()
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

//  @Bean
//  public PasswordEncoder passwordEncoder(){
//    return new BCryptPasswordEncoder();
//  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer () throws Exception {
    return web -> web.ignoring().requestMatchers(SWAGGER_RESOURCES);
  }

}