package uz.pdp.cityuserservice.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.cityuserservice.domain.dto.MailDto;
import uz.pdp.cityuserservice.domain.entity.user.UserEntity;
import uz.pdp.cityuserservice.domain.entity.verification.VerificationEntity;
import uz.pdp.cityuserservice.exceptions.DataNotFoundException;
import uz.pdp.cityuserservice.repository.VerificationRepository;
import uz.pdp.cityuserservice.repository.user.UserRepository;

import java.net.URI;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final RestTemplate restTemplate;
    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    @Value("${services.notification-url}")
    private String notificationServiceUrl;
    private final Random random = new Random();

    public void sendVerificationCode(UserEntity user) {
        int i = random.nextInt(10000);
        VerificationEntity verificationEntity = verificationRepository.findVerificationEntityByUserId(user.getId()).orElseGet(
                () -> verificationRepository.save(new VerificationEntity("http://localhost:8086/user/verify/" + user.getId() + "?verificationCode="+i,user, (long) i))
        );
        String message = "This is your verification code to Business management service "
                +verificationEntity.getCode()+"\nThis code will be expired in 10 minutes.\nUse this link to verify "
                +verificationEntity.getLink();
        sendMail(user.getEmail(),message);
    }
    private void sendMail(String email,String message) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        MailDto mailDto = new MailDto(message,userEntity.getEmail());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MailDto> entity = new HttpEntity<>(mailDto,httpHeaders);
        restTemplate.exchange(
                URI.create(notificationServiceUrl + "/send-single"),
                HttpMethod.POST,
                entity,
                String.class
        );
    }
}