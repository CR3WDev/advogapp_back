package dev.adovgapp.advogapp.repositories;

import dev.adovgapp.advogapp.models.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface LawyerRepository extends JpaRepository<Lawyer,String> {

    Optional<Lawyer> findByCPF(String cpf);
}
