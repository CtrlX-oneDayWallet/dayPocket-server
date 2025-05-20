package hyu.dayPocket.config;

import hyu.dayPocket.exceptions.JwtTokenException;
import hyu.dayPocket.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailService userDetailService;
    private final JwtTokenUtils jwtTokenUtils;
    private final List<RestfulUrl> WHITELIST = SecurityConfig.WHITELIST;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null && jwtTokenUtils.validateToken(token)) {
            String phoneNumber = (String) jwtTokenUtils.getClaims(token).get("PhoneNumber");

            UserDetails userDetails = userDetailService.loadUserByUsername(phoneNumber);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return WHITELIST.stream().anyMatch(
                url -> url.getPath().equals(path) && method.equals(url.getMethod())
        );
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        throw new JwtTokenException("토큰이 존재하지 않거나, 잘못된 형식입니다.");
    }
}