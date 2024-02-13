package dev.adovgapp.advogapp.dto.security;

import dev.adovgapp.advogapp.enums.UserRole;

public record RegisterDTO(String fullName,String email, String password) {
}
