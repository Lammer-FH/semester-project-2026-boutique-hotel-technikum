package at.fhtw.hotel.controller;

import at.fhtw.hotel.domain.model.Extra;
import at.fhtw.hotel.service.ExtraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/extras")
@Tag(name = "Extras", description = "Browse bookable extras and amenities")
public class ExtraController {

    private static final Logger log = LoggerFactory.getLogger(ExtraController.class);

    private final ExtraService extraService;

    public ExtraController(ExtraService extraService) {
        this.extraService = extraService;
    }

    @GetMapping
    @Operation(summary = "List all extras", description = "Returns all bookable extras and amenities available at the hotel.")
    @ApiResponse(responseCode = "200")
    public List<Extra> listExtras() {
        List<Extra> extras = extraService.listExtras();
        log.debug("Extras listed size={}", extras.size());
        return extras;
    }
}
