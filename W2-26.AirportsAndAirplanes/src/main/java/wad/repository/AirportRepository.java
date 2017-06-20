package wad.repository;

import org.springframework.data.repository.CrudRepository;
import wad.domain.Airport;

public interface AirportRepository extends CrudRepository<Airport, Long> {
}
