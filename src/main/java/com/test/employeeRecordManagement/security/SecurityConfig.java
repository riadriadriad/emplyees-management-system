package com.test.employeeRecordManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    public static final String [] PUBLIC_PATHS=new String[]{"swagger-ui/**", "swagger-ui**", "/v3/api-docs/**", "/v3/api-docs**","/login",};
    public static final String [] PUBLIC_ROOT_PATHS=new String[]{"/swagger-ui","/configuration","/swagger-resources","/v2","/webjars","/v3"};


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth.requestMatchers(PUBLIC_PATHS).permitAll().anyRequest().authenticated())
                .oauth2ResourceServer(oAuth2->oAuth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();

    }



}
