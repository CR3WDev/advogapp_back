package dev.adovgapp.advogapp.repositories;

import dev.adovgapp.advogapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    UserDetails findUserByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

}
