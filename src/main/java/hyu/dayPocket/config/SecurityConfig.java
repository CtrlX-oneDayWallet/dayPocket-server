package hyu.dayPocket.config;

import hyu.dayPocket.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public static final List<RestfulUrl> WHITELIST = List.of(
            new RestfulUrl(HttpMethod.POST, "/auth/login"),
            new RestfulUrl(HttpMethod.POST, "/auth/phoneNumber"),
            new RestfulUrl(HttpMethod.POST, "/auth/signup"),
            new RestfulUrl(HttpMethod.GET, "/auth/auth-code"),
            new RestfulUrl(HttpMethod.GET, "/error")
    );

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> {
                    for (RestfulUrl url : WHITELIST) {
                        requests.requestMatchers(url.getMethod(), url.getPath()).permitAll();
                    }
                    requests.anyRequest().authenticated();
                })
                .cors(cors -> cors
                        .configurationSource(CorsConfig.corsConfigurationSource())
                )
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
