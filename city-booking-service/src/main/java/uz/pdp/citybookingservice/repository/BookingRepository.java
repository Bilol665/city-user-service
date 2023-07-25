package uz.pdp.citybookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.citybookingservice.entity.BookingEntity;

import java.util.UUID;
@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {


}
