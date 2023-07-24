package uz.pdp.cityuserservice.domain.entity.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.pdp.cityuserservice.domain.entity.BaseEntity;

import java.util.List;

@Entity(name = "roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoleEntity extends BaseEntity {
    String role;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<PermissionEntity> permissions;
}