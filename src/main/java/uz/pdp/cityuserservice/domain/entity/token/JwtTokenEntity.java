package uz.pdp.cityuserservice.domain.entity.token;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "token")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class JwtTokenEntity {
    @Id
    private String username;
    private String token;

}
