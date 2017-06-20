package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long>{
}
