package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Aircraft;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
