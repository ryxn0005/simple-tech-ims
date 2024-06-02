package IMS.view;

import java.util.Arrays;
import IMS.controller.OverviewController;
import IMS.model.ComponentTypeCount;
import IMS.model.InventoryModel;
import IMS.model.enumerations.ComponentType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OverviewTab {
        private TableView<ComponentTypeCount> overviewTypeCounts;
        private VBox mainContent;
        private InventoryModel invModel;
        private OverviewController controller;

        public OverviewTab(InventoryModel invModel, OverviewController controller) {
                this.invModel = invModel;
                this.controller = controller;

                createAndConfigurePane();
                createAndLayoutControls();
        }

        public Parent asParent() {
                return mainContent;
        }

        private void createAndLayoutControls() {
                // Set up an overview for total products and total products available
                Label lblTotalProducts = new Label();
                lblTotalProducts.textProperty().bind(invModel.totalProductsProperty().asString("Total products: %d"));
                Label lblInStockProducts = new Label();
                lblInStockProducts.textProperty()
                                .bind(invModel.productsAvailableProperty().asString("In-Stock products: %d"));

                HBox productsRow = new HBox(60, lblTotalProducts, lblInStockProducts);

                // Set up an overview table for the quantity of each Component Type
                this.overviewTypeCounts = new TableView<>();

                TableColumn<ComponentTypeCount, ComponentType> typeColumn = new TableColumn<>(
                                "Component Type");
                typeColumn.setCellValueFactory(
                                cellData -> cellData.getValue().typeProperty());

                TableColumn<ComponentTypeCount, Integer> countColumn = new TableColumn<>(
                                "Count");
                countColumn
                                .setCellValueFactory(
                                                cellData -> Bindings.createObjectBinding(
                                                                () -> cellData.getValue().getCount(),
                                                                cellData.getValue().countProperty()));

                overviewTypeCounts.getColumns().addAll(Arrays.asList(typeColumn, countColumn));

                // Setting the initial collumn widths
                typeColumn.prefWidthProperty().bind(overviewTypeCounts.widthProperty().multiply(0.5));
                countColumn.prefWidthProperty().bind(overviewTypeCounts.widthProperty().multiply(0.5));

                // Disable horizontal scrollbar
                overviewTypeCounts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

                VBox.setVgrow(overviewTypeCounts, Priority.ALWAYS);
                overviewTypeCounts.setMaxWidth(Double.MAX_VALUE);

                overviewTypeCounts.setItems(invModel.typeCountsProperty());

                mainContent.getChildren().addAll(productsRow, overviewTypeCounts);
        }

        private void createAndConfigurePane() {
                mainContent = new VBox(20);
                mainContent.setAlignment(Pos.TOP_CENTER);
        }
}
