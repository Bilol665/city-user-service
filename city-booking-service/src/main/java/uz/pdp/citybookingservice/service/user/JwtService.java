package uz.pdp.citybookingservice.service.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey;
    public Jws<Claims> extractToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

}

