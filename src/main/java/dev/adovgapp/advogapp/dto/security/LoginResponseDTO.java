package dev.adovgapp.advogapp.dto.security;

import java.util.Optional;

public record LoginResponseDTO(String token, Optional<String> lawyerId, String userId, String role) {
}
