package IMS.model.components;

import IMS.model.enumerations.CPUChipset;
import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import IMS.model.enumerations.FormFactor;
import javafx.beans.property.SimpleObjectProperty;

public class Motherboard extends Component {
    private final SimpleObjectProperty<CPUChipset> chipset;
    private final SimpleObjectProperty<FormFactor> formFactor;

    public Motherboard(ComponentBrand brand, String name, double price, CPUChipset chipset,
            FormFactor formFactor, int quantity) {
        super(ComponentType.MOTHERBOARD, brand, name, price, quantity);
        this.chipset = new SimpleObjectProperty<>(chipset);
        this.formFactor = new SimpleObjectProperty<>(formFactor);
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

    public FormFactor getFormFactor() {
        return this.formFactor.get();
    }

    public void setFormFactor(FormFactor formFactor) {
        this.formFactor.set(formFactor);
    }

    public SimpleObjectProperty<FormFactor> formFactorProperty() {
        return this.formFactor;
    }
}