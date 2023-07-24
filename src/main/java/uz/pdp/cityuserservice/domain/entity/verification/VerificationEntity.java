package uz.pdp.cityuserservice.domain.entity.verification;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.pdp.cityuserservice.domain.entity.BaseEntity;
import uz.pdp.cityuserservice.domain.entity.user.UserEntity;

@Entity(name = "verifications")
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VerificationEntity extends BaseEntity {
    String link;
    @ManyToOne
    UserEntity user;
    Long code;
}