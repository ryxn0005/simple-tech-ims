package IMS.controller;

import IMS.model.InventoryModel;
import IMS.view.AddProductTab;
import IMS.view.OverviewTab;
import IMS.view.RemoveProductTab;
import IMS.view.StockBrowserTab;
import IMS.view.UpdateProductInfoTab;
import javafx.scene.Parent;
import javafx.stage.Stage;

// The DashboardController class is responsible for managing the different tabs in the application.
public class DashboardController {
    // Declare the model and controllers for each tab
    InventoryModel invModel;
    OverviewController overviewController;
    AddProductController addProductController;
    RemoveProductController removeProductController;
    UpdateProductInfoController updateProductInfoController;
    StockBrowserController stockBrowserController;
    Stage primaryStage;

    // Constructor for the DashboardController class
    public DashboardController(InventoryModel invModel, Stage primaryStage) {
        // Initialize the model and controllers
        this.invModel = invModel;
        this.primaryStage = primaryStage;
        this.overviewController = new OverviewController(invModel);
        this.addProductController = new AddProductController(invModel);
        this.removeProductController = new RemoveProductController(invModel);
        this.updateProductInfoController = new UpdateProductInfoController(invModel);
        this.stockBrowserController = new StockBrowserController(invModel);
    }

    // Method to open the Overview tab
    public Parent openOverViewTab() {
        OverviewTab overViewTab = new OverviewTab(this.invModel, this.overviewController);
        return overViewTab.asParent();
    }

    // Method to open the Add Product tab
    public Parent openAddProductTab() {
        AddProductTab addProductTab = new AddProductTab(invModel, this.addProductController, this.primaryStage);
        return addProductTab.asParent();
    }

    // Method to open the Remove Product tab
    public Parent openRemoveProductTab() {
        RemoveProductTab removeProductTab = new RemoveProductTab(this.invModel, this.removeProductController,
                this.primaryStage);
        return removeProductTab.asParent();
    }

    // Method to open the Stock Browser tab
    public Parent openStockBrowserTab() {
        StockBrowserTab stockBrowserTab = new StockBrowserTab(this.invModel, this.stockBrowserController,
                this.primaryStage);
        return stockBrowserTab.asParent();
    }

    // Method to open the Update Product Information tab
    public Parent openUpdateProductInfoTab() {
        UpdateProductInfoTab updateProductInfoTab = new UpdateProductInfoTab(this.invModel,
                this.updateProductInfoController, this.primaryStage);
        return updateProductInfoTab.asParent();
    }
}