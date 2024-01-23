package dev.adovgapp.advogapp.domain.user;

import dev.adovgapp.advogapp.enums.UserRole;

public record RegisterDTO(String fullName,String email, String password, UserRole role) {
}
