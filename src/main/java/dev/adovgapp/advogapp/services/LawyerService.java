package dev.adovgapp.advogapp.services;

import dev.adovgapp.advogapp.dto.LawyerRequestDTO;
import dev.adovgapp.advogapp.dto.LawyerResponseDTO;
import dev.adovgapp.advogapp.enums.Specialization;
import dev.adovgapp.advogapp.enums.UserRole;
import dev.adovgapp.advogapp.exceptions.ApiRequestException;
import dev.adovgapp.advogapp.exceptions.RestError;
import dev.adovgapp.advogapp.models.Lawyer;
import dev.adovgapp.advogapp.models.User;
import dev.adovgapp.advogapp.repositories.LawyerRepository;
import dev.adovgapp.advogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LawyerService {

    @Autowired
    LawyerRepository repository;

    @Autowired
    UserRepository userRepository;


    public LawyerResponseDTO convertToDTO(Lawyer lawyer) {
        return new LawyerResponseDTO(lawyer.getUser().getFullName(), Specialization.getByCode(lawyer.getSpecialization()));
    }
    public List<LawyerResponseDTO> convertToListDTO(Page<Lawyer> lawyerPage) {
         List<LawyerResponseDTO> lawyerDTOList = lawyerPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());;
        return lawyerDTOList;
    }

    public Lawyer save(LawyerRequestDTO lawyerRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Lawyer> lawyerFindedByOAB = repository.findByUniqueValues(user.getId(),lawyerRequestDTO.cpf(),lawyerRequestDTO.oab());

        if(lawyerFindedByOAB.isPresent()) {
            throw new ApiRequestException("Usuário já cadastrado",HttpStatus.BAD_REQUEST);
        }

        Lawyer lawyer = new Lawyer();
        lawyer.setOAB(lawyer.getOAB());
        lawyer.setSpecialization(lawyerRequestDTO.specialization());
        lawyer.setCPF(lawyerRequestDTO.cpf());
        lawyer.setUser(user);
        return repository.save(lawyer);
    }

    public List<Lawyer> finAll() {
        return repository.findAll();
    }

    public Optional<Lawyer> findById(String id) {
        return repository.findById(id);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

//    public Lawyer update(Lawyer lawyer) {
//        return save(lawyer);
//    }

    public Page<Lawyer> findAllByPage(int pagina, int tamanhoPagina) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return repository.findAll(pageRequest);
    }
}