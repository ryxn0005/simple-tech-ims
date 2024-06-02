package IMS.model.components;

import javafx.beans.property.SimpleObjectProperty;
import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Component {
    private final SimpleObjectProperty<ComponentType> type;
    private final SimpleObjectProperty<ComponentBrand> brand;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty id;
    private static int nextId = 1;
    private final SimpleIntegerProperty quantity;

    Component(ComponentType type, ComponentBrand brand, String name, double price, int quantity) {
        this.id = new SimpleIntegerProperty(nextId++);
        this.type = new SimpleObjectProperty<>(type);
        this.brand = new SimpleObjectProperty<>(brand);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    // properties
    public ComponentType getType() {
        return this.type.get();
    }

    public void setType(ComponentType type) {
        this.type.set(type);
    }

    public SimpleObjectProperty<ComponentType> typeProperty() {
        return this.type;
    }

    public ComponentBrand getBrand() {
        return this.brand.get();
    }

    public void setBrand(ComponentBrand brand) {
        this.brand.set(brand);
    }

    public SimpleObjectProperty<ComponentBrand> brandProperty() {
        return this.brand;
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return this.name;
    }

    public double getPrice() {
        return this.price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public SimpleDoubleProperty priceProperty() {
        return this.price;
    }

    public int getId() {
        return this.id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return this.id;
    }

    public int getQuantity() {
        return this.quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public SimpleIntegerProperty quantityProperty() {
        return this.quantity;
    }
}