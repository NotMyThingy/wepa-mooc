package wad.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class OrderItem extends AbstractPersistable<Long> {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;
    @Min(1)
    private Long itemCount;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
    }

}
