package at.fhtw.hotel.controller.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PaginatedResponse<T> {
    List<T> data;
    Pagination pagination;

    @Value
    @Builder
    @Jacksonized
    public static class Pagination {
        int page;
        int size;
        long total;
        long totalPages;
    }
}
