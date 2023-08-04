package uz.pdp.cityuserservice.components;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.cityuserservice.domain.entity.user.RoleEntity;
import uz.pdp.cityuserservice.domain.entity.user.UserEntity;
import uz.pdp.cityuserservice.domain.entity.user.UserState;
import uz.pdp.cityuserservice.repository.user.PermissionRepository;
import uz.pdp.cityuserservice.repository.user.UserRepository;



import java.util.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final PermissionRepository permissionRepository;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlMode;

    @Override
    public void run(String... args) {
        if (Objects.equals(ddlMode, "update")) {
            if (userRepository.findUserEntityByEmail("admin@gmail.com").isEmpty()) {
                userRepository.save(new UserEntity(
                        "admin",
                        "admin@gmail.com",
                        passwordEncoder.encode("admin"),
                        List.of(new RoleEntity("ROLE_SUPER_ADMIN", permissionRepository.findAll())),
                        permissionRepository.findAll(),
                        UserState.ACTIVE,
                        0
                ));
            }
        }
    }
}
