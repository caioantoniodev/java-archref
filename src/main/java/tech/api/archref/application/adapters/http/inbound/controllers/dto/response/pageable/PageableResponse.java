package tech.api.archref.application.adapters.http.inbound.controllers.dto.response.pageable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PageableResponse<T> {

    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalRecords;

    public PageableResponse(List<T> content, int currentPage, int totalPages, long totalRecords) {
        this.content = content;
        this.currentPage = currentPage + 1;
        this.totalPages = (int) Math.ceil((double) totalRecords / totalPages);
        this.totalRecords = totalRecords;
    }
}
