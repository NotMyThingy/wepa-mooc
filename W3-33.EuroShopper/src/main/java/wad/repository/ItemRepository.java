package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
