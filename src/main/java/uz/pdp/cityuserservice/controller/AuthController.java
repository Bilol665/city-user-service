package uz.pdp.cityuserservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityuserservice.domain.dto.LoginDto;
import uz.pdp.cityuserservice.domain.dto.UserRequestDto;
import uz.pdp.cityuserservice.domain.dto.response.JwtResponse;
import uz.pdp.cityuserservice.domain.entity.user.UserEntity;
import uz.pdp.cityuserservice.exceptions.RequestValidationException;
import uz.pdp.cityuserservice.service.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/api/v1/auth")
public class AuthController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<UserEntity> signUp(
            @Valid @RequestBody UserRequestDto userCreateDto,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) throw new RequestValidationException(bindingResult.getAllErrors());
        return ResponseEntity.ok(userService.signUp(userCreateDto));
    }
    @GetMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody LoginDto loginDto
    ) {
        return ResponseEntity.ok(userService.login(loginDto));
    }
}
