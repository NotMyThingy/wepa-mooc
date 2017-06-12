/*
 */
package wad.jobs;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Job extends AbstractPersistable<Long> {

    private String name;
    private boolean done;

    public Job() {
    }

    public Job(String name) {
        this.name = name;
        this.done = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
