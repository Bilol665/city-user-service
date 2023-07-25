package uz.pdp.citybookingservice.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.citybookingservice.exception.DataNotFoundException;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<String> dataNotFoundExp(DataNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
