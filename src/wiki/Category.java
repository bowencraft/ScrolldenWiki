package wiki;

import java.util.*;

public class Category implements WikiComponent {
    private String name;
    private List<WikiComponent> components = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public void addComponent(WikiComponent c) {
        components.add(c);
    }

    public void removeComponent(WikiComponent c) {
        components.remove(c);
    }

    public void display() {
        System.out.println("== Category: " + name + " ==");
        for (WikiComponent c : components) {
            c.display();
        }
    }

    public int getViewCount() {
        return components.stream().mapToInt(WikiComponent::getViewCount).sum();
    }
}