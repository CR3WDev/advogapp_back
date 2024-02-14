package dev.adovgapp.advogapp.dto.lawyer;

import lombok.Data;

@Data
public class LawyerResponseByIdDTO {
    private String id;
    private String fullName;
    private String email;
    private String cpf;
    private String OAB;
    private String description;
    private Long rating;
    private Integer reviewsNumber;
    private String specialization;
    private String userId;

}
