package wad.hellofirebase;

import java.util.UUID;

public class FirebaseItem {

    private String name;
    private String identifier;

    public FirebaseItem() {
    }

    public FirebaseItem(String name) {
        this(name, UUID.randomUUID().toString());
    }

    public FirebaseItem(String name, String identifier) {
        this.name = name;
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
