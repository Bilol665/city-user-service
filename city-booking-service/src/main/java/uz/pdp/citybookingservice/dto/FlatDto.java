package uz.pdp.citybookingservice.dto;

import lombok.*;
import uz.pdp.citybookingservice.entity.FlatStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlatDto {
    private UUID flatId;
    private UUID ownerId;


}
