package uz.pdp.cityuserservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.cityuserservice.filter.JwtFilterToken;
import uz.pdp.cityuserservice.repository.token.JwtTokenRepository;
import uz.pdp.cityuserservice.service.auth.AuthenticationService;
import uz.pdp.cityuserservice.service.auth.JwtService;
import uz.pdp.cityuserservice.service.user.UserService;

@Configuration
@EnableMethodSecurity()
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserService auth;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final JwtTokenRepository jwtTokenRepository;
    private final String[] roleCRUD ={"/user/api/v1/role/save","/user/api/v1/role/getRole","/user/api/v1/role/{id}/updateRole","/user/api/v1/role/{id}/deleteRole"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/api/v1/auth/**").permitAll()
                .requestMatchers(roleCRUD).hasAnyRole("SUPER_ADMIN","ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilterToken(jwtService,authenticationService,jwtTokenRepository),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(auth)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
}
