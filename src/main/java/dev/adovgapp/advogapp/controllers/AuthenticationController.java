package dev.adovgapp.advogapp.controllers;

import dev.adovgapp.advogapp.domain.user.AuthenticationDTO;
import dev.adovgapp.advogapp.domain.user.RegisterDTO;
import dev.adovgapp.advogapp.domain.user.User;
import dev.adovgapp.advogapp.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(),data.password());
        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            System.out.println("Usuário autenticado com sucesso: " + auth.getName());
        } catch (AuthenticationException e) {
            System.out.println("Falha na autenticação: " + e.getMessage());
            throw e;
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        String encrytpedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encrytpedPassword, data.role());

        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
