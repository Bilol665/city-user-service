package uz.pdp.cityuserservice.domain.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoleEntity  {
    @Id
    @Column(unique = true)
    String role;
//    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
//    List<PermissionEntity> permissions;
}