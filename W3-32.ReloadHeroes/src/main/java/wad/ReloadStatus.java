package wad;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class ReloadStatus extends AbstractPersistable<Long> {

    private String name;
    private Integer reloads;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReloads() {
        return reloads;
    }

    public void setReloads(Integer reloads) {
        this.reloads = reloads;
    }

}
