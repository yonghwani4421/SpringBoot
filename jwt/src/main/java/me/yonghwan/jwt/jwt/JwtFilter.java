package me.yonghwan.jwt.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yonghwan.jwt.domain.User;
import me.yonghwan.jwt.dto.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authrization = request.getHeader(HEADER_AUTHORIZATION);

        if (authrization == null || !authrization.startsWith(TOKEN_PREFIX)){
            log.debug("token null");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authrization.split(" ")[1];

        if (jwtUtil.isExpired(token)){
            log.debug("token expired");
            filterChain.doFilter(request,response);
            return;
        }


        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        CustomUserDetails customUserDetails = new CustomUserDetails(User.builder()
                .email(jwtUtil.getEmail(token))
                .role(jwtUtil.getRole(token))
                .password("temppassword")
                .build());

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        filterChain.doFilter(request, response);
    }
}
