package dev.adovgapp.advogapp.dto.security;

public record LoginResponseDTO(String token,String lawyerId,String userId,String role) {
}
