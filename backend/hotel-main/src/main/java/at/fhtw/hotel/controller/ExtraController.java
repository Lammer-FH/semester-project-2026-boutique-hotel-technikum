package at.fhtw.hotel.controller;

import at.fhtw.hotel.dto.response.ExtraResponse;
import at.fhtw.hotel.model.Extra;
import at.fhtw.hotel.service.ExtraService;
import at.fhtw.hotel.util.Logger;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/extras")
public class ExtraController {

    private static final Logger log = Logger.get(ExtraController.class);

    private final ExtraService extraService;

    public ExtraController(ExtraService extraService) {
        this.extraService = extraService;
    }

    @GetMapping
    public List<ExtraResponse> listExtras() {
        List<Extra> extras = extraService.listExtras();
        log.debug("Extras listed size={}", extras.size());
        return extras.stream()
                .map(extra -> ExtraResponse.builder()
                        .id(extra.getId())
                        .code(extra.getCode())
                        .title(extra.getTitle())
                        .description(extra.getDescription())
                        .iconName(extra.getIconName())
                        .build())
                .toList();
    }
}
