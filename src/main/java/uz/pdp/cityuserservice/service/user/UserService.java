package uz.pdp.cityuserservice.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.cityuserservice.domain.dto.LoginDto;
import uz.pdp.cityuserservice.domain.dto.ResetPasswordDto;
import uz.pdp.cityuserservice.domain.dto.UserRequestDto;
import uz.pdp.cityuserservice.domain.dto.response.ApiResponse;
import uz.pdp.cityuserservice.domain.dto.response.JwtResponse;
import uz.pdp.cityuserservice.domain.entity.user.UserEntity;
import uz.pdp.cityuserservice.domain.entity.user.UserState;
import uz.pdp.cityuserservice.domain.entity.verification.VerificationEntity;
import uz.pdp.cityuserservice.exceptions.AuthFailedException;
import uz.pdp.cityuserservice.exceptions.DataNotFoundException;
import uz.pdp.cityuserservice.exceptions.NotAcceptable;
import uz.pdp.cityuserservice.repository.user.RoleRepository;
import uz.pdp.cityuserservice.repository.user.UserRepository;
import uz.pdp.cityuserservice.repository.verification.VerificationRepository;
import uz.pdp.cityuserservice.service.auth.JwtService;
import uz.pdp.cityuserservice.service.mail.MailService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MailService mailService;
    private final JwtService jwtService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserEntityByEmail(username).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
    }

    public UserEntity getUser(String username){
        return userRepository.findUserEntityByEmail(username)
                .orElseThrow(()-> new DataNotFoundException("User Not Found!"));
    }

    public UserEntity getUserById(UUID id){
        return userRepository.findUserEntityById(id)
                .orElseThrow(()->new DataNotFoundException("User not found"));
    }
    public UserEntity signUp(UserRequestDto userRequestDto) {
        UserEntity user = modelMapper.map(userRequestDto, UserEntity.class);
        if(checkEmail(user.getEmail())) throw new NotAcceptable("Email already exists!");
        user.setState(UserState.UNVERIFIED);
        user.setAttempts(0);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRoles(List.of(roleRepository.findById("USER").orElseThrow(() -> new DataNotFoundException("Role not found!"))));
        UserEntity savedUser = userRepository.save(user);
        mailService.sendVerificationCode(savedUser);
        return savedUser;
    }
    private Boolean checkEmail(String email) {
        Integer integer = userRepository.countUserEntitiesByEmail(email);
        return integer >= 1;
    }

    public JwtResponse login(LoginDto loginDto) {
        UserEntity user = userRepository.findUserEntityByEmail(loginDto.getEmail()).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        if(passwordEncoder.matches(loginDto.getPassword(),user.getPassword())) {
            return JwtResponse.builder()
                    .accessToken(jwtService.generateAccessToken(user))
                    .refreshToken(jwtService.generateRefreshToken(user))
                    .build();
        }
        throw new AuthFailedException("Wrong credentials!");
    }
    public ApiResponse resetPassword(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        mailService.sendResetPassword(user.getEmail());
        return new ApiResponse(HttpStatus.OK,true,"Success");
    }
    public ApiResponse resetPassword(Principal principal,ResetPasswordDto resetPasswordDto) {
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName()).
                orElseThrow(() -> new DataNotFoundException("User do not exist"));
        if (!Objects.equals(resetPasswordDto.getNewPassword(), resetPasswordDto.getConfirmPassword())) {
            throw new NotAcceptable("Both passwords must be same");
        }
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        userRepository.save(user);
        return new ApiResponse(HttpStatus.OK, true, "success");
    }


    public ApiResponse verify(UUID userId, String code) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User Not found"));
        VerificationEntity verificationEntity = verificationRepository.findVerificationEntityByUserId(userId)
                .orElseThrow(() -> new DataNotFoundException("Verification code not found"));
        if (user.getAttempts()!=3) {
            if (Objects.equals(code, verificationEntity.getCode().toString())) {
                if (verificationEntity.getCreatedTime().plusMinutes(10).isAfter(LocalDateTime.now())) {
                    verificationRepository.delete(verificationEntity);
                    user.setState(UserState.ACTIVE);
                    UserEntity save = userRepository.save(user);
                    JwtResponse login = login(LoginDto.builder().email(save.getEmail()).password(save.getPassword()).build());
                    return ApiResponse.builder().status(HttpStatus.OK).data(login).message("Successfully verified").success(true).build();
                }
                throw new NotAcceptable("Verification Code Expired!");
            }
            user.setAttempts(user.getAttempts() + 1);
            userRepository.save(user);
            throw new NotAcceptable("Wrong Verification Code!");
        }
        user.setState(UserState.BLOCKED);
        userRepository.save(user);
        throw new NotAcceptable("Too many failed attempts. You have been blocked!");
    }
    public ApiResponse changeName(Principal principal,String name){
        UserEntity userNotFound = userRepository.findUserEntityByEmail(principal.getName()).orElseThrow(() -> new DataNotFoundException("User not found"));
        userNotFound.setName(name);
        UserEntity save = userRepository.save(userNotFound);
        return new ApiResponse(HttpStatus.OK,true,"success",save);
    }
}
