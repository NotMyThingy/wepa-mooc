package wad.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Item;
import wad.domain.ShoppingCart;
import wad.repository.ItemRepository;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCart shoppingCart;
    @Autowired
    ItemRepository itemRepository;

    public Map<Item, Long> findAll() {
        return shoppingCart.getItems();
    }

    public void addItem(Long id) {
        Item item = itemRepository.findOne(id);
        shoppingCart.addToCart(item);
    }

}
