package IMS.view;

import IMS.controller.DashboardController;
import IMS.model.InventoryModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class DashboardView {
    // Create a new TabPane to serves as a mother view container for other views (as
    // tabs)
    private TabPane dashboardView;

    public DashboardView(DashboardController controller, InventoryModel invModel) {
        dashboardView = new TabPane();

        // Create a new tab with the title "Overview"
        Tab overviewTab = new Tab("Overview");

        // Set the content of the tab to the view returned by the openOverViewTab()
        // method of the controller
        overviewTab.setContent(controller.openOverViewTab());

        // Make the tab not closable by the user
        overviewTab.setClosable(false);

        // Create a new tab with the title "Stock Browser"
        Tab stockBrowserTab = new Tab("Stock Browser");

        // Set the content of the tab to the view returned by the openStockBrowserTab()
        // method of the controller
        stockBrowserTab.setContent(controller.openStockBrowserTab());

        // Make the tab not closable by the user
        stockBrowserTab.setClosable(false);

        // Create a new tab with the title "Add a Product"
        Tab addProductTab = new Tab("Add a Product");

        // Set the content of the tab to the view returned by the openAddProductTab()
        // method of the controller
        addProductTab.setContent(controller.openAddProductTab());

        // Make the tab not closable by the user
        addProductTab.setClosable(false);

        // Create a new tab with the title "Remove a Product"
        Tab removeProductTab = new Tab("Remove a Product");

        // Set the content of the tab to the view returned by the openRemoveProductTab()
        // method of the controller
        removeProductTab.setContent(controller.openRemoveProductTab());

        // Make the tab not closable by the user
        removeProductTab.setClosable(false);

        // Create a new tab with the title "Update Product Information"
        Tab updateProductInfo = new Tab("Update Product Information");

        // Set the content of the tab to the view returned by the
        // openUpdateProductInfoTab()
        // method of the controller
        updateProductInfo.setContent(controller.openUpdateProductInfoTab());

        // Make the tab not closable by the user
        updateProductInfo.setClosable(false);

        dashboardView.getTabs().addAll(overviewTab, stockBrowserTab, addProductTab, removeProductTab,
                updateProductInfo);
    }

    public TabPane asParent() {
        return dashboardView;
    }
}