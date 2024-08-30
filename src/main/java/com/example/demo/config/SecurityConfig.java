package com.example.demo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import com.example.demo.security.JwtAuthenticationEntryPoint;
import com.example.demo.security.JwtAuthenticationFilter;

//import io.jsonwebtoken.lang.Arrays;

@Configuration
public class SecurityConfig {


    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
    
    @Value("${allowed.origin.local}")
	private String allowedOriginLocal;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final String[] ALLOWED_HEADERS = { "Authorization", "Content-Type", "observe", "Accept", "Set-Cookie",
			"Rate-Limit-Remaining", "X-Content-Type-Options", "X-XSS-Protection", "Cache-Control", "X-Frame-Options",
			"X-Content-Security-Policy", "Strict-Transport-Security", "X-XSRF-TOKEN", "Access-Control-Allow-Headers" };
    
    @Autowired
    private UserDetailsService userDetaisService;
    
    @SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
   
    	http.csrf(csrf -> csrf.disable())
		.cors().and()
        .authorizeHttpRequests(
        		auth -> 
        			auth.requestMatchers("/api/myp/v1/auth/login").permitAll()
        				.requestMatchers("/api/myp/v1/auth/createUser").permitAll()
        				.requestMatchers("/api/**").authenticated()
        					.anyRequest().authenticated())
        .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    
    	http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    	
    	return http.build();
        
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
    	
    	DaoAuthenticationProvider daoAuthProvider=new DaoAuthenticationProvider();
    	daoAuthProvider.setUserDetailsService(userDetaisService);
    	daoAuthProvider.setPasswordEncoder(passwordEncoder);
    	return daoAuthProvider;
    	
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		List<String> allowedOrigins = new ArrayList<>();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS" ));
		corsConfiguration.setAllowedHeaders(Arrays.asList(ALLOWED_HEADERS));
		corsConfiguration.setExposedHeaders(Arrays.asList("X-XSRF-TOKEN"));
		allowedOrigins.add(allowedOriginLocal);
		corsConfiguration.setAllowedOrigins(allowedOrigins);
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;

	}


}