package dev.adovgapp.advogapp.services;

import dev.adovgapp.advogapp.dto.User.UserRequestDTO;
import dev.adovgapp.advogapp.dto.User.UserResponseDTO;
import dev.adovgapp.advogapp.dto.lawyer.LawyerResponseDTO;
import dev.adovgapp.advogapp.enums.Specialization;
import dev.adovgapp.advogapp.exceptions.ApiRequestException;
import dev.adovgapp.advogapp.models.Lawyer;
import dev.adovgapp.advogapp.models.User;
import dev.adovgapp.advogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getFullName(),user.getEmail());
    }

    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    public User updateUser(UserRequestDTO user) {
        Optional<User> userFound = findById(user.id());
        if(userFound.isEmpty()){
            throw new ApiRequestException("Usuário Não encontrado!", HttpStatus.BAD_REQUEST);
        }
        User newUser = userFound.get();
        newUser.setFullName(user.fullName());
        return repository.save(newUser);
    }
}
