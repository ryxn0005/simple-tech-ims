package IMS.controller;

import IMS.model.InventoryModel;
import IMS.model.components.Component;

// The RemoveProductController class is responsible for removing components from the inventory.
public class RemoveProductController {
    // Declare the model
    private InventoryModel invModel;

    // Constructor for the RemoveProductController class
    public RemoveProductController(InventoryModel invModel) {
        // Initialize the model
        this.invModel = invModel;
    }

    // Method to find a product in the inventory by its ID
    public Component findProduct(int id) {
        // Return the product with the given ID
        return invModel.findProductByID(id);
    }

    // Method to remove a component from the inventory
    public void removeComponent(Component c) {
        // Remove the component from the inventory
        this.invModel.removeComponent(c);

        // Update the count of the component type
        this.invModel.decrementTypeCount(c.getType());

        // Update the total number of products and the number of available products
        this.invModel.initialiseTotalProductsAndProductsAvailable();
    }
}