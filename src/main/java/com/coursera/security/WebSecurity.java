package com.coursera.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurity {


    private final CustomUserDetailsService userDetailService;

    public WebSecurity(CustomUserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // telling security to don't check the below urls
        http.authorizeRequests().mvcMatchers("/login", "/authenticate", "/actuator", "*/h2-console/**", "/h2-console", "/actuator/**").permitAll();

        // telling to check all urls
        http.authorizeRequests().anyRequest().authenticated();

        // setting login  page and authentication
        http.userDetailsService(userDetailService).formLogin().loginPage("/login").loginProcessingUrl("/authenticate")
                .failureForwardUrl("/login?error")
                .defaultSuccessUrl("/home", true);

        // setting maximum user sessions and registry
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry()).expiredUrl("/login");

        // setting the session policy
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).sessionFixation()
                .migrateSession();

        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("*/h2-console/**"));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
