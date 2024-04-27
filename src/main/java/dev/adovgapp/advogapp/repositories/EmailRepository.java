package dev.adovgapp.advogapp.repositories;

import dev.adovgapp.advogapp.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailModel,Long> {
}
