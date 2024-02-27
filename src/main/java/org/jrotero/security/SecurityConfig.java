package org.jrotero.security;


import org.jrotero.security.filters.JwtAuthenticationFilter;
import org.jrotero.security.filters.JwtAuthorizationFilter;
import org.jrotero.security.jwt.JwtUtils;
import org.jrotero.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	JwtAuthorizationFilter authorizationFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
		JwtAuthenticationFilter autho = new JwtAuthenticationFilter(jwtUtils);
		autho.setAuthenticationManager(authenticationManager);
		autho.setFilterProcessesUrl("/login");
		return httpSecurity.csrf(config -> config.disable()).authorizeHttpRequests(auth -> {
			auth.requestMatchers("/create-first-user").permitAll();
			auth.anyRequest().authenticated();
		}).sessionManagement(session -> {
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}).addFilter(autho)
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class).build();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("Sergio").password("1234").roles().build());
		return manager;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {		
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder)
				.and().build();
	}
}
