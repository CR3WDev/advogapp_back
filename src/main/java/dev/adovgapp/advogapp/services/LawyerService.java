package dev.adovgapp.advogapp.services;

import dev.adovgapp.advogapp.dto.lawyer.LawyerRequestDTO;
import dev.adovgapp.advogapp.dto.lawyer.LawyerResponseByIdDTO;
import dev.adovgapp.advogapp.dto.lawyer.LawyerResponseDTO;
import dev.adovgapp.advogapp.enums.Specialization;
import dev.adovgapp.advogapp.enums.UserRole;
import dev.adovgapp.advogapp.exceptions.ApiRequestException;
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
        return new LawyerResponseDTO(lawyer.getId(), lawyer.getUser().getFullName(), Specialization.getByCode(lawyer.getSpecialization()));
    }
    public LawyerResponseByIdDTO convertToByIdDTO(Lawyer lawyer) {
        LawyerResponseByIdDTO lawyerResponseByIdDTO = new LawyerResponseByIdDTO();
        lawyerResponseByIdDTO.setId(lawyer.getId());
        lawyerResponseByIdDTO.setFullName(lawyer.getUser().getFullName());
        lawyerResponseByIdDTO.setEmail(lawyer.getUser().getEmail());
        lawyerResponseByIdDTO.setCpf(lawyer.getCPF());
        lawyerResponseByIdDTO.setOAB(lawyer.getOAB());
        lawyerResponseByIdDTO.setDescription(lawyer.getDescription());
        lawyerResponseByIdDTO.setRating(lawyer.getRating());
        lawyerResponseByIdDTO.setUserId(lawyer.getUser().getId());
        lawyerResponseByIdDTO.setReviewsNumber(lawyer.getReviewsNumber());
        lawyerResponseByIdDTO.setSpecialization(Specialization.getByCode(lawyer.getSpecialization()));
        return lawyerResponseByIdDTO;
    }
    public List<LawyerResponseDTO> convertToListDTO(Page<Lawyer> lawyerPage) {
         List<LawyerResponseDTO> lawyerDTOList = lawyerPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());;
        return lawyerDTOList;
    }

    private void updateUserToLawyer(User user,Lawyer lawyer) {
        user.setLawyer(lawyer);
        user.setRole(UserRole.LAWYER);
        userRepository.save(user);
    }
    public Lawyer save(LawyerRequestDTO lawyerRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Lawyer> lawyerFindedByOAB = repository.findByUniqueValues(user.getId(),lawyerRequestDTO.cpf(),lawyerRequestDTO.oab());

        if(lawyerFindedByOAB.isPresent()) {
            throw new ApiRequestException("Usuário já cadastrado",HttpStatus.BAD_REQUEST);
        }
        Lawyer lawyer = new Lawyer();
        lawyer.setOAB(lawyerRequestDTO.oab());
        lawyer.setSpecialization(lawyerRequestDTO.specialization());
        lawyer.setCPF(lawyerRequestDTO.cpf());
        lawyer.setUser(user);
        updateUserToLawyer(user,lawyer);
        return repository.save(lawyer);
    }

    public Lawyer updateLawyer(LawyerRequestDTO lawyerRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Lawyer> lawyerFindedByOAB = repository.findByUniqueValues(user.getId(),lawyerRequestDTO.cpf(),lawyerRequestDTO.oab());

        if(lawyerFindedByOAB.isPresent()) {
            throw new ApiRequestException("Usuário já cadastrado",HttpStatus.BAD_REQUEST);
        }
        Lawyer lawyer = new Lawyer();
        lawyer.setOAB(lawyerRequestDTO.oab());
        lawyer.setSpecialization(lawyerRequestDTO.specialization());
        lawyer.setCPF(lawyerRequestDTO.cpf());
        lawyer.setUser(user);
        updateUserToLawyer(user,lawyer);
        return repository.save(lawyer);
    }

    public List<Lawyer> finAll() {
        return repository.findAll();
    }

    public Optional<Lawyer> findById(String id) {
        return repository.findById(id);
    }
    public Optional<Lawyer> findByUserId(String id) {
        return repository.findByUserId(id);
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
