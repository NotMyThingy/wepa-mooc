package wad.tododatabase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemReposity extends JpaRepository<Item, Long> {
    
}
