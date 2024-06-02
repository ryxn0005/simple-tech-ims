package IMS.view;

import IMS.controller.UpdateProductInfoController;
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
import IMS.model.enumerations.CPUChipset;
import IMS.model.enumerations.ComponentType;
import IMS.model.enumerations.EfficiencyRating;
import IMS.model.enumerations.FormFactor;
import IMS.model.enumerations.MonitorResolution;
import IMS.model.enumerations.ScreenSize;
import IMS.model.enumerations.StorageType;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateProductInfoTab {
    private Stage primaryStage;

    // Create a main container for all javafx view components
    private VBox mainContent;
    // Containers for specific types of components' details
    private VBox productInfo, cpuDetailsBox, cpuCoolerDetailsBox, motherboardDetailsBox,
            ramDetailsBox, storageDetailsBox, gpuDetailsBox, caseDetailsBox, powerSupplyDetailsBox, monitorDetailsBox;

    // Labels for generic component attributes
    private Label lblComponentId, lblComponentType, lblComponentBrand, lblComponentName, lblComponentPrice,
            lblComponentQuantity;

    // Fields for component attributes
    private TextField componentPriceField, componentQuantityField, coreCountField, coreClockField, fanRPMField,
            noiseLevelField, radiatorSizeField, ramMemoryField, ramSpeedField, storageCapacityField, gpuVRAMField,
            gpuCoreClockField, wattageField, pcCaseColourField, monitorRefreshRateField;

    // Dropdowns for selecting specific component attributes
    private ComboBox<CPUChipset> chipsetComboBox;
    private ComboBox<StorageType> storageTypeComboBox;
    private ComboBox<EfficiencyRating> powerSupplyEffRatingComboBox;
    private ComboBox<ScreenSize> screenSizeComboBox;
    private ComboBox<MonitorResolution> monitorResolutionComboBox;
    private ComboBox<FormFactor> formFactorComboBox;

    // UI elements for product ID and update operation
    private Label lblEnterProductID;
    private TextField productIDField;
    private Button updateBtn;

    private InventoryModel invModel;
    private UpdateProductInfoController controller;

    // Constructor to initialize the UpdateProductInfoTab.
    // parameters:
    // invModel: the inventory model
    // controller: the controller managing updates
    // primaryStage: the primary stage of the application
    public UpdateProductInfoTab(InventoryModel invModel, UpdateProductInfoController controller, Stage primaryStage) {
        this.invModel = invModel;
        this.controller = controller;
        this.primaryStage = primaryStage;

        createAndConfigurePane();
        createAndLayoutControls();
    }

    // Converts this tab into a Parent node and return the main content as a Parent
    // node
    public Parent asParent() {
        return this.mainContent;
    }

    // Sets up initial layout and UI elements.
    private void createAndLayoutControls() {
        // Product ID entry
        lblEnterProductID = new Label("Enter Product ID (e.g. 1, 2, 3, etc):");
        productIDField = new TextField();
        configTextFieldForInts(productIDField);
        productIDField.setMaxWidth(200);
        updateBtn = new Button("Update Product Information");

        updateBtn.setOnAction(e -> {
            try {
                productEditorPopUp(primaryStage, productIDField.getText());
            } catch (NullPointerException ex) {
                productNotFoundPopUp(primaryStage);
            }
        });

        mainContent.getChildren().addAll(lblEnterProductID, productIDField, updateBtn);
    }

    // Displays a pop-up window for editing product information.
    // motherStage: the parent stage to lock to, and productID: the id of the
    // component being
    // edited
    private void productEditorPopUp(Stage motherStage, String productId) throws NullPointerException {
        Stage stage = new Stage();
        stage.initOwner(motherStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Product Editor");

        Component newComponent = controller.findProduct(productId);

        // Product details setup
        showComponentFields(newComponent);

        // Confirmation buttons
        Button yesBtn = new Button("Update");
        yesBtn.setOnAction(e -> {
            try {
                updateProduct(newComponent);
                notifyProductChanged(motherStage, newComponent);
                stage.close();
            } catch (NumberFormatException | NullPointerException ex) {
                notEnoughInformationAlert(motherStage);
            }
        });

        Button revertBtn = new Button("Revert");
        revertBtn.setOnAction(e -> {
            revertChanges(newComponent);
        });

        Button noBtn = new Button("Cancel");
        noBtn.setOnAction(e -> {
            stage.close();
        });

        // Button rows, main view and scene
        HBox buttonRow = new HBox(20, yesBtn, revertBtn, noBtn);
        buttonRow.setAlignment(Pos.CENTER);
        VBox root = new VBox(10, new Label("Edit Product Information:"), productInfo, buttonRow);
        root.setAlignment(Pos.CENTER);
        Scene editorScene = new Scene(root, 300, 500);

        // Set the scene and show it
        stage.setScene(editorScene);
        stage.showAndWait();
    }

    // Function to updates product information based on user input.
    // parameter c: the component being edited
    // throws 2 exeptions for the main product editor to handle
    private void updateProduct(Component component) throws NumberFormatException, NullPointerException {
        controller.updateGeneralInformation(component, componentPriceField.getText(), componentQuantityField.getText());

        switch (component.getType()) {
            case CPU:
                CPU cpu = (CPU) component;
                controller.updateCPU(cpu, coreCountField.getText(), coreClockField.getText(),
                        chipsetComboBox.getValue());
                break;
            case CPU_COOLER:
                CPUCooler cooler = (CPUCooler) component;
                controller.updateCooler(cooler, fanRPMField.getText(), noiseLevelField.getText(),
                        radiatorSizeField.getText());
                break;
            case MOTHERBOARD:
                Motherboard mtb = (Motherboard) component;
                controller.updateMotherboard(mtb, chipsetComboBox.getValue(), formFactorComboBox.getValue());
                break;
            case RAM:
                RAM ram = (RAM) component;
                controller.updateRAM(ram, ramMemoryField.getText(), ramSpeedField.getText());
                break;
            case STORAGE:
                Storage strg = (Storage) component;
                controller.updateStorage(strg, storageCapacityField.getText(), storageTypeComboBox.getValue());
                break;
            case GPU:
                GPU gpu = (GPU) component;
                controller.updateGPU(gpu, gpuVRAMField.getText(), gpuCoreClockField.getText());
                break;
            case CASE:
                PCCase pcCase = (PCCase) component;
                controller.updateCase(pcCase, formFactorComboBox.getValue(), pcCaseColourField.getText());
                break;
            case POWER_SUPPLY:
                PowerSupply ps = (PowerSupply) component;
                controller.updatePowerSupply(ps, wattageField.getText(), powerSupplyEffRatingComboBox.getValue());
                break;
            case MONITOR:
                Monitor monitor = (Monitor) component;
                controller.updateMonitor(monitor, screenSizeComboBox.getValue(), monitorResolutionComboBox.getValue(),
                        monitorRefreshRateField.getText());
                break;
            default:
                break;
        }

    }

    // Displays fields for component attributes based on its type.
    // type: the type of component
    // c: the component
    private void showComponentFields(Component component) {
        // Clear the box of previous search querry.
        productInfo.getChildren().clear();

        lblComponentId = new Label("ID: " + component.idProperty().get());
        lblComponentType = new Label("Type: " + component.typeProperty().get().toString());
        lblComponentBrand = new Label("Brand: " + component.brandProperty().get().getName());
        lblComponentName = new Label("Name: " + component.nameProperty().get());
        lblComponentPrice = new Label("Price:");
        componentPriceField = new TextField(String.valueOf(component.getPrice()));
        configTextFieldForDoubles(componentPriceField);
        lblComponentQuantity = new Label("Quantity:");
        componentQuantityField = new TextField(String.valueOf(component.getQuantity()));
        configTextFieldForInts(componentQuantityField);

        // Set max width for fields
        componentPriceField.setMaxWidth(200);
        componentQuantityField.setMaxWidth(200);

        // Based on the type, setup and add the relevant detail box
        switch (component.getType()) {
            case CPU:
                showCpuFields(component);
                break;
            case CPU_COOLER:
                showCpuCoolerFields(component);
                break;
            case MOTHERBOARD:
                showMotherboardFields(component);
                break;
            case RAM:
                showRamFields(component);
                break;
            case STORAGE:
                showStorageFields(component);
                break;
            case GPU:
                showGpuFields(component);
                break;
            case CASE:
                showPCCaseFields(component);
                break;
            case POWER_SUPPLY:
                showPowerSupplyFields(component);
                break;
            case MONITOR:
                showMonitorFields(component);
                break;
            default:
                break;
        }
    }

    private void setUpProductBox(VBox typeBox) {
        productInfo.getChildren().addAll(lblComponentId, lblComponentType,
                lblComponentBrand, lblComponentName,
                lblComponentPrice, componentPriceField, lblComponentQuantity,
                componentQuantityField, typeBox);
    }

    // Function to display fields related to CPU
    private void showCpuFields(Component c) {
        CPU cpu = (CPU) c;
        // Create CPU-specific labels and text fields
        Label lblCPUCore = new Label("CPU Core Count:");
        coreCountField = new TextField(String.valueOf(cpu.getCore()));
        configTextFieldForInts(coreCountField);
        coreCountField.setMaxWidth(200);

        Label lblCPUClock = new Label("CPU Core Clock");
        coreClockField = new TextField(String.valueOf(cpu.getCoreClock()));
        configTextFieldForDoubles(coreClockField);
        coreClockField.setMaxWidth(200);

        Label lblChipset = new Label("Chipset:");
        chipsetComboBox = new ComboBox<>();
        for (CPUChipset type : CPUChipset.values()) {
            chipsetComboBox.getItems().add(type);
        }
        chipsetComboBox.getSelectionModel().select(cpu.getChipset());
        chipsetComboBox.setMaxWidth(200);
        chipsetComboBox.getSelectionModel().select(cpu.getChipset());

        // Add CPU-specific fields to the VBox
        cpuDetailsBox.getChildren().clear();
        cpuDetailsBox.getChildren().addAll(lblCPUCore, coreCountField, lblCPUClock, coreClockField, lblChipset,
                chipsetComboBox);
        setUpProductBox(cpuDetailsBox);
    }

    // Function to display fields related to CPU Cooler
    private void showCpuCoolerFields(Component c) {
        CPUCooler cooler = (CPUCooler) c;
        // Create CPU-Cooler-specific labels and text fields
        Label lblCoolerFanRPM = new Label("Fan RPM:");
        fanRPMField = new TextField(String.valueOf(cooler.getFanRPM()));
        configTextFieldForInts(fanRPMField);
        fanRPMField.setMaxWidth(200);

        Label lblCoolerNoiseLevel = new Label("Noise Level:");
        noiseLevelField = new TextField(String.valueOf(cooler.getNoiseLevel()));
        configTextFieldForDoubles(noiseLevelField);
        noiseLevelField.setMaxWidth(200);

        Label lblCoolerRadiatorSize = new Label("Radiator Size:");
        radiatorSizeField = new TextField(String.valueOf(cooler.getRadiatorSize()));
        configTextFieldForInts(radiatorSizeField);
        radiatorSizeField.setMaxWidth(200);

        // Add CPU-Cooler-specific fields to the VBox
        cpuCoolerDetailsBox.getChildren().clear();
        cpuCoolerDetailsBox.getChildren().addAll(lblCoolerFanRPM, fanRPMField, lblCoolerNoiseLevel, noiseLevelField,
                lblCoolerRadiatorSize, radiatorSizeField);
        setUpProductBox(cpuCoolerDetailsBox);
    }

    // Function to display fields related to Motherboard
    private void showMotherboardFields(Component c) {
        Motherboard mtb = (Motherboard) c;
        // Create Motherboard-specific labels and text fields
        Label lblChipset = new Label("Chipset:");
        chipsetComboBox = new ComboBox<>();
        for (CPUChipset type : CPUChipset.values()) {
            chipsetComboBox.getItems().add(type);
        }
        chipsetComboBox.getSelectionModel().select(mtb.getChipset());
        chipsetComboBox.setMaxWidth(200);

        Label lblFormFactor = new Label("Form Factor:");
        formFactorComboBox = new ComboBox<>();
        for (FormFactor type : FormFactor.values()) {
            formFactorComboBox.getItems().add(type);
        }
        formFactorComboBox.getSelectionModel().select(mtb.getFormFactor());
        formFactorComboBox.setMaxWidth(200);

        // Add Motherboard-specific fields to the VBox
        motherboardDetailsBox.getChildren().clear();
        motherboardDetailsBox.getChildren().addAll(lblChipset, chipsetComboBox, lblFormFactor, formFactorComboBox);
        setUpProductBox(motherboardDetailsBox);
    }

    // Function to display fields related to RAM
    private void showRamFields(Component c) {
        RAM ram = (RAM) c;
        // Create RAM-specific labels and text fields
        Label lblRAMMemory = new Label("Memory:");
        ramMemoryField = new TextField(String.valueOf(ram.getMemory()));
        configTextFieldForInts(ramMemoryField);
        ramMemoryField.setMaxWidth(200);

        Label lblRAMSpeed = new Label("Speed:");
        ramSpeedField = new TextField(String.valueOf(ram.getSpeed()));
        configTextFieldForInts(ramSpeedField);
        ramSpeedField.setMaxWidth(200);

        // Add RAM-specific fields to the VBox
        ramDetailsBox.getChildren().clear();
        ramDetailsBox.getChildren().addAll(lblRAMMemory, ramMemoryField, lblRAMSpeed, ramSpeedField);
        setUpProductBox(ramDetailsBox);
    }

    // Function to display fields related to Storage
    private void showStorageFields(Component c) {
        Storage strg = (Storage) c;
        // Create Storage-specific labels and text fields
        Label lblStorageCapacity = new Label("Capacity:");
        storageCapacityField = new TextField(String.valueOf(strg.getCapacity()));
        configTextFieldForInts(storageCapacityField);
        storageCapacityField.setMaxWidth(200);

        Label lblStorageType = new Label("Storage Type:");
        storageTypeComboBox = new ComboBox<>();
        for (StorageType type : StorageType.values()) {
            storageTypeComboBox.getItems().add(type);
        }
        storageTypeComboBox.getSelectionModel().select(strg.getStorageType());
        storageTypeComboBox.setMaxWidth(200);

        // Add Storage-specific fields to the VBox
        storageDetailsBox.getChildren().clear();
        storageDetailsBox.getChildren().addAll(lblStorageCapacity, storageCapacityField, lblStorageType,
                storageTypeComboBox);
        setUpProductBox(storageDetailsBox);
    }

    // Function to display fields related to GPU
    private void showGpuFields(Component c) {
        GPU gpu = (GPU) c;
        // Create GPU-specific labels and text fields
        Label lblGPUVRAM = new Label("VRAM:");
        gpuVRAMField = new TextField(String.valueOf(gpu.getVRAM()));
        configTextFieldForInts(gpuVRAMField);
        gpuVRAMField.setMaxWidth(200);

        Label lblGPUCoreClock = new Label("GPU Core Clock:");
        gpuCoreClockField = new TextField(String.valueOf(gpu.getCoreClock()));
        configTextFieldForDoubles(gpuCoreClockField);
        gpuCoreClockField.setMaxWidth(200);

        // Add GPU-specific fields to the VBox
        gpuDetailsBox.getChildren().clear();
        gpuDetailsBox.getChildren().addAll(lblGPUVRAM, gpuVRAMField, lblGPUCoreClock, gpuCoreClockField);
        setUpProductBox(gpuDetailsBox);
    }

    // Function to display fields related to PCCASE
    private void showPCCaseFields(Component c) {
        PCCase pcCase = (PCCase) c;
        // Create PCCase-specific labels and text fields
        Label lblFormFactor = new Label("Form Factor:");
        formFactorComboBox = new ComboBox<>();
        for (FormFactor type : FormFactor.values()) {
            formFactorComboBox.getItems().add(type);
        }
        formFactorComboBox.getSelectionModel().select(pcCase.getFormFactor());
        formFactorComboBox.setMaxWidth(200);

        Label lblPCCaseColour = new Label("Case Colour:");
        pcCaseColourField = new TextField(String.valueOf(pcCase.getCaseColour()));
        pcCaseColourField.setMaxWidth(200);

        // Add PCCase-specific fields to the VBox
        caseDetailsBox.getChildren().clear();
        caseDetailsBox.getChildren().addAll(lblFormFactor, formFactorComboBox, lblPCCaseColour, pcCaseColourField);
        setUpProductBox(caseDetailsBox);
    }

    // Function to display fields related to Power Supply
    private void showPowerSupplyFields(Component c) {
        PowerSupply ps = (PowerSupply) c;
        // Create PowerSupply-specific labels and text fields
        Label lblPowerSupplyWattage = new Label("Wattage");
        wattageField = new TextField(String.valueOf(ps.getWattage()));
        configTextFieldForInts(wattageField);
        wattageField.setMaxWidth(200);

        Label lblPowerSupplyEffRating = new Label("Efficiency Rating");
        powerSupplyEffRatingComboBox = new ComboBox<>();
        for (EfficiencyRating type : EfficiencyRating.values()) {
            powerSupplyEffRatingComboBox.getItems().add(type);
        }
        powerSupplyEffRatingComboBox.getSelectionModel().select(ps.getEfficiencyRating());
        powerSupplyEffRatingComboBox.setMaxWidth(200);

        // Add PowerSupply-specific fields to the VBox
        powerSupplyDetailsBox.getChildren().clear();
        powerSupplyDetailsBox.getChildren().addAll(lblPowerSupplyWattage, wattageField, lblPowerSupplyEffRating,
                powerSupplyEffRatingComboBox);
        setUpProductBox(powerSupplyDetailsBox);
    }

    // Function to display fields related to Monitor
    private void showMonitorFields(Component c) {
        Monitor monitor = (Monitor) c;
        // Create Monitor-specific labels and text fields
        Label lblMonitorScreenSize = new Label("Screen Size:");
        screenSizeComboBox = new ComboBox<>();
        for (ScreenSize type : ScreenSize.values()) {
            screenSizeComboBox.getItems().add(type);
        }
        screenSizeComboBox.getSelectionModel().select(monitor.getScreenSize());
        screenSizeComboBox.setMaxWidth(200);

        Label lblMonitorResolution = new Label("Resolution:");
        monitorResolutionComboBox = new ComboBox<>();
        for (MonitorResolution type : MonitorResolution.values()) {
            monitorResolutionComboBox.getItems().add(type);
        }
        monitorResolutionComboBox.getSelectionModel().select(monitor.getResolution());
        monitorResolutionComboBox.setMaxWidth(200);

        Label lblMonitorRefreshRate = new Label("Refresh Rate:");
        monitorRefreshRateField = new TextField(String.valueOf(monitor.getRefreshRate()));
        monitorRefreshRateField.setMaxWidth(200);
        configTextFieldForInts(monitorRefreshRateField);

        // Add Monitor-specific fields to the VBox
        monitorDetailsBox.getChildren().clear();
        monitorDetailsBox.getChildren().addAll(lblMonitorScreenSize, screenSizeComboBox, lblMonitorResolution,
                monitorResolutionComboBox, lblMonitorRefreshRate, monitorRefreshRateField);
        setUpProductBox(monitorDetailsBox);
    }

    // Reverts changes made in the UI back to the original component values.
    // component: the component whose changes are being reverted
    private void revertChanges(Component component) {
        componentPriceField.setText(String.valueOf(component.getPrice()));
        componentQuantityField.setText(String.valueOf(component.getQuantity()));
        switch (component.getType()) {
            case CPU:
                CPU cpu = (CPU) component;
                coreCountField.setText(String.valueOf(cpu.getCore()));
                coreClockField.setText(String.valueOf(cpu.getCoreClock()));
                chipsetComboBox.getSelectionModel().select(cpu.getChipset());
                break;
            case CPU_COOLER:
                CPUCooler cooler = (CPUCooler) component;
                fanRPMField.setText(String.valueOf(cooler.getFanRPM()));
                noiseLevelField.setText(String.valueOf(cooler.getNoiseLevel()));
                radiatorSizeField.setText(String.valueOf(cooler.getRadiatorSize()));
                break;
            case MOTHERBOARD:
                Motherboard mtb = (Motherboard) component;
                chipsetComboBox.getSelectionModel().select(mtb.getChipset());
                formFactorComboBox.getSelectionModel().select(mtb.getFormFactor());
                break;
            case RAM:
                RAM ram = (RAM) component;
                ramMemoryField.setText(String.valueOf(ram.getMemory()));
                ramSpeedField.setText(String.valueOf(ram.getSpeed()));
                break;
            case STORAGE:
                Storage strg = (Storage) component;
                storageCapacityField.setText(String.valueOf(strg.getCapacity()));
                storageTypeComboBox.getSelectionModel().select(strg.getStorageType());
                break;
            case GPU:
                GPU gpu = (GPU) component;
                gpuVRAMField.setText(String.valueOf(gpu.getVRAM()));
                gpuCoreClockField.setText(String.valueOf(gpu.getCoreClock()));
                break;
            case CASE:
                PCCase pcCase = (PCCase) component;
                formFactorComboBox.getSelectionModel().select(pcCase.getFormFactor());
                pcCaseColourField.setText(pcCase.getCaseColour());
                break;
            case POWER_SUPPLY:
                PowerSupply ps = (PowerSupply) component;
                wattageField.setText(String.valueOf(ps.getWattage()));
                powerSupplyEffRatingComboBox.getSelectionModel().select(ps.getEfficiencyRating());
                break;
            case MONITOR:
                Monitor monitor = (Monitor) component;
                screenSizeComboBox.getSelectionModel().select(monitor.getScreenSize());
                monitorResolutionComboBox.getSelectionModel().select(monitor.getResolution());
                monitorRefreshRateField.setText(String.valueOf(monitor.getRefreshRate()));
                break;
            default:
                break;
        }
    }

    // Display a pop up informs user that product is not found
    // motherStage: the main stage of the application
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

    // Display a pop up informs user that not enough information is provided
    // motherStage: the main stage of the application
    private void notEnoughInformationAlert(Stage motherStage) {
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
        VBox root = new VBox(10, new Label("Please provide sufficient information!"), okBtn);
        root.setAlignment(Pos.CENTER);
        Scene confirmationScene = new Scene(root, 300, 200);

        // Set the scene and show it
        stage.setScene(confirmationScene);
        stage.showAndWait();
    }

    // Display a pop up notifies user that product is not found
    // motherStage: the main stage of the application
    // c: the newly updated component
    private void notifyProductChanged(Stage motherStage, Component c) {
        Stage stage = new Stage();
        stage.initOwner(motherStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Notification");

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
        Button okBtn = new Button("OK");
        okBtn.setAlignment(Pos.CENTER);
        okBtn.setOnAction(e -> {
            stage.close();
        });

        // Main view and scene
        VBox root = new VBox(10, new Label("Product Information Updated Successfully!"),
                new Label("NEW PRODUCT INFORMATION:"), productInfo,
                okBtn);
        root.setAlignment(Pos.CENTER);
        Scene confirmationScene = new Scene(root, 300, 500);

        // Set the scene and show it
        stage.setScene(confirmationScene);
        stage.show();
    }

    private void createAndConfigurePane() {
        mainContent = new VBox(5);
        mainContent.setAlignment(Pos.TOP_CENTER);

        productInfo = new VBox(5);
        productInfo.setAlignment(Pos.CENTER);

        cpuDetailsBox = new VBox(5);
        cpuDetailsBox.setAlignment(Pos.TOP_CENTER);

        cpuCoolerDetailsBox = new VBox(5);
        cpuCoolerDetailsBox.setAlignment(Pos.TOP_CENTER);

        motherboardDetailsBox = new VBox(5);
        motherboardDetailsBox.setAlignment(Pos.TOP_CENTER);

        ramDetailsBox = new VBox(5);
        ramDetailsBox.setAlignment(Pos.TOP_CENTER);

        storageDetailsBox = new VBox(5);
        storageDetailsBox.setAlignment(Pos.TOP_CENTER);

        gpuDetailsBox = new VBox(5);
        gpuDetailsBox.setAlignment(Pos.TOP_CENTER);

        caseDetailsBox = new VBox(5);
        caseDetailsBox.setAlignment(Pos.TOP_CENTER);

        powerSupplyDetailsBox = new VBox(5);
        powerSupplyDetailsBox.setAlignment(Pos.TOP_CENTER);

        monitorDetailsBox = new VBox(5);
        monitorDetailsBox.setAlignment(Pos.TOP_CENTER);
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

    // Function to confire text fields to accept double inputs only.
    private void configTextFieldForDoubles(TextField field) {
        field.setTextFormatter(new TextFormatter<Integer>((Change c) -> {
            if (c.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return c;
            }
            return null;
        }));
    }
}
