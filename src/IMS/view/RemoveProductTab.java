package IMS.view;

import IMS.controller.RemoveProductController;
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
import IMS.model.enumerations.ComponentType;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RemoveProductTab {
    private Stage primaryStage;

    private VBox mainContent;

    private Label lblEnterProductID;
    private TextField productIDField;
    private Button removeBtn;

    private InventoryModel invModel;
    private RemoveProductController controller;

    public RemoveProductTab(InventoryModel invModel, RemoveProductController controller, Stage primaryStage) {
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
        lblEnterProductID = new Label("Enter Product ID (e.g. 1, 2, 3, etc):");
        productIDField = new TextField();
        configTextFieldForInts(productIDField);
        productIDField.setMaxWidth(200);
        removeBtn = new Button("Remove Product From Inventory");

        removeBtn.setOnAction(e -> {
            Component c;
            try {
                c = controller.findProduct(Integer.parseInt(productIDField.textProperty().get()));
            } catch (NumberFormatException n) {
                c = null;
            }

            if (c != null) {
                promptUserConfirmation(this.primaryStage, c);
            } else {
                productNotFoundPopUp(this.primaryStage);
            }
        });

        mainContent.getChildren().addAll(lblEnterProductID, productIDField, removeBtn);
    }

    private void createAndConfigurePane() {
        mainContent = new VBox(5);
        mainContent.setAlignment(Pos.TOP_CENTER);
    }

    private void promptUserConfirmation(Stage motherStage, Component c) {
        Stage stage = new Stage();
        stage.initOwner(motherStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmation");

        // VBox for updated product information
        VBox productInfo = new VBox(10);
        productInfo.setAlignment(Pos.CENTER);
        // Labels for the updated product information
        ComponentType type = c.getType();
        Label lblType = new Label("Type: " + type.toString());
        Label lblBrand = new Label("Brand: " + c.getBrand().toString());
        Label lblName = new Label("Name: " + c.getName());
        Label lblPrice = new Label("Price: " + String.valueOf(c.getPrice()));
        Label lblQuantity = new Label("Quantity: " + String.valueOf(c.getQuantity()));

        // Add labels to the product info VBox based on type
        productInfo.getChildren().addAll(lblType, lblBrand, lblName, lblPrice, lblQuantity);
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
        Button yesBtn = new Button("Yes");
        yesBtn.setOnAction(e -> {
            controller.removeComponent(c);
            notifyProductRemoved(this.primaryStage);
            stage.close();
        });
        Button noBtn = new Button("No");
        noBtn.setOnAction(e -> {
            stage.close();
        });

        // Button rows, main view and scene
        HBox buttonRow = new HBox(20, yesBtn, noBtn);
        buttonRow.setAlignment(Pos.CENTER);
        VBox root = new VBox(10, new Label("Do you wish to remove this product?"), productInfo, buttonRow);
        root.setAlignment(Pos.CENTER);
        Scene confirmationScene = new Scene(root, 300, 500);

        // Set the scene and show it
        stage.setScene(confirmationScene);
        stage.showAndWait();
    }

    private void productNotFoundPopUp(Stage motherStage) {
        Stage stage = new Stage();
        stage.initOwner(motherStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Error");

        // Confirmation buttons
        Button okBtn = new Button("OK");
        okBtn.setAlignment(Pos.CENTER);
        okBtn.setOnAction(e -> {
            stage.close();
        });

        // Main view and scene
        VBox root = new VBox(10, new Label("Product Not Found!"), okBtn);
        root.setAlignment(Pos.CENTER);
        Scene confirmationScene = new Scene(root, 300, 200);

        // Set the scene and show it
        stage.setScene(confirmationScene);
        stage.showAndWait();
    }

    private void notifyProductRemoved(Stage motherStage) {
        Stage stage = new Stage();
        stage.initOwner(motherStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Notification");

        // Confirmation buttons
        Button okBtn = new Button("OK");
        okBtn.setAlignment(Pos.CENTER);
        okBtn.setOnAction(e -> {
            stage.close();
        });

        // Main view and scene
        VBox root = new VBox(10, new Label("Product Removed Successfully!"), okBtn);
        root.setAlignment(Pos.CENTER);
        Scene confirmationScene = new Scene(root, 300, 200);

        // Set the scene and show it
        stage.setScene(confirmationScene);
        stage.showAndWait();
    }

    // Function to confire text fields to accept int inputs only.
    private void configTextFieldForInts(TextField field) {
        field.setTextFormatter(new TextFormatter<Integer>((Change c) -> {
            if (c.getControlNewText().matches("\\d*")) {
                return c;
            }
            return null;
        }));
    }
}
