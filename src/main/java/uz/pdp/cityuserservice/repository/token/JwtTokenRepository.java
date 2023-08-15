package uz.pdp.cityuserservice.repository.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cityuserservice.domain.entity.token.JwtTokenEntity;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity,String> {
}
