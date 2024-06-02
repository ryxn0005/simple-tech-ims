package IMS.model.components;

import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import IMS.model.enumerations.EfficiencyRating;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PowerSupply extends Component {
    private final SimpleIntegerProperty wattage;
    private final SimpleObjectProperty<EfficiencyRating> efficiencyRating;

    public PowerSupply(ComponentBrand brand, String name, double price, int wattage,
            EfficiencyRating efficiencyRating, int quantity) {
        super(ComponentType.POWER_SUPPLY, brand, name, price, quantity);
        this.wattage = new SimpleIntegerProperty(wattage);
        this.efficiencyRating = new SimpleObjectProperty<>(efficiencyRating);
    }

    public int getWattage() {
        return this.wattage.get();
    }

    public void setWattage(int wattage) {
        this.wattage.set(wattage);
    }

    public SimpleIntegerProperty wattageProperty() {
        return this.wattage;
    }

    public EfficiencyRating getEfficiencyRating() {
        return this.efficiencyRating.get();
    }

    public void setEfficiencyRating(EfficiencyRating efficiencyRating) {
        this.efficiencyRating.set(efficiencyRating);
    }

    public SimpleObjectProperty<EfficiencyRating> efficiencyRatingProperty() {
        return this.efficiencyRating;
    }
}