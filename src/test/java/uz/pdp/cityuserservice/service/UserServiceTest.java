package uz.pdp.cityuserservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.cityuserservice.domain.dto.LoginDto;
import uz.pdp.cityuserservice.domain.dto.UserRequestDto;
import uz.pdp.cityuserservice.domain.entity.user.PermissionEntity;
import uz.pdp.cityuserservice.domain.entity.user.RoleEntity;
import uz.pdp.cityuserservice.domain.entity.user.UserEntity;
import uz.pdp.cityuserservice.domain.entity.user.UserState;
import uz.pdp.cityuserservice.exceptions.DataNotFoundException;
import uz.pdp.cityuserservice.repository.user.PermissionRepository;
import uz.pdp.cityuserservice.repository.user.RoleRepository;
import uz.pdp.cityuserservice.repository.user.UserRepository;
import uz.pdp.cityuserservice.service.auth.JwtService;
import uz.pdp.cityuserservice.service.mail.MailService;
import uz.pdp.cityuserservice.service.user.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceTest {
    private final String email="user";
    private final String password="password";

    private UserEntity userEntity;
    private UserRequestDto userRequestDto;

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        permissionRepository.save(PermissionEntity.builder().permission("ALL").build());
        roleRepository.save(RoleEntity.builder().role("ADMIN").permissions(List.of()).build());
        userRequestDto= new UserRequestDto("user",email,password,List.of("ADMIN"),List.of("ALL"));
        userEntity=UserEntity.builder()
                .email(email)
                .name("user")
                .state(UserState.ACTIVE)
                .roles(List.of(RoleEntity.builder().build()))
                .permissions(List.of(PermissionEntity.builder().build()))
                .build();
        mailService.sendVerificationCode(userEntity);



    }

    @Test
    void loginExceptionTest() {
        LoginDto loginDto = new LoginDto(email, password);
        when(userRepository.findUserEntityByEmail(email)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> userService.login(loginDto));
    }


    @Test
    void signUpUserTest(){
        when(modelMapper.map(userRequestDto,UserEntity.class)).thenReturn(userEntity);
        when(passwordEncoder.encode(userEntity.getPassword())).thenReturn(password);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserEntity user = userService.signUp(userRequestDto);
        assertEquals(email,user.getEmail());
    }
}
