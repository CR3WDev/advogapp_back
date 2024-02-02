package dev.adovgapp.advogapp.dto;

import java.util.List;

public record ResponseListDTO<T>(List<T> list,Long totalRecords) {
}
