package com.coursera.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
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
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurity {

    private final CustomAuthenticationProvider authenticationProvider;

    private final SessionRegistry sessionRegistry;

    public WebSecurity(CustomAuthenticationProvider authenticationProvider, SessionRegistry sessionRegistry) {
        this.authenticationProvider = authenticationProvider;
        this.sessionRegistry = sessionRegistry;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // telling security to don't check the below urls
        http.authorizeRequests().mvcMatchers("/login", "/authenticate", "/actuator", "*/h2-console/**", "/h2-console", "/actuator/**").permitAll();

        // telling to check all urls
        http.authorizeRequests().anyRequest().authenticated();

        // setting login  page and authentication
        http.authenticationProvider(authenticationProvider).formLogin().loginPage("/login").loginProcessingUrl("/authenticate")
                .failureForwardUrl("/login")
                .failureUrl("/login")
                .defaultSuccessUrl("/home", true);

        // setting maximum user sessions and registry
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry).expiredUrl("/login");

        // setting the session policy
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).sessionFixation()
                .migrateSession();

        http.headers().frameOptions().disable().addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS",
                "ALLOW-FROM https://www.youtube.com"));
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("*/h2-console/**"));
    }

}
