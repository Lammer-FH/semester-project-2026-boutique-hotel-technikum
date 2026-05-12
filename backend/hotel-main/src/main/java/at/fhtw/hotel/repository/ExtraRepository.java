package at.fhtw.hotel.repository;

import at.fhtw.hotel.model.Extra;
import java.util.List;

public interface ExtraRepository {
    List<Extra> findAll();
}
