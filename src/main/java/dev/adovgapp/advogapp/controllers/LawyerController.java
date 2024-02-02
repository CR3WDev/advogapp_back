package dev.adovgapp.advogapp.controllers;

import dev.adovgapp.advogapp.dto.LawyerRequestDTO;
import dev.adovgapp.advogapp.dto.LawyerResponseDTO;
import dev.adovgapp.advogapp.dto.ResponseListDTO;
import dev.adovgapp.advogapp.enums.Specialization;
import dev.adovgapp.advogapp.exceptions.ApiRequestException;
import dev.adovgapp.advogapp.dto.RequestListDTO;
import dev.adovgapp.advogapp.models.Lawyer;
import dev.adovgapp.advogapp.services.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lawyer")
public class LawyerController {
    @Autowired
    private LawyerService service;


    @PostMapping
    public ResponseEntity<LawyerResponseDTO> addLawyer(@RequestBody LawyerRequestDTO data) {
        try {
            var lawyer = service.save(data);
            LawyerResponseDTO lawyerResponseDTO = new LawyerResponseDTO(lawyer.getUser().getFullName(), Specialization.getByCode(lawyer.getSpecialization()));
        return ResponseEntity.ok().body(lawyerResponseDTO);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseListDTO<LawyerResponseDTO>> lawyerListByPage(@RequestBody RequestListDTO data) {
        try {
            Page<Lawyer> lawyerPage = service.findAllByPage(data.pagina(), data.tamanhoPagina());
            List<LawyerResponseDTO> lawyerPageDTO = service.convertToListDTO(lawyerPage);
            ResponseListDTO<LawyerResponseDTO> responseListDTO = new ResponseListDTO<LawyerResponseDTO>(lawyerPageDTO,lawyerPage.getTotalElements());
            return ResponseEntity.ok().body(responseListDTO);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
