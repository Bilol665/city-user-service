package uz.pdp.citybookingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@MappedSuperclass
public abstract class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @CreationTimestamp
    private LocalDateTime whenBooked;
    @UpdateTimestamp
    private LocalDateTime whenWillFinish;
    private UUID ownerId;
    private Double price;
    private UUID orderId;
    private BookingType type;
}
