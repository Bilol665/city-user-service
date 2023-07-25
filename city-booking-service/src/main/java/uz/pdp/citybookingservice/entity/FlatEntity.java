package uz.pdp.citybookingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;
@Entity(name = "flats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FlatEntity extends BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private FlatStatus flatStatus;
}
