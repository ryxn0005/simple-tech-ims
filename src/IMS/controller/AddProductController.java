package IMS.controller;

import IMS.model.InventoryModel;
import IMS.model.components.Component;

// The AddProductController class is responsible for adding new components to the inventory.
public class AddProductController {
    // Declare the model
    private InventoryModel invModel;

    // Constructor for the AddProductController class
    public AddProductController(InventoryModel invModel) {
        // Initialize the model
        this.invModel = invModel;
    }

    // Method to add a new component to the inventory
    public void addComponent(Component c) {
        // Add the component to the inventory
        this.invModel.addComponent(c);

        // Update the count of the component type
        this.invModel.incrementTypeCount(c.getType());

        // Update the total number of products and the number of available products
        this.invModel.initialiseTotalProductsAndProductsAvailable();
    }
}