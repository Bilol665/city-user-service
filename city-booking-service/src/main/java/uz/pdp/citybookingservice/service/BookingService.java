package uz.pdp.citybookingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.citybookingservice.dto.ApiResponse;
import uz.pdp.citybookingservice.entity.BookingEntity;
import uz.pdp.citybookingservice.entity.BookingType;
import uz.pdp.citybookingservice.entity.FlatStatus;
import uz.pdp.citybookingservice.entity.FlatEntity;
import uz.pdp.citybookingservice.exception.DataNotFoundException;
import uz.pdp.citybookingservice.repository.BookingRepository;
import uz.pdp.citybookingservice.repository.FlatRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    private final FlatRepository flatRepository;
    private final BookingRepository bookingRepository;

    public ApiResponse bookFlat(UUID flatId, UUID owner, LocalDateTime startTime) {
        FlatEntity flat = flatRepository.findById(flatId).orElseThrow(() -> new DataNotFoundException("Flat not found"));
        if (flat.getFlatStatus() != FlatStatus.FREE) {
            return new ApiResponse(HttpStatus.BAD_REQUEST, false, "Flat is not available for booking");
        }
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
        LocalDateTime endTime = startTime.plusDays(5);
        if (endTime.isAfter(LocalDateTime.now().plusDays(5))) {
            return new ApiResponse(HttpStatus.BAD_REQUEST, false, "Booking duration cannot exceed 5 days");
        }
        flat.setOwnerId(owner);
        flat.setOrderId(flatId);
        flat.setFlatStatus(FlatStatus.BOOKED);
        flat.setPrice(10000.0);
        flat.setType(BookingType.FLAT);
        flat.setWhenBooked(startTime);
        flat.setWhenWillFinish(endTime);
        bookingRepository.save(flat);

        return new ApiResponse(HttpStatus.OK, true, "success");
    }

    public void cancelBooking(UUID orderId){
        BookingEntity thereIsNoSuchOrders = bookingRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("There is no such orders"));
        bookingRepository.delete(thereIsNoSuchOrders);
    }
}


