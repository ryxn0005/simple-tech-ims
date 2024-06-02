package IMS.model;

import IMS.model.enumerations.ComponentType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ComponentTypeCount {
    private final SimpleObjectProperty<ComponentType> type;
    private final SimpleIntegerProperty count;

    ComponentTypeCount(ComponentType type, int count) {
        this.type = new SimpleObjectProperty<>(type);
        this.count = new SimpleIntegerProperty(count);
    }

    // Getters and Setters
    public ComponentType getType() {
        return type.get();
    }

    public SimpleObjectProperty<ComponentType> typeProperty() {
        return this.type;
    }

    public int getCount() {
        return this.count.get();
    }

    public void setCount(int x) {
        this.count.set(x);
    }

    public SimpleIntegerProperty countProperty() {
        return this.count;
    }
}
