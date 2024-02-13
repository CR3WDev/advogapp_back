package dev.adovgapp.advogapp.controllers;

import dev.adovgapp.advogapp.dto.User.UserRequestDTO;
import dev.adovgapp.advogapp.dto.User.UserResponseDTO;
import dev.adovgapp.advogapp.dto.lawyer.LawyerRequestDTO;
import dev.adovgapp.advogapp.dto.lawyer.LawyerResponseByIdDTO;
import dev.adovgapp.advogapp.dto.lawyer.LawyerResponseDTO;
import dev.adovgapp.advogapp.enums.Specialization;
import dev.adovgapp.advogapp.exceptions.ApiRequestException;
import dev.adovgapp.advogapp.models.User;
import dev.adovgapp.advogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getLawyerById(@RequestBody @PathVariable String id) {
        try {
            var user = service.findById(id);
            if(user.isEmpty()) {
                throw new ApiRequestException("Advogado Não Cadastrado", HttpStatus.BAD_REQUEST);
            }
            UserResponseDTO userResponseDTO = service.convertToDTO(user.get());
            return ResponseEntity.ok().body(userResponseDTO);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity<String> editUser(@RequestBody UserRequestDTO user) {
        try {
            service.updateUser(user);
            return ResponseEntity.ok().body("Usuário Atualizado!");
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
