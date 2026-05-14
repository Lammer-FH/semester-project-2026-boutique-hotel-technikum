package at.fhtw.hotel.controller;

import at.fhtw.hotel.controller.dto.response.ErrorResponse;
import at.fhtw.hotel.controller.dto.response.ErrorResponse.ErrorDetail;
import at.fhtw.hotel.controller.dto.response.ErrorResponse.ErrorPayload;
import at.fhtw.hotel.domain.DomainException;
import at.fhtw.hotel.domain.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException exception) {
        HttpStatus status = toStatus(exception.getCode());
        ErrorPayload payload = ErrorPayload.builder()
                .message(exception.getMessage())
                .code(exception.getCode().name())
                .details(List.of())
                .build();
        return ResponseEntity.status(status).body(ErrorResponse.builder().error(payload).build());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        List<ErrorDetail> details = new ArrayList<>();
        details.addAll(exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ErrorDetail.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .toList());
        details.addAll(exception.getBindingResult().getGlobalErrors().stream()
                .map(error -> ErrorDetail.builder()
                        .field(error.getObjectName())
                        .message(error.getDefaultMessage())
                        .build())
                .toList());
        ErrorPayload payload = ErrorPayload.builder()
                .message("Validation failed")
                .code(ErrorCode.INVALID_INPUT.name())
                .details(details)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().error(payload).build());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException exception) {
        List<ErrorDetail> details = exception.getConstraintViolations().stream()
                .map(violation -> ErrorDetail.builder()
                        .field(violation.getPropertyPath().toString())
                        .message(violation.getMessage())
                        .build())
                .toList();
        ErrorPayload payload = ErrorPayload.builder()
                .message("Validation failed")
                .code(ErrorCode.INVALID_INPUT.name())
                .details(details)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().error(payload).build());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception) {
        log.error("Unexpected error", exception);
        ErrorPayload payload = ErrorPayload.builder()
                .message("Unexpected error")
                .code(ErrorCode.INTERNAL_ERROR.name())
                .details(List.of())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().error(payload).build());
    }

    private HttpStatus toStatus(ErrorCode code) {
        return switch (code) {
            case ROOM_NOT_FOUND, BOOKING_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case DATES_UNAVAILABLE -> HttpStatus.CONFLICT;
            case INVALID_DATE_RANGE, EMAIL_MISMATCH, INVALID_INPUT -> HttpStatus.BAD_REQUEST;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
