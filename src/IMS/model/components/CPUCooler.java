package IMS.model.components;

import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CPUCooler extends Component {
    private final SimpleIntegerProperty fanRPM;
    private final SimpleDoubleProperty noiseLevel;
    private final SimpleIntegerProperty radiatorSize;

    public CPUCooler(ComponentBrand brand, String name, double price, int fanRPM, double noiseLevel,
            int radiatorSize, int quantity) {
        super(ComponentType.CPU_COOLER, brand, name, price, quantity);
        this.fanRPM = new SimpleIntegerProperty(fanRPM);
        this.noiseLevel = new SimpleDoubleProperty(noiseLevel);
        this.radiatorSize = new SimpleIntegerProperty(radiatorSize);
    }

    public int getFanRPM() {
        return this.fanRPM.get();
    }

    public void setFanRPM(int fanRPM) {
        this.fanRPM.set(fanRPM);
    }

    public SimpleIntegerProperty fanRPMProperty() {
        return this.fanRPM;
    }

    public double getNoiseLevel() {
        return this.noiseLevel.get();
    }

    public void setNoiseLevel(double noiseLevel) {
        this.noiseLevel.set(noiseLevel);
    }

    public SimpleDoubleProperty noiseLevelProperty() {
        return this.noiseLevel;
    }

    public int getRadiatorSize() {
        return this.radiatorSize.get();
    }

    public void setRadiatorSize(int radiatorSize) {
        this.radiatorSize.set(radiatorSize);
    }

    public SimpleIntegerProperty radiatorSizeProperty() {
        return this.radiatorSize;
    }
}