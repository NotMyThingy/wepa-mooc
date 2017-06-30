package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
