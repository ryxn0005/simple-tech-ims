package IMS.model;

import java.util.Arrays;

import IMS.model.components.CPU;
import IMS.model.components.CPUCooler;
import IMS.model.components.Component;
import IMS.model.components.GPU;
import IMS.model.components.Monitor;
import IMS.model.components.Motherboard;
import IMS.model.components.PCCase;
import IMS.model.components.PowerSupply;
import IMS.model.components.RAM;
import IMS.model.components.Storage;
import IMS.model.enumerations.CPUChipset;
import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import IMS.model.enumerations.EfficiencyRating;
import IMS.model.enumerations.FormFactor;
import IMS.model.enumerations.MonitorResolution;
import IMS.model.enumerations.ScreenSize;
import IMS.model.enumerations.StockLevel;
import IMS.model.enumerations.StorageType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

interface InventoryManageable {
    void addComponent(Component c);

    void removeComponent(Component c);
}

interface SearchAble {
    ObservableList<Component> searchProduct(int id);

    ObservableList<Component> searchProduct(String name);

    ObservableList<Component> searchProduct(ComponentBrand brand);

    ObservableList<Component> searchProduct(ComponentType type);

    ObservableList<Component> seachProduct(StockLevel level);
}

public class InventoryModel implements InventoryManageable, SearchAble {
    private final ObservableList<ComponentTypeCount> typeCounts;
    private final ObservableList<Component> matchingSearchResults;
    private final SimpleIntegerProperty totalProducts;
    private final SimpleIntegerProperty productsAvailable;

    private final ObservableList<Component> products = FXCollections.observableArrayList(
            Arrays.asList(new GPU(ComponentBrand.EVGA, "GeForce RTX 2080 Ti", 500, 11, 1350, 20),
                    new GPU(ComponentBrand.SAPPHIRE, "Radeon RX 5700 XT", 400, 8, 1900, 15),
                    new GPU(ComponentBrand.ASUS, "ROG Strix GeForce RTX 2080 Super", 800, 8, 1650, 15),
                    new GPU(ComponentBrand.SAPPHIRE, "Radeon RX 5700", 350, 8, 1750, 15),
                    new GPU(ComponentBrand.ASUS, "ROG Strix GeForce RTX 2070 Super", 600, 8, 1605, 15),
                    new GPU(ComponentBrand.SAPPHIRE, "Radeon RX 5600 XT", 300, 6, 1375, 15),
                    new GPU(ComponentBrand.EVGA, "GeForce RTX 2080 Super", 700, 8, 1650, 15),
                    new GPU(ComponentBrand.EVGA, "GeForce RTX 2070 Super", 600, 8, 1605, 15),
                    new GPU(ComponentBrand.EVGA, "GeForce RTX 2060 Super", 500, 8, 1470, 15),
                    new GPU(ComponentBrand.EVGA, "GeForce GTX 1660 Ti", 300, 6, 1500, 0),
                    new GPU(ComponentBrand.EVGA, "GeForce GTX 1650 Super", 200, 4, 1530, 15),
                    new CPU(ComponentBrand.INTEL, "Core i9-12900K", 600, 16, 5, CPUChipset.LGA1700, 0),
                    new CPU(ComponentBrand.INTEL, "Core i7-12700K", 500, 12, 4.7, CPUChipset.LGA1700, 10),
                    new CPU(ComponentBrand.INTEL, "Core i5-12600K", 400, 10, 4.5, CPUChipset.LGA1700, 0),
                    new CPU(ComponentBrand.AMD, "Ryzen 9 5950X", 800, 16, 4.9, CPUChipset.AM4, 10),
                    new CPU(ComponentBrand.AMD, "Ryzen 9 5900X", 550, 12, 4.8, CPUChipset.AM4, 6),
                    new CPU(ComponentBrand.AMD, "Ryzen 7 5800X", 450, 8, 4.7, CPUChipset.AM4, 9),
                    new CPU(ComponentBrand.AMD, "Ryzen 5 5600X", 300, 6, 4.6, CPUChipset.AM4, 2),
                    new CPU(ComponentBrand.AMD, "Ryzen 5 3600", 200, 6, 4.2, CPUChipset.AM4, 0),
                    new RAM(ComponentBrand.G_SKILL, "Trident Z RGB", 200, 16, 3200, 0),
                    new RAM(ComponentBrand.CORSAIR, "Vengeance LPX", 100, 8, 2400, 20),
                    new Motherboard(ComponentBrand.ASUS, "ROG Strix Z690-E", 300, CPUChipset.LGA1700,
                            FormFactor.ATX, 10),
                    new Motherboard(ComponentBrand.GIGABYTE, "B550 AORUS PRO", 200, CPUChipset.AM4,
                            FormFactor.ATX,
                            10),
                    new PCCase(ComponentBrand.NZXT, "H510", 100, FormFactor.ATX, "Black", 20),
                    new PCCase(ComponentBrand.FRACTAL_DESIGN, "Meshify C", 120, FormFactor.MATX, "White", 0),
                    new Monitor(ComponentBrand.BENQ, "ZOWIE XL2411P", 200, ScreenSize.INCH_24,
                            MonitorResolution.FHD, 144, 10),
                    new Monitor(ComponentBrand.LG, "27GL850-B", 400, ScreenSize.INCH_27, MonitorResolution.QHD,
                            144,
                            10),
                    new CPUCooler(ComponentBrand.NOCTUA, "NH-D15", 100, 1500, 24, 140, 10),
                    new CPUCooler(ComponentBrand.CORSAIR, "H100i RGB", 150, 2400, 30, 240, 10),
                    new Storage(ComponentBrand.WESTERN_DIGITAL, "WD Blue", 50, 500, StorageType.SSD, 20),
                    new Storage(ComponentBrand.SEAGATE, "Barracuda", 40, 1000, StorageType.HDD, 0),
                    new PowerSupply(ComponentBrand.CORSAIR, "RM750x", 100, 750, EfficiencyRating.GOLD, 10),
                    new PowerSupply(ComponentBrand.CORSAIR, "RM850x", 120, 850, EfficiencyRating.PLATINUM, 10),
                    new PowerSupply(ComponentBrand.CORSAIR, "RM1000x", 150, 1000, EfficiencyRating.TITANIUM,
                            10),
                    new PowerSupply(ComponentBrand.CORSAIR, "RM650x", 80, 650, EfficiencyRating.SILVER, 10),
                    new PowerSupply(ComponentBrand.CORSAIR, "RM450x", 60, 450, EfficiencyRating.BRONZE, 10)));

    public InventoryModel() {
        this.typeCounts = FXCollections.observableArrayList();
        this.matchingSearchResults = FXCollections.observableArrayList();
        this.totalProducts = new SimpleIntegerProperty();
        this.productsAvailable = new SimpleIntegerProperty();
        initialiseTotalProductsAndProductsAvailable();
        initialiseTypeCounts();

    }

    private void initialiseTypeCounts() {
        for (ComponentType type : ComponentType.values()) {
            typeCounts.add(new ComponentTypeCount(type, getProductCount(type)));
        }
    }

    public void initialiseTotalProductsAndProductsAvailable() {
        this.totalProducts.set(this.products.size());
        int count = 0;
        for (Component c : this.products) {
            if (c.getQuantity() > 0) {
                count += 1;
            }
        }
        this.productsAvailable.set(count);
    }

    private int getProductCount(ComponentType type) {
        int count = 0;
        for (Component c : this.products) {
            if (c.getType().equals(type)) {
                count += 1;
            }
        }
        return count;
    }

    public void incrementTypeCount(ComponentType type) {
        for (ComponentTypeCount record : this.typeCounts) {
            int initialCount = record.getCount();
            if (record.getType().equals(type)) {
                record.setCount(initialCount + 1);
            }
        }
    }

    public void decrementTypeCount(ComponentType type) {
        for (ComponentTypeCount record : this.typeCounts) {
            int initialCount = record.getCount();
            if (record.getType().equals(type) && record.getCount() > 0) {
                record.setCount(initialCount - 1);
            }
        }
    }

    public ObservableList<ComponentTypeCount> typeCountsProperty() {
        return this.typeCounts;
    }

    public ObservableList<Component> productsProperty() {
        return this.products;
    }

    public SimpleIntegerProperty totalProductsProperty() {
        return totalProducts;
    }

    public SimpleIntegerProperty productsAvailableProperty() {
        return productsAvailable;
    }

    public Component findProductByID(int id) {
        Component foundComponent = null;

        for (Component c : this.products) {
            if (c.getId() == id) {
                foundComponent = c;
            }
        }

        return foundComponent;
    }

    @Override
    public void addComponent(Component c) {
        this.products.add(c);
    }

    @Override
    public void removeComponent(Component c) {
        this.products.remove(c);
        decrementTypeCount(c.getType());
        initialiseTotalProductsAndProductsAvailable();
    }

    @Override
    public ObservableList<Component> searchProduct(int id) {
        // Clear the previous seach result
        this.matchingSearchResults.clear();

        // Find data matching the criteria in the inventory
        for (Component c : this.products) {
            if (c.getId() == id) {
                this.matchingSearchResults.add(c);
            }
        }
        return this.matchingSearchResults;
    }

    @Override
    public ObservableList<Component> searchProduct(String name) {
        // Clear the previous seach result
        this.matchingSearchResults.clear();

        // Find data matching the criteria in the inventory
        for (Component c : this.products) {
            if (c.getName().toLowerCase().contains(name.toLowerCase())
                    || name.toLowerCase().contains(c.getName().toLowerCase())) {
                this.matchingSearchResults.add(c);
            } else if (c.getBrand().getName().toLowerCase().contains(name.toLowerCase())
                    || name.toLowerCase().contains(c.getBrand().getName().toLowerCase())) {
                this.matchingSearchResults.add(c);
            }
        }
        return this.matchingSearchResults;
    }

    @Override
    public ObservableList<Component> searchProduct(ComponentBrand brand) {
        // Clear the previous seach result
        this.matchingSearchResults.clear();

        // Find data matching the criteria in the inventory
        for (Component c : this.products) {
            if (c.getBrand().equals(brand)) {
                this.matchingSearchResults.add(c);
            }
        }

        return this.matchingSearchResults;
    }

    @Override
    public ObservableList<Component> searchProduct(ComponentType type) {
        // Clear the previous seach result
        this.matchingSearchResults.clear();

        // Find data matching the criteria in the inventory
        for (Component c : this.products) {
            if (c.getType().equals(type)) {
                this.matchingSearchResults.add(c);
            }
        }

        return this.matchingSearchResults;
    }

    @Override
    public ObservableList<Component> seachProduct(StockLevel level) {
        // Clear the previous seach result
        this.matchingSearchResults.clear();

        // Find data matching the criteria in the inventory
        if (StockLevel.IN_STOCK.equals(level)) {
            for (Component c : this.products) {
                if (c.getQuantity() > 0) {
                    this.matchingSearchResults.add(c);
                }
            }
        } else {
            for (Component c : this.products) {
                if (c.getQuantity() == 0) {
                    this.matchingSearchResults.add(c);
                }
            }
        }

        return this.matchingSearchResults;
    }

}