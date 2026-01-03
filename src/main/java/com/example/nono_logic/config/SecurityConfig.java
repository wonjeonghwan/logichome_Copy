package com.example.nono_logic.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // [오류 해결] HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // [오류 해결] AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy; // [오류 해결] SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // [오류 해결] Filter

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 끄기
                .authorizeHttpRequests(auth -> auth
                        // 1. 로그인/회원가입은 누구나 가능
                        .requestMatchers("/auth/**", "/users/**").permitAll()

                        // 2. 퍼즐 '조회(GET)'는 누구나 가능
                        .requestMatchers(HttpMethod.GET, "/api/puzzles/**").permitAll()

                        // 3. 그 외 모든 요청(생성, 삭제 등)은 인증 필요
                        .anyRequest().authenticated()
                )
                // [중요 수정] new JwtAuthenticationFilter(...) 대신 주입받은 빈을 사용
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}