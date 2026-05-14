package at.fhtw.hotel.controller.dto.annotation;

import at.fhtw.hotel.controller.dto.request.BookingRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailMatchValidator implements ConstraintValidator<EmailMatch, BookingRequest> {

    @Override
    public boolean isValid(BookingRequest request, ConstraintValidatorContext context) {
        if (request.getGuestEmail() == null || request.getConfirmEmail() == null) {
            return true;
        }
        return request.getGuestEmail().equalsIgnoreCase(request.getConfirmEmail());
    }
}
