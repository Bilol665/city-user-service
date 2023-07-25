package uz.pdp.cityuserservice.domain.entity.user;

import jakarta.persistence.*;
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
    @Column(unique = true)
    String role;
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    List<PermissionEntity> permissions;
}