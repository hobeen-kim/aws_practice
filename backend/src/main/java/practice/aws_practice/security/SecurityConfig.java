package practice.aws_practice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthService oAuthService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf()
                .csrfTokenRepository(sessionCsrfRepository())
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic().disable()
                .formLogin()
                .loginProcessingUrl("/form/login")
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .sessionFixation().changeSessionId()
                .and()
                .authorizeRequests()
                .antMatchers("/userInfo").authenticated()
                .antMatchers("/auth").authenticated()
                .anyRequest().permitAll()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .successHandler(customAuthenticationSuccessHandler())
                .userInfoEndpoint()
                .userService(oAuthService);

        return http.build();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(new ChangeSessionIdAuthenticationStrategy());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://sixman-front-s3.s3-website.ap-northeast-2.amazonaws.com");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("X-CSRF-TOKEN"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    HttpSessionCsrfTokenRepository sessionCsrfRepository() {
        HttpSessionCsrfTokenRepository csrfRepository = new HttpSessionCsrfTokenRepository();

        // HTTP 헤더에서 토큰을 인덱싱하는 문자열 설정
        csrfRepository.setHeaderName("X-CSRF-TOKEN");
        // URL 파라미터에서 토큰에 대응되는 변수 설정
        csrfRepository.setParameterName("_csrf");
        // 세션에서 토큰을 인덱싱 하는 문자열을 설정. 기본값이 무척 길어서 오버라이딩 하는 게 좋아요.
        // 기본값: "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN"
        csrfRepository.setSessionAttributeName("CSRF_TOKEN");

        return csrfRepository;
    }
}