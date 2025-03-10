package com.nhnacademy.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.nhnacademy.springjwt.jwt.CustomLogoutFilter;
import com.nhnacademy.springjwt.jwt.JWTFilter;
import com.nhnacademy.springjwt.jwt.JWTUtils;
import com.nhnacademy.springjwt.jwt.LoginFilter;
import com.nhnacademy.springjwt.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtils jwtUtils;
	// private final RefreshTokenRepository refreshTokenRepository;
	private final RedisTemplate<String, Object> redisTemplate;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		//csrf disable
		http
			.csrf((auth) -> auth.disable());

		//From 로그인 방식 disable
		http
			.formLogin((auth) -> auth.disable());

		//http basic 인증 방식 disable
		http
			.httpBasic((auth) -> auth.disable());

		//경로별 인가 작업
		http
			.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/login", "/", "/sign-up").permitAll()
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/reissue").permitAll()
				.anyRequest().authenticated());

		http.addFilterBefore(new JWTFilter(jwtUtils), LoginFilter.class);
		http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtils, redisTemplate), UsernamePasswordAuthenticationFilter.class);
		// http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtils, refreshTokenRepository), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(new CustomLogoutFilter(redisTemplate, jwtUtils), LogoutFilter.class);
		// http.addFilterBefore(new CustomLogoutFilter(refreshTokenRepository, jwtUtils), LogoutFilter.class);

		//세션 설정
		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
