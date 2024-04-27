package dev.adovgapp.advogapp.repositories;

import dev.adovgapp.advogapp.models.Lawyer;
import dev.adovgapp.advogapp.models.ResetPasswordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPasswordModel,String> {

    @Query("SELECT rp FROM ResetPasswordModel rp WHERE rp.userId = :userId AND rp.token = :token")
    Optional<ResetPasswordModel> validateToken(@Param("userId") String userId, @Param("token") String token);

    Optional<ResetPasswordModel> findByToken(String token);
}
