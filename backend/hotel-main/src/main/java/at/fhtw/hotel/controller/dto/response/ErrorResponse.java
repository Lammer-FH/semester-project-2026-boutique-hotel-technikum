package at.fhtw.hotel.controller.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ErrorResponse {
    ErrorPayload error;

    @Value
    @Builder
    @Jacksonized
    public static class ErrorPayload {
        String message;
        String code;
        List<ErrorDetail> details;
    }

    @Value
    @Builder
    @Jacksonized
    public static class ErrorDetail {
        String field;
        String message;
    }

}
