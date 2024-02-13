package dev.adovgapp.advogapp.controllers;

import dev.adovgapp.advogapp.dto.LawyerResponseDTO;
import dev.adovgapp.advogapp.dto.RequestListDTO;
import dev.adovgapp.advogapp.dto.ResponseListDTO;
import dev.adovgapp.advogapp.exceptions.ApiRequestException;
import dev.adovgapp.advogapp.models.Lawyer;
import dev.adovgapp.advogapp.services.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/lawyer")
public class LawyerPublicController {

    @Autowired
    LawyerService service;
    @GetMapping("/list")
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
}
