package dev.adovgapp.advogapp.models;

import dev.adovgapp.advogapp.enums.Specialization;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
@Entity
@Table(name = "lawyer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Lawyer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String OAB;
    private String CPF;
    private Integer specialization;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
