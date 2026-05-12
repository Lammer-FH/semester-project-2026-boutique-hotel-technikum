package at.fhtw.hotel.persistence.repository;

import at.fhtw.hotel.mapper.ExtraMapper;
import at.fhtw.hotel.model.Extra;
import at.fhtw.hotel.repository.ExtraRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ExtraRepositoryAdapter implements ExtraRepository {

    private final JpaExtraRepository jpaExtraRepository;
    private final ExtraMapper extraMapper;

    public ExtraRepositoryAdapter(JpaExtraRepository jpaExtraRepository, ExtraMapper extraMapper) {
        this.jpaExtraRepository = jpaExtraRepository;
        this.extraMapper = extraMapper;
    }

    @Override
    public List<Extra> findAll() {
        return jpaExtraRepository.findAll().stream()
                .map(extraMapper::toDomain)
                .toList();
    }
}
