package wad.helloobjects;

import java.util.UUID;

public class Item {

    private String identifier;
    private String name;
    private String type;

    public Item(String name, String type) {
        this.identifier = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
