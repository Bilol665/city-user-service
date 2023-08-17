package uz.pdp.cityuserservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.cityuserservice.domain.entity.token.JwtTokenEntity;
import uz.pdp.cityuserservice.exceptions.NotAcceptable;
import uz.pdp.cityuserservice.repository.token.JwtTokenRepository;
import uz.pdp.cityuserservice.service.auth.AuthenticationService;
import uz.pdp.cityuserservice.service.auth.JwtService;

import java.io.IOException;
import java.util.Date;

@AllArgsConstructor
public class JwtFilterToken extends OncePerRequestFilter {
    private JwtService jwtService;
    private AuthenticationService authenticationService;
    private JwtTokenRepository jwtTokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);
        Jws<Claims> claimsJws = jwtService.extractToken(token);
        jwtTokenRepository.save(new JwtTokenEntity(claimsJws.getBody().getSubject(),token));
        Date expiration = claimsJws.getBody().getExpiration();
        if(new Date().after(expiration)) throw new NotAcceptable("Expired access token!");
        authenticationService.Authenticate(claimsJws.getBody(), request);

        filterChain.doFilter(request, response);
    }
}
