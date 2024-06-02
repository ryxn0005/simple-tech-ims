package IMS.model.components;

import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GPU extends Component {
    private final SimpleIntegerProperty vram;
    private final SimpleDoubleProperty coreClock;

    public GPU(ComponentBrand brand, String name, double price, int vram, double coreClock, int quantity) {
        super(ComponentType.GPU, brand, name, price, quantity);
        this.vram = new SimpleIntegerProperty(vram);
        this.coreClock = new SimpleDoubleProperty(coreClock);
    }

    public int getVRAM() {
        return this.vram.get();
    }

    public void setVRAM(int vram) {
        this.vram.set(vram);
    }

    public SimpleIntegerProperty vramProperty() {
        return this.vram;
    }

    public double getCoreClock() {
        return this.coreClock.get();
    }

    public void setCoreClock(double coreClock) {
        this.coreClock.set(coreClock);
    }

    public SimpleDoubleProperty coreClockProperty() {
        return this.coreClock;
    }
}