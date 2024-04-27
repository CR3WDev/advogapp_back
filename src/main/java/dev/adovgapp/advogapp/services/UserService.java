package dev.adovgapp.advogapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.adovgapp.advogapp.dto.User.UserRequestDTO;
import dev.adovgapp.advogapp.dto.User.UserResponseDTO;
import dev.adovgapp.advogapp.exceptions.ApiRequestException;
import dev.adovgapp.advogapp.models.EmailModel;
import dev.adovgapp.advogapp.models.ResetPasswordModel;
import dev.adovgapp.advogapp.models.User;
import dev.adovgapp.advogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    private ObjectMapper objectMapper;

    public UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getFullName(),user.getEmail());
    }

    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
    public UserDetails findUserByEmail(String email) {
        return repository.findUserByEmail(email);
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

    public User update(User user) {
        return repository.save(user);
    }

    public EmailModel recoverPassword(String email) {
        Optional<User> userFound = findByEmail(email);
        if(userFound.isEmpty()){
            throw new ApiRequestException("Usuário Não encontrado!", HttpStatus.BAD_REQUEST);
        }

        ResetPasswordModel resetPasswordModel = resetPasswordService.generateToken(userFound.get().getId());
        byte[] encodeToken = Base64.getEncoder().encode(resetPasswordModel.getToken().getBytes());

        EmailModel emailModel = new EmailModel();
        emailModel.setSubject("Recuperação de Senha");
        emailModel.setText("http://localhost:5173/changepassword?params=" + new String(encodeToken));
        emailModel.setUserId(userFound.get().getId());
        emailModel.setEmailTo(email);

        return emailService.sendEmail(emailModel);
    }

    public ResetPasswordModel resetPassword(String token,String password) {
      Optional<ResetPasswordModel> resetPasswordModel =  resetPasswordService.findByToken(token);
      if(resetPasswordModel.isEmpty()) {
          throw new ApiRequestException("Token Inválido!", HttpStatus.BAD_REQUEST);
      }
      Optional<User> user = findById(resetPasswordModel.get().getUserId());
      if(user.isEmpty()) {
          throw new ApiRequestException("Token Inválido!", HttpStatus.BAD_REQUEST);
      }
      User userFounded = user.get();
      String encrytpedPassword = new BCryptPasswordEncoder().encode(password);
      userFounded.setPassword(encrytpedPassword);
      update(userFounded);
      return resetPasswordModel.get();
    }
}
