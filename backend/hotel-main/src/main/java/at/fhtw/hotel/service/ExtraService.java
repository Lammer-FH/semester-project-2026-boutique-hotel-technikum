package at.fhtw.hotel.service;

import at.fhtw.hotel.model.Extra;
import at.fhtw.hotel.repository.ExtraRepository;
import at.fhtw.hotel.util.Logger;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExtraService {

    private static final Logger log = Logger.get(ExtraService.class);

    private final ExtraRepository extraRepository;

    public ExtraService(ExtraRepository extraRepository) {
        this.extraRepository = extraRepository;
    }

    public List<Extra> listExtras() {
        log.debug("Listing extras");
        return extraRepository.findAll();
    }
}
