package dev.adovgapp.advogapp.dto.security;

import java.util.List;

public record ResponseListDTO<T>(List<T> list,Long totalRecords) {
}
