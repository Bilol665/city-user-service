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
import uz.pdp.cityuserservice.domain.entity.user.PermissionEntity;
import uz.pdp.cityuserservice.domain.entity.user.RoleEntity;
import uz.pdp.cityuserservice.domain.entity.user.UserEntity;
import uz.pdp.cityuserservice.domain.entity.user.UserState;
import uz.pdp.cityuserservice.exceptions.AuthFailedException;
import uz.pdp.cityuserservice.exceptions.DataNotFoundException;
import uz.pdp.cityuserservice.exceptions.NotAcceptable;
import uz.pdp.cityuserservice.repository.user.PermissionRepository;
import uz.pdp.cityuserservice.repository.user.RoleRepository;
import uz.pdp.cityuserservice.repository.user.UserRepository;
import uz.pdp.cityuserservice.service.auth.JwtService;
import uz.pdp.cityuserservice.service.mail.MailService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository userRoleRepository;
    private final PermissionRepository permissionRepository;
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
    public UserEntity signUp(UserRequestDto userRequestDto) {
        UserEntity user = modelMapper.map(userRequestDto, UserEntity.class);
        if(checkEmail(user.getEmail())) throw new NotAcceptable("Email already exists!");
        user.setRoles(getRoleFromString(userRequestDto.getRoles()));
        user.setPermissions(getPermissionFromString(userRequestDto.getPermissions()));
        user.setState(UserState.UNVERIFIED);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        UserEntity savedUser = userRepository.save(user);
        mailService.sendVerificationCode(savedUser);
        return savedUser;
    }
    private List<RoleEntity> getRoleFromString(List<String> roles) {
        return userRoleRepository.findRoleEntitiesByRoleIn(roles);
    }
    private List<PermissionEntity> getPermissionFromString(List<String> permission) {
        return permissionRepository.findPermissionEntitiesByPermissionIn(permission);
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
    public ApiResponse resetPassword(String email,ResetPasswordDto resetPasswordDto){
       UserEntity user = userRepository.findUserEntityByEmail(email).
                orElseThrow(()-> new DataNotFoundException("User do not exist"));
       if(!Objects.equals(resetPasswordDto.getNewPassword(),resetPasswordDto.getConfirmPassword())){
           throw new NotAcceptable("Both passwords must be same");
        }
        user.setPassword(resetPasswordDto.getNewPassword());
       return new ApiResponse(HttpStatus.OK,true,"success");
    }
}
