package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
