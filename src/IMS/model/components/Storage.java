package IMS.model.components;

import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import IMS.model.enumerations.StorageType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Storage extends Component {
    private final SimpleIntegerProperty capacity;
    private final SimpleObjectProperty<StorageType> storageType;

    public Storage(ComponentBrand brand, String name, double price, int capacity,
            StorageType storageType, int quantity) {
        super(ComponentType.STORAGE, brand, name, price, quantity);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.storageType = new SimpleObjectProperty<>(storageType);
    }

    public int getCapacity() {
        return this.capacity.get();
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public SimpleIntegerProperty capacityProperty() {
        return this.capacity;
    }

    public StorageType getStorageType() {
        return this.storageType.get();
    }

    public void setStorageType(StorageType storageType) {
        this.storageType.set(storageType);
    }

    public SimpleObjectProperty<StorageType> storageTypeProperty() {
        return this.storageType;
    }
}