package uz.pdp.cityuserservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto {
    @Pattern(regexp = "^ROLE_\\w+$")
    @NotBlank(message = "role name cannot be blank")
    private String role;
    private List<String>permission;
}
