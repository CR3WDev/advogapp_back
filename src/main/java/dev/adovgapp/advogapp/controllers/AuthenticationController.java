package dev.adovgapp.advogapp.controllers;

import dev.adovgapp.advogapp.exceptions.ApiRequestException;
import dev.adovgapp.advogapp.dto.security.LoginRequestDTO;
import dev.adovgapp.advogapp.dto.security.LoginResponseDTO;
import dev.adovgapp.advogapp.dto.security.RegisterDTO;
import dev.adovgapp.advogapp.models.User;
import dev.adovgapp.advogapp.enums.UserRole;
import dev.adovgapp.advogapp.infra.security.TokenService;
import dev.adovgapp.advogapp.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(),data.password());
        try {
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
        }catch(AuthenticationException authenticationException) {
            throw new ApiRequestException("Usuário ou senha inválido!",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if(this.repository.findByEmail(data.email()) != null) {
            throw new ApiRequestException("Email já cadastrado!",HttpStatus.BAD_REQUEST);
        }
        String encrytpedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.fullName(),data.email(), encrytpedPassword, UserRole.USER);

        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
