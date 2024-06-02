package IMS.model.components;

import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import javafx.beans.property.SimpleIntegerProperty;

public class RAM extends Component {
    private final SimpleIntegerProperty memory;
    private final SimpleIntegerProperty speed;

    public RAM(ComponentBrand brand, String name, double price, int memory, int speed, int quantity) {
        super(ComponentType.RAM, brand, name, price, quantity);
        this.memory = new SimpleIntegerProperty(memory);
        this.speed = new SimpleIntegerProperty(speed);
    }

    public int getMemory() {
        return this.memory.get();
    }

    public void setMemory(int memory) {
        this.memory.set(memory);
    }

    public SimpleIntegerProperty memoryProperty() {
        return this.memory;
    }

    public int getSpeed() {
        return this.speed.get();
    }

    public void setSpeed(int speed) {
        this.speed.set(speed);
    }

    public SimpleIntegerProperty speedProperty() {
        return this.speed;
    }
}
