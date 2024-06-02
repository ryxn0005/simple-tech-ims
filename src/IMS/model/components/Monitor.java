package IMS.model.components;

import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import IMS.model.enumerations.MonitorResolution;
import IMS.model.enumerations.ScreenSize;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Monitor extends Component {
    private final SimpleObjectProperty<ScreenSize> screenSize;
    private final SimpleObjectProperty<MonitorResolution> resolution;
    private final SimpleIntegerProperty refreshRate;

    public Monitor(ComponentBrand brand, String name, double price, ScreenSize screenSize,
            MonitorResolution resolution, int refreshRate, int quantity) {
        super(ComponentType.MONITOR, brand, name, price, quantity);
        this.screenSize = new SimpleObjectProperty<>(screenSize);
        this.resolution = new SimpleObjectProperty<>(resolution);
        this.refreshRate = new SimpleIntegerProperty(refreshRate);
    }

    public ScreenSize getScreenSize() {
        return this.screenSize.get();
    }

    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize.set(screenSize);
    }

    public SimpleObjectProperty<ScreenSize> screenSizeProperty() {
        return this.screenSize;
    }

    public MonitorResolution getResolution() {
        return this.resolution.get();
    }

    public void setResolution(MonitorResolution resolution) {
        this.resolution.set(resolution);
    }

    public SimpleObjectProperty<MonitorResolution> resolutionProperty() {
        return this.resolution;
    }

    public int getRefreshRate() {
        return this.refreshRate.get();
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate.set(refreshRate);
    }

    public SimpleIntegerProperty refreshRateProperty() {
        return this.refreshRate;
    }
}