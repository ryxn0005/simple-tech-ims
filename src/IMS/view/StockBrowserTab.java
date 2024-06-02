package IMS.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import IMS.controller.StockBrowserController;
import IMS.model.InventoryModel;
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
import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import IMS.model.enumerations.StockLevel;

public class StockBrowserTab {
    private VBox mainContent;
    private HBox searchBar;
    private ComboBox<String> searchType;
    private TextField searchField;
    private TableView<Component> componentTable;
    private ComboBox<ComponentBrand> componentBrandComboBox;
    private ComboBox<ComponentType> componentTypeComboBox;
    private ComboBox<StockLevel> stockLevelComboBox;
    private Button viewInfoBtn;

    private StockBrowserController controller;
    private InventoryModel invModel;
    private Stage primaryStage;

    public StockBrowserTab(InventoryModel invModel, StockBrowserController controller, Stage primaryStage) {
        this.invModel = invModel;
        this.controller = controller;
        this.primaryStage = primaryStage;

        createAndConfigurePane();
        createAndLayoutControls();
    }

    public Parent asParent() {
        return this.mainContent;
    }

    private void createAndLayoutControls() {
        // Set up search bar items, products can be search by different type.
        this.searchType = new ComboBox<>();
        searchType.getItems().addAll("Name", "ID", "Brand", "Type", "Stock");

        componentBrandComboBox = new ComboBox<>();
        for (ComponentBrand brand : ComponentBrand.values()) {
            componentBrandComboBox.getItems().add(brand);
        }
        componentBrandComboBox.setPromptText("Select Brand");

        componentTypeComboBox = new ComboBox<>();
        for (ComponentType type : ComponentType.values()) {
            componentTypeComboBox.getItems().add(type);
        }
        componentTypeComboBox.setPromptText("Select Component Type");

        stockLevelComboBox = new ComboBox<>();
        for (StockLevel level : StockLevel.values()) {
            stockLevelComboBox.getItems().add(level);
        }
        stockLevelComboBox.setPromptText("Select Stock Level");

        searchField = new TextField();

        // Set up main table view
        this.componentTable = new TableView<>();
        TableColumn<Component, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Component, ComponentType> typeCol = new TableColumn<>("TYPE");
        typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        TableColumn<Component, ComponentBrand> brandCol = new TableColumn<>("BRAND");
        brandCol.setCellValueFactory(cellData -> cellData.getValue().brandProperty());

        TableColumn<Component, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Component, Double> priceCol = new TableColumn<>("PRICE");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        TableColumn<Component, Integer> quantityCol = new TableColumn<>("QUANTITY");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        componentTable.getColumns().addAll(List.of(idCol, typeCol, brandCol, nameCol, priceCol, quantityCol));

        // Setting the initial collumn widths
        idCol.prefWidthProperty().bind(componentTable.widthProperty().multiply(0.1)); // 10%
        typeCol.prefWidthProperty().bind(componentTable.widthProperty().multiply(0.1)); // 10%
        brandCol.prefWidthProperty().bind(componentTable.widthProperty().multiply(0.2)); // 20%
        nameCol.prefWidthProperty().bind(componentTable.widthProperty().multiply(0.3)); // 30%
        priceCol.prefWidthProperty().bind(componentTable.widthProperty().multiply(0.15)); // 15%
        quantityCol.prefWidthProperty().bind(componentTable.widthProperty().multiply(0.15)); // 15%

        // Disable horizontal scrollbar
        componentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        VBox.setVgrow(componentTable, Priority.ALWAYS);
        componentTable.setMaxWidth(Double.MAX_VALUE);

        viewInfoBtn = new Button("View Details");
        viewInfoBtn.setOnAction(e -> {
            int index = this.componentTable.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                displayProductInformation(this.primaryStage, this.componentTable.getItems(), index);
            }
        });

        componentBrandComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateTableBasedOnSearchType());
        componentTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateTableBasedOnSearchType());
        stockLevelComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateTableBasedOnSearchType());
        searchField.textProperty().addListener((obs, oldV, newVal) -> updateTableBasedOnSearchType());
        searchType.valueProperty().addListener((observedValue, oldValue, newValue) -> {
            // clear everything after a change
            mainContent.getChildren().clear();
            searchBar.getChildren().clear();
            componentTable.setItems(FXCollections.observableArrayList());
            searchField.clear();
            if ("Brand".equals(newValue)) {
                searchBar.getChildren().addAll(searchType, componentBrandComboBox, viewInfoBtn);
            } else if ("Type".equals(newValue)) {
                searchBar.getChildren().addAll(searchType, componentTypeComboBox, viewInfoBtn);
            } else if ("Stock".equals(newValue)) {
                searchBar.getChildren().addAll(searchType, stockLevelComboBox, viewInfoBtn);
            } else {
                searchBar.getChildren().addAll(searchType, searchField, viewInfoBtn);
                if ("ID".equals(newValue)) {
                    configTextFieldForInts(searchField);
                } else {
                    unconfigTextField(searchField);
                }
            }
            updateTableBasedOnSearchType();
            addToMainContent(searchBar, componentTable);
        });

        searchType.getSelectionModel().selectFirst();

    }

    private void updateTableBasedOnSearchType() {
        switch (searchType.getValue()) {
            case "ID":
                componentTable.setItems(controller.searchProductByID(searchField.getText().trim()));
                break;
            case "Name":
                componentTable.setItems(controller.searchProductByName(searchField.getText().trim()));
                break;
            case "Brand":
                if (componentBrandComboBox.getValue() != null) {
                    componentTable.setItems(controller.searchProductByBrand(componentBrandComboBox.getValue()));
                }
                break;
            case "Type":
                if (componentTypeComboBox.getValue() != null) {
                    componentTable.setItems(controller.searchProductByType(componentTypeComboBox.getValue()));
                }
                break;
            case "Stock":
                if (stockLevelComboBox.getValue() != null) {
                    componentTable.setItems(controller.searchProductByStockLevel(stockLevelComboBox.getValue()));
                }
                break;
            default:
                componentTable.setItems(FXCollections.observableArrayList()); // Clear table on undefined search type
                break;
        }

    }

    private void displayProductInformation(Stage motherStage, ObservableList<Component> list, int index) {
        Stage stage = new Stage();
        stage.initOwner(motherStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Details");

        // VBox for updated product information
        VBox productInfo = new VBox(10);
        productInfo.setAlignment(Pos.CENTER);
        Component c = list.get(index);
        // Labels for the updated product information
        ComponentType type = c.getType();
        Label lblId = new Label("ID: " + String.valueOf(c.getId()));
        Label lblType = new Label("Type: " + type.toString());
        Label lblBrand = new Label("Brand: " + c.getBrand().toString());
        Label lblName = new Label("Name: " + c.getName());
        Label lblPrice = new Label("Price: " + String.valueOf(c.getPrice()));
        Label lblQuantity = new Label("Quantity: " + String.valueOf(c.getQuantity()));

        // Add labels to the product info VBox based on type
        productInfo.getChildren().addAll(lblId, lblType, lblBrand, lblName, lblPrice, lblQuantity);
        switch (type) {
            case CPU:
                CPU cpu = (CPU) c;
                Label lblCores = new Label("Cores: " + String.valueOf(cpu.getCore()));
                Label lblClockSpeed = new Label("Clock Speed: " + String.valueOf(cpu.getCoreClock()));
                Label lblChipset = new Label("Chipset: " + cpu.getChipset().toString());
                productInfo.getChildren().addAll(lblCores, lblClockSpeed, lblChipset);
                break;
            case CPU_COOLER:
                CPUCooler cooler = (CPUCooler) c;
                Label lblFanRPM = new Label("Fan RPM: " + String.valueOf(cooler.getFanRPM()));
                Label lblNoiseLevel = new Label("Noise Level: " + String.valueOf(cooler.getNoiseLevel()));
                Label lblRadiatorSize = new Label("Radiator Size: " + String.valueOf(cooler.getRadiatorSize()));
                productInfo.getChildren().addAll(lblFanRPM, lblNoiseLevel, lblRadiatorSize);
                break;
            case MOTHERBOARD:
                Motherboard mtb = (Motherboard) c;
                Label lblMtbChipset = new Label("Chipset: " + mtb.getChipset().toString());
                Label lblFormFactor = new Label("Form Factor: " + mtb.getFormFactor().toString());
                productInfo.getChildren().addAll(lblMtbChipset, lblFormFactor);
                break;
            case RAM:
                RAM ram = (RAM) c;
                Label lblMemory = new Label("Memory: " + String.valueOf(ram.getMemory()));
                Label lblSpeed = new Label("Speed: " + String.valueOf(ram.getSpeed()));
                productInfo.getChildren().addAll(lblMemory, lblSpeed);
                break;
            case STORAGE:
                Storage strg = (Storage) c;
                Label lblCapacity = new Label("Capacity: " + String.valueOf(strg.getCapacity()));
                Label lblStorageType = new Label("Storage Type: " + strg.getStorageType().toString());
                productInfo.getChildren().addAll(lblCapacity, lblStorageType);
                break;
            case GPU:
                GPU gpu = (GPU) c;
                Label lblVRAM = new Label("VRAM: " + String.valueOf(gpu.getVRAM()));
                Label lblCoreClock = new Label("Core Clock: " + String.valueOf(gpu.getCoreClock()));
                productInfo.getChildren().addAll(lblVRAM, lblCoreClock);
                break;
            case CASE:
                PCCase pcCase = (PCCase) c;
                Label lblCaseFormFactor = new Label("Form Factor: " + pcCase.getFormFactor().toString());
                Label lblColour = new Label("Colour: " + pcCase.getCaseColour());
                productInfo.getChildren().addAll(lblCaseFormFactor, lblColour);
                break;
            case POWER_SUPPLY:
                PowerSupply ps = (PowerSupply) c;
                Label lblWattage = new Label("Wattage: " + String.valueOf(ps.getWattage()));
                Label lblEfficiencyRating = new Label("Efficiency Rating: " + ps.getEfficiencyRating().toString());
                productInfo.getChildren().addAll(lblWattage, lblEfficiencyRating);
                break;
            case MONITOR:
                Monitor monitor = (Monitor) c;
                Label lblScreenSize = new Label("Screen Size: " + monitor.getScreenSize().toString());
                Label lblResolution = new Label("Resolution: " + monitor.getResolution().toString());
                Label lblRefreshRate = new Label("Refresh Rate: " + String.valueOf(monitor.getRefreshRate()));
                productInfo.getChildren().addAll(lblScreenSize, lblResolution, lblRefreshRate);
                break;
            default:
                break;
        }

        // Confirmation buttons
        Button okBtn = new Button("OK");
        okBtn.setAlignment(Pos.CENTER);
        okBtn.setOnAction(e -> {
            stage.close();
        });

        // Main view and scene
        VBox root = new VBox(10, new Label("PRODUCT INFORMATION:"), productInfo, okBtn);
        root.setAlignment(Pos.CENTER);
        Scene confirmationScene = new Scene(root, 300, 400);

        // Set the scene and show it
        stage.setScene(confirmationScene);
        stage.show();
    }

    private void addToMainContent(HBox searchBar, TableView<Component> componentTable) {
        Label instruction = new Label("Click \"View Details\" to know more about the product that you are selecting.");
        mainContent.getChildren().addAll(searchBar, instruction, componentTable);
    }

    private void createAndConfigurePane() {
        mainContent = new VBox(5);
        mainContent.setAlignment(Pos.TOP_CENTER);
        searchBar = new HBox(5);
        searchBar.setAlignment(Pos.CENTER);
    }

    private void configTextFieldForInts(TextField field) {
        field.setTextFormatter(new TextFormatter<Integer>((Change c) -> {
            if (c.getControlNewText().matches("\\d*")) {
                return c;
            }
            return null;
        }));
    }

    private void unconfigTextField(TextField field) {
        field.setTextFormatter(null);
    }
}
