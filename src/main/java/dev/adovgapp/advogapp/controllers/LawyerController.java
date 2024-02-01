package dev.adovgapp.advogapp.controllers;

import dev.adovgapp.advogapp.dto.LawyerRequestDTO;
import dev.adovgapp.advogapp.exceptions.ApiRequestException;
import dev.adovgapp.advogapp.models.Lawyer;
import dev.adovgapp.advogapp.dto.Pagination;
import dev.adovgapp.advogapp.services.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lawyer")
public class LawyerController {
    @Autowired
    private LawyerService service;


    @PostMapping
    public ResponseEntity addLawyer(@RequestBody LawyerRequestDTO data) {
        try {
        var lawyer = service.save(data);
        return ResponseEntity.ok().body(lawyer);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/list")
    public ResponseEntity lawyerListByPage(@RequestBody Pagination data) {
        try {
            var pagina = service.findAllByPage(data.pagina(), data.tamanhoPagina());
            return ResponseEntity.ok().body(pagina);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
