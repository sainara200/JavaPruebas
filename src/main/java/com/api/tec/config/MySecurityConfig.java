package com.api.tec.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
 
@EnableWebSecurity
@Configuration 
@RequiredArgsConstructor
public class MySecurityConfig   {

   
@Autowired
   JwtAuthenticationFilter jwtAuthenticationFilter;
@Autowired
    AuthenticationProvider authProvider; 
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {return http
            .csrf(csrf -> 
            csrf
            .disable())
        .authorizeHttpRequests(authRequest ->
          authRequest
          .requestMatchers(AntPathRequestMatcher.antMatcher( "/users/**"),
        		  AntPathRequestMatcher.antMatcher("/h2-ui/**" )).permitAll() 
            .anyRequest().authenticated()
            ).headers().frameOptions().sameOrigin() // Permitir mostrar en un iframe del mismo origen
        .and()
        .sessionManagement(sessionManager->
            sessionManager 
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

}