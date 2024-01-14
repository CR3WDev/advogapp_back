package dev.adovgapp.advogapp.domain.user;

import dev.adovgapp.advogapp.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
