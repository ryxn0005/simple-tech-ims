package IMS.model.components;

import IMS.model.enumerations.CPUChipset;
import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CPU extends Component {
    private final SimpleIntegerProperty core;
    private final SimpleDoubleProperty coreClock;
    private final SimpleObjectProperty<CPUChipset> chipset;

    public CPU(ComponentBrand brand, String name, double price, int core, double coreClock,
            CPUChipset chipset, int quantity) {
        super(ComponentType.CPU, brand, name, price, quantity);
        this.core = new SimpleIntegerProperty(core);
        this.coreClock = new SimpleDoubleProperty(coreClock);
        this.chipset = new SimpleObjectProperty<>(chipset);
    }

    public int getCore() {
        return this.core.get();
    }

    public void setCore(int core) {
        this.core.set(core);
    }

    public SimpleIntegerProperty coreProperty() {
        return this.core;
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

    public CPUChipset getChipset() {
        return this.chipset.get();
    }

    public void setChipset(CPUChipset chipset) {
        this.chipset.set(chipset);
    }

    public SimpleObjectProperty<CPUChipset> chipsetProperty() {
        return this.chipset;
    }
}
