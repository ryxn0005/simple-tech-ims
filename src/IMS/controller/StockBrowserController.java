package IMS.controller;

import IMS.model.InventoryModel;
import IMS.model.components.Component;
import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import IMS.model.enumerations.StockLevel;
import javafx.collections.ObservableList;

// The StockBrowserController class is responsible for searching components in the inventory.
public class StockBrowserController {
    // Declare the model
    private InventoryModel invModel;

    // Constructor for the StockBrowserController class
    public StockBrowserController(InventoryModel invModel) {
        // Initialize the model
        this.invModel = invModel;
    }

    // Method to search a product in the inventory by its ID
    public ObservableList<Component> searchProductByID(String id) {
        // Convert the ID from string to integer and search the product
        return this.invModel.searchProduct(convertStringToInt(id));
    }

    // Method to search a product in the inventory by its name
    public ObservableList<Component> searchProductByName(String name) {
        // Search the product by name
        return this.invModel.searchProduct(name);
    }

    // Method to search a product in the inventory by its type
    public ObservableList<Component> searchProductByType(ComponentType type) {
        // Search the product by type
        return this.invModel.searchProduct(type);
    }

    // Method to search a product in the inventory by its brand
    public ObservableList<Component> searchProductByBrand(ComponentBrand brand) {
        // Search the product by brand
        return this.invModel.searchProduct(brand);
    }

    // Method to search a product in the inventory by its stock level
    public ObservableList<Component> searchProductByStockLevel(StockLevel level) {
        // Search the product by stock level
        return this.invModel.seachProduct(level);
    }

    // Private method to convert a string to an integer
    private int convertStringToInt(String s) {
        // If the string is null or empty, return 0
        if (s == null || s.isEmpty()) {
            return 0;
        }
        // If the string is "-", return 0
        if ("-".equals(s)) {
            return 0;
        }
        // Convert the string to an integer and return it
        return Integer.parseInt(s);
    }
}