package uz.pdp.cityuserservice.domain.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDto {
    String email;
    String password;
}
