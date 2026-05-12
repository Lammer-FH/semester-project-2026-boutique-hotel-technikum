package at.fhtw.hotel.persistence.repository;

import at.fhtw.hotel.persistence.entity.ExtraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaExtraRepository extends JpaRepository<ExtraEntity, Long> {
}
