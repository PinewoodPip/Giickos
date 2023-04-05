package edu.ub.pis.giickos.ui.section.taskcreator;

public class TagDescriptor {
    private String id;
    private String name;

    public TagDescriptor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
