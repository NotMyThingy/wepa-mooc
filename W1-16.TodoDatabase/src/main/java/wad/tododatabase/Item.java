package wad.tododatabase;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Item extends AbstractPersistable<Long> {

    private String name;
    private Integer checked;

    public Item() {
    }

    public Item(String name) {
        this.name = name;
        this.checked = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

}
