package wad.todoapplication;

import java.util.UUID;

public class TodoItem {

    private String identifier;
    private String name;
    private int checked;

    public TodoItem(String newName) {
        this.identifier = UUID.randomUUID().toString();
        this.name = newName;
        this.checked = 0;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

}
