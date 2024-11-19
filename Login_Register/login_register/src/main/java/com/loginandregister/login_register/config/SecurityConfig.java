package com.loginandregister.login_register.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.loginandregister.login_register.service.CustomSuccessHandler;
import com.loginandregister.login_register.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
	CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
		
		.authorizeHttpRequests(request -> request
				.requestMatchers("/admin-page").hasAuthority("ADMIN")
				.requestMatchers("/addChapter").hasAuthority("ADMIN")
				.requestMatchers("/user-page").hasAuthority("USER")
				.requestMatchers("/registration", "/css/**", "/home", "/", "/static/**", "/image/**", "/js/**", "/story-info/**", "/chapter/**", "/search", "/category/**", "/list/**", "/author/**").permitAll()
				.anyRequest().authenticated())
		
		.formLogin(form -> form.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/home", true)
				//.successHandler(customSuccessHandler)
				.permitAll())
		
		.logout(form -> form.invalidateHttpSession(true)
				.logoutUrl("/logout").clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/home").permitAll());
		
		return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
	public void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
}