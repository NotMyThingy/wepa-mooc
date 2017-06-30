package wad.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "PRODUCT_ORDER")
public class Order extends AbstractPersistable<Long> {

    private String name;
    private String address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
