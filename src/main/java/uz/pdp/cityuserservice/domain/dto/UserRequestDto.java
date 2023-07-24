package uz.pdp.cityuserservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequestDto {
    String name;
    @NotBlank(message = "email must not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?^_`{|}~-]+)*@"
            + "[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String email;

    @NotBlank(message = "password must not be blank")
    private String password;
    List<String> roles;
    List<String> permissions;
}
