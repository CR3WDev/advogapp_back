package dev.adovgapp.advogapp.controllers;

import dev.adovgapp.advogapp.dto.lawyer.LawyerResponseDTO;
import dev.adovgapp.advogapp.dto.security.RecoverPasswordDTO;
import dev.adovgapp.advogapp.dto.security.ResetPasswordDTO;
import dev.adovgapp.advogapp.dto.security.ResponseListDTO;
import dev.adovgapp.advogapp.exceptions.ApiRequestException;
import dev.adovgapp.advogapp.models.EmailModel;
import dev.adovgapp.advogapp.models.Lawyer;
import dev.adovgapp.advogapp.models.ResetPasswordModel;
import dev.adovgapp.advogapp.services.EmailService;
import dev.adovgapp.advogapp.services.LawyerService;
import dev.adovgapp.advogapp.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    LawyerService service;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/lawyer/list")
    public ResponseEntity<ResponseListDTO<LawyerResponseDTO>> lawyerPublicList() {
        try {
            Page<Lawyer> lawyerPage = service.findAllByPage(0, 20);
            List<LawyerResponseDTO> lawyerPageDTO = service.convertToListDTO(lawyerPage);
            ResponseListDTO<LawyerResponseDTO> responseListDTO = new ResponseListDTO<LawyerResponseDTO>(lawyerPageDTO,lawyerPage.getTotalElements());
            return ResponseEntity.ok().body(responseListDTO);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recoverpassword")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid RecoverPasswordDTO recoverPasswordDTO){
       EmailModel emailModel = userService.recoverPassword(recoverPasswordDTO.email());
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<ResetPasswordModel> sendingEmail(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO){
        ResetPasswordModel resetPasswordModel = userService.resetPassword(resetPasswordDTO.token(),resetPasswordDTO.password());
        return new ResponseEntity<>(resetPasswordModel, HttpStatus.CREATED);
    }
}
