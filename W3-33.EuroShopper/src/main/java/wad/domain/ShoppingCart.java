package wad.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart implements Serializable {

    private Map<Item, Long> items;

    public ShoppingCart() {
        this.items = new TreeMap<>();
    }

    public Map<Item, Long> getItems() {
        return items;
    }

    public void addToCart(Item item) {
        this.items.put(item, item.getId());
    }

    public void removeFromCart(Item item) {
        Long itemCount = this.items.get(item);
        itemCount--;

        if (itemCount == 0) {
            this.items.remove(item);
        } else {
            this.items.put(item, itemCount);
        }
    }
}
