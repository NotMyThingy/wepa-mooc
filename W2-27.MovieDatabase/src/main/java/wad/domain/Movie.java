package wad.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Movie extends AbstractPersistable<Long> {

    @Column
    private String name;
    @Column
    private Integer lengthInMinutes;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Actor> actors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(Integer lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
