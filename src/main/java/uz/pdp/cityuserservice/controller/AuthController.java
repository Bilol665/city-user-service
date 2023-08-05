package uz.pdp.cityuserservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityuserservice.domain.dto.*;
import uz.pdp.cityuserservice.domain.dto.response.ApiResponse;
import uz.pdp.cityuserservice.domain.dto.response.JwtResponse;
import uz.pdp.cityuserservice.domain.entity.user.UserEntity;
import uz.pdp.cityuserservice.exceptions.RequestValidationException;
import uz.pdp.cityuserservice.service.user.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserEntity> signUp(
            @Valid @RequestBody UserRequestDto userCreateDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) throw new RequestValidationException(bindingResult.getAllErrors());
        return ResponseEntity.ok(userService.signUp(userCreateDto));
    }

    @GetMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody LoginDto loginDto
    ) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @GetMapping("/reset-password/{userId}")
    public ResponseEntity<ApiResponse> resetPassword(
            @PathVariable UUID userId
    ){
        return ResponseEntity.ok(userService.resetPassword(userId));
    }
    @PutMapping("/changePassword/{email}")
    public ResponseEntity<ApiResponse> changePassword(
            @PathVariable String email,
            @RequestBody ResetPasswordDto resetPasswordDto
    ) {
        return ResponseEntity.ok(userService.resetPassword(email, resetPasswordDto));
    }
    @GetMapping ("/verify/{userId}/{code}")
    @CrossOrigin(origins = "http://localhost:3000")
    public String verify(
            @PathVariable UUID userId,
            @PathVariable String code
    ){
        return userService.verify(userId,code);
    }
    @GetMapping("/get")
    public ResponseEntity<UserDetails> getUser(
            @RequestBody Username username
    ) {
        return ResponseEntity.ok(userService.loadUserByUsername(username.getUsername()));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserEntity> getUserById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(userService.loadByUserId(id));
    }
}
