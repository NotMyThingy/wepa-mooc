package wad.hellodao.domain;

public class Agent {

    private String id;
    private String name;

    public Agent() {
    }

    public Agent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.id + ")";
    }

}
