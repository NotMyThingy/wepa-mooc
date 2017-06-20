package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Airport extends AbstractPersistable<Long> {

    private String identifier;
    private String name;
    @OneToMany(mappedBy = "airport", fetch = FetchType.EAGER)
    private List<Aircraft> aircrafts;

    public void addAircraft(Aircraft aircraft) {
        if (this.aircrafts == null) {
            this.aircrafts = new ArrayList<>();
        }

        this.aircrafts.add(aircraft);
    }

    /*
      * GETTER UND SETTERS 
     */
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }

}
