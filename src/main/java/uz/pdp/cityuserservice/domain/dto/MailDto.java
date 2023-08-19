package uz.pdp.cityuserservice.domain.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MailDto {
    private static final long serialVersionUID = 123;
    String message;
    String email;
}
