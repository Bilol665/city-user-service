package uz.pdp.cityuserservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.cityuserservice.domain.entity.user.UserEntity;
import uz.pdp.cityuserservice.service.user.UserService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/user/api/v1/get")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class GetterController {
    private final UserService userService;
    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUser(
            @RequestParam String username
    ) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping("/id")
    public ResponseEntity<UserEntity> getUser(
            @RequestParam UUID id
    ) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
