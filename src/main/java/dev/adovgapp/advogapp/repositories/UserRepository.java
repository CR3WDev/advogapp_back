package dev.adovgapp.advogapp.repositories;

import dev.adovgapp.advogapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,String> {

    UserDetails findByEmail(String email);

}
