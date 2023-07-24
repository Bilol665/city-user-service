package uz.pdp.cityuserservice.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class VerificationDto {
    private String code;
    private String link;
    private String userEmail;
}
