package uz.pdp.cityuserservice.domain.entity.user;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.pdp.cityuserservice.domain.entity.BaseEntity;

@Entity(name = "permissions")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PermissionEntity extends BaseEntity {
    String permission;
}