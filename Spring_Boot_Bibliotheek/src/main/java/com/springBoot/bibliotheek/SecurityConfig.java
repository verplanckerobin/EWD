package com.springBoot.bibliotheek;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGobal(AuthenticationManagerBuilder auth) throws Exception {
	auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder())
		.usersByUsernameQuery("select username,password,enabled from `Gebruiker` where username = ?")
		.authoritiesByUsernameQuery("select username,authority from `Gebruiker` where username = ?");
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	// @formatter:off
	http.csrf().and().authorizeHttpRequests(requests -> requests
        		.requestMatchers("/login**").permitAll()
        		.requestMatchers("/css/**").permitAll()
        		.requestMatchers("/voeg-boek-toe").hasRole("ADMIN")
        		.requestMatchers("/rest/boek/**").permitAll()
        		.requestMatchers("/403**").permitAll()
        		.requestMatchers("/**").access(new WebExpressionAuthorizationManager("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')"))
        	)
		.formLogin(form -> form.defaultSuccessUrl("/bibliotheek", true))
		.exceptionHandling().accessDeniedPage("/403");
	return http.build();
	// @formatter:on
    }
}
