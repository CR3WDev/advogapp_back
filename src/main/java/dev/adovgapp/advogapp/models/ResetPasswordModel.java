package dev.adovgapp.advogapp.models;

import dev.adovgapp.advogapp.enums.StatusEmail;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "reset_password")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ResetPasswordModel implements Serializable {
    private static final long serialVersisonUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String token;
    private String userId;
    private String expirationTime;
}
