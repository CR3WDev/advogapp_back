package dev.adovgapp.advogapp.services;

import dev.adovgapp.advogapp.models.ResetPasswordModel;
import dev.adovgapp.advogapp.repositories.ResetPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetPasswordService {

    @Autowired
    ResetPasswordRepository resetPasswordRepository;

    public Optional<ResetPasswordModel> validateToken(String userId, String token) {
        return resetPasswordRepository.validateToken(userId,token);
    }
    public ResetPasswordModel generateToken(String userId) {
        ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
        resetPasswordModel.setToken(UUID.randomUUID().toString());
        resetPasswordModel.setUserId(userId);
        resetPasswordModel.setExpirationTime(LocalDateTime.now().plusHours(1).toString());
        return this.resetPasswordRepository.save(resetPasswordModel);
    }
    public Optional<ResetPasswordModel> findByToken(String token) {
        return resetPasswordRepository.findByToken(token);
    }
}
