package dev.adovgapp.advogapp.repositories;

import dev.adovgapp.advogapp.models.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface LawyerRepository extends JpaRepository<Lawyer,String> {

    Optional<Lawyer> findByCPF(String cpf);

    Optional<Lawyer> findByOAB(String oab);

    @Query("SELECT l FROM Lawyer l WHERE l.user.id = :userId OR l.CPF = :cpf OR l.OAB = :oab")
    Optional<Lawyer> findByUniqueValues(@Param("userId") String userId, @Param("cpf") String cpf,@Param("oab") String oab);
}
