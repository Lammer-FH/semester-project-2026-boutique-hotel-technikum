package at.fhtw.hotel.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "Generic paginated response wrapper")
public class PaginatedResponse<T> {
    @Schema(description = "List of items for the current page")
    List<T> data;

    @Schema(description = "Pagination metadata")
    Pagination pagination;

    @Value
    @Builder
    @Jacksonized
    @Schema(description = "Pagination information")
    public static class Pagination {
        @Schema(description = "Current page number (1-based)", example = "1")
        int page;

        @Schema(description = "Number of items per page", example = "5")
        int size;

        @Schema(description = "Total number of items across all pages", example = "12")
        long total;

        @Schema(description = "Total number of pages", example = "3")
        long totalPages;
    }
}
