package tech.api.archref.application.adapters.http.inbound.controllers.dto.response.pageable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PageableResponse<T>(List<T> content,
                                  int currentPage,
                                  int totalPages,
                                  long totalRecords) {
}
