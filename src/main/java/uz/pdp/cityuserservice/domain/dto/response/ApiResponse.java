package uz.pdp.cityuserservice.domain.dto.response;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private HttpStatus status;
    private boolean success;
    private String message;
    private Object data;

    public ApiResponse(HttpStatus status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }
}
