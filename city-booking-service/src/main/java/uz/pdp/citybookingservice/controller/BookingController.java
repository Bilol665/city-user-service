package uz.pdp.citybookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.citybookingservice.dto.ApiResponse;
import uz.pdp.citybookingservice.dto.FlatDto;
import uz.pdp.citybookingservice.service.BookingService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    @PutMapping("/bookFlat{dateTime}")
    public ResponseEntity<ApiResponse> bookFlat(
            @RequestBody FlatDto flatDto,
            @PathVariable LocalDateTime dateTime

            ) {
        return ResponseEntity.ok(bookingService.bookFlat(flatDto.getFlatId(),flatDto.getOwnerId(),dateTime));
    }
}
