package uz.pdp.cityuserservice.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cityuserservice.domain.entity.user.RoleEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    List<RoleEntity> findRoleEntitiesByRoleIn(List<String> roles);

}
