package at.fhtw.hotel.service;

import at.fhtw.hotel.domain.model.Extra;
import at.fhtw.hotel.persistence.mapper.ExtraMapper;
import at.fhtw.hotel.persistence.repository.JpaExtraRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExtraService {

    private static final Logger log = LoggerFactory.getLogger(ExtraService.class);

    private final JpaExtraRepository jpaExtraRepository;
    private final ExtraMapper extraMapper;

    public ExtraService(JpaExtraRepository jpaExtraRepository, ExtraMapper extraMapper) {
        this.jpaExtraRepository = jpaExtraRepository;
        this.extraMapper = extraMapper;
    }

    public List<Extra> listExtras() {
        log.debug("Listing extras");
        return jpaExtraRepository.findAll().stream()
                .map(extraMapper::toDomain)
                .toList();
    }
}
