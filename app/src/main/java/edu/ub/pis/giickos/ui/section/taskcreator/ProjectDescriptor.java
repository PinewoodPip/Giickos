package edu.ub.pis.giickos.ui.section.taskcreator;

// TODO move once viewmodel is implemented
public class ProjectDescriptor {
    private String id;
    private String name;

    public ProjectDescriptor(String id, String name) {
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
