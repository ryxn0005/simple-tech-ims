package IMS.view;

import IMS.controller.AddProductController;
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
import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import IMS.model.enumerations.EfficiencyRating;
import IMS.model.enumerations.FormFactor;
import IMS.model.enumerations.MonitorResolution;
import IMS.model.enumerations.ScreenSize;
import IMS.model.enumerations.StorageType;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

public class AddProductTab {
    private VBox mainContent;
    private VBox cpuDetailsBox;
    private VBox cpuCoolerDetailsBox;
    private VBox motherboardDetailsBox;
    private VBox ramDetailsBox;
    private VBox storageDetailsBox;
    private VBox gpuDetailsBox;
    private VBox caseDetailsBox;
    private VBox powerSupplyDetailsBox;
    private VBox monitorDetailsBox;

    Label lblComponentType;
    Label lblComponentBrand;
    Label lblComponentName;
    Label lblComponentQuantity;
    Label lblComponentPrice;

    TextField componentNameField;
    TextField componentPriceField;
    TextField componentQuantityField;
    TextField coreCountField;
    TextField coreClockField;
    TextField fanRPMField;
    TextField noiseLevelField;
    TextField radiatorSizeField;
    TextField ramMemoryField;
    TextField ramSpeedField;
    TextField storageCapacityField;
    TextField gpuVRAMField;
    TextField gpuCoreClockField;
    TextField wattageField;
    TextField pcCaseColourField;
    TextField monitorRefreshRateField;

    ComboBox<EfficiencyRating> powerSupplyEffRatingComboBox;
    ComboBox<ScreenSize> screenSizeComboBox;
    ComboBox<MonitorResolution> monitorResolutionComboBox;
    ComboBox<ComponentBrand> componentBrandComboBox;
    ComboBox<ComponentType> componentTypeComboBox;
    ComboBox<CPUChipset> chipsetComboBox;
    ComboBox<StorageType> storageTypeComboBox;
    ComboBox<FormFactor> formFactorComboBox;

    Button addBtn;

    private InventoryModel invModel;
    private AddProductController controller;
    Stage primaryStage;

    public AddProductTab(InventoryModel invModel, AddProductController controller, Stage primaryStage) {
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
        // Set up general labels and text fields or combo boxes
        lblComponentType = new Label("Product Type:");
        componentTypeComboBox = new ComboBox<>();
        lblComponentBrand = new Label("Brand:");
        componentBrandComboBox = new ComboBox<>();
        lblComponentName = new Label("Name:");
        componentNameField = new TextField();
        lblComponentPrice = new Label("Price:");
        componentPriceField = new TextField();
        configTextFieldForDoubles(componentPriceField);
        lblComponentQuantity = new Label("Quantity:");
        componentQuantityField = new TextField();
        configTextFieldForInts(componentQuantityField);

        // Set max width for all fields and combobox
        componentTypeComboBox.setMaxWidth(200);
        componentBrandComboBox.setMaxWidth(200);
        componentNameField.setMaxWidth(200);
        componentPriceField.setMaxWidth(200);
        componentQuantityField.setMaxWidth(200);

        addBtn = new Button("Add Product to Inventory");
        addBtn.setOnAction(e -> {
            addProductToInventory();
        });

        // Populate general combo boxes
        for (ComponentType type : ComponentType.values()) {
            componentTypeComboBox.getItems().add(type);
        }
        componentTypeComboBox.setPromptText("Select Component Type");

        for (ComponentBrand brand : ComponentBrand.values()) {
            componentBrandComboBox.getItems().add(brand);
        }
        componentBrandComboBox.setPromptText("Select Brand");

        componentTypeComboBox.valueProperty().addListener((observedValue, oldValue, newValue) -> {
            if (newValue != null) {
                showComponentFields(newValue);
            }
        });

        mainContent.getChildren().addAll(lblComponentType, componentTypeComboBox);

    }

    // Function to show specific component type-related fields when a type is chosen
    private void showComponentFields(ComponentType type) {
        // clear up the fields of the previous selection (if needed)
        clearPreviousFieldsSeletion();
        // Based on the type, setup and add the relevant detail box
        switch (type) {
            case CPU:
                showCpuFields();
                break;
            case CPU_COOLER:
                showCpuCoolerFields();
                break;
            case MOTHERBOARD:
                showMotherboardFields();
                break;
            case RAM:
                showRamFields();
                break;
            case STORAGE:
                showStorageFields();
                break;
            case GPU:
                showGpuFields();
                break;
            case CASE:
                showPCCaseFields();
                break;
            case POWER_SUPPLY:
                showPowerSupplyFields();
                break;
            case MONITOR:
                showMonitorFields();
                break;
            default:
                break;
        }
    }

    // Function to clear the field of the previous component type selection and then
    // set up the general fields again.
    private void clearPreviousFieldsSeletion() {
        mainContent.getChildren().clear();
        mainContent.getChildren().addAll(lblComponentType, componentTypeComboBox);
    }

    // Function to setup the main content, add the type-specific field into main
    // VBox.
    private void setUpMainContent(VBox typeBox) {
        mainContent.getChildren().addAll(lblComponentBrand,
                componentBrandComboBox, lblComponentName, componentNameField, lblComponentPrice, componentPriceField,
                lblComponentQuantity, componentQuantityField, typeBox, addBtn);
    }

    // Function to display fields related to CPU
    private void showCpuFields() {
        // Create CPU-specific labels and text fields
        Label lblCPUCore = new Label("CPU Core Count:");
        coreCountField = new TextField();
        configTextFieldForInts(coreCountField);
        coreCountField.setMaxWidth(200);

        Label lblCPUClock = new Label("CPU Core Clock");
        coreClockField = new TextField();
        configTextFieldForDoubles(coreClockField);
        coreClockField.setMaxWidth(200);

        Label lblChipset = new Label("Chipset:");
        chipsetComboBox = new ComboBox<>();
        for (CPUChipset type : CPUChipset.values()) {
            chipsetComboBox.getItems().add(type);
        }
        chipsetComboBox.setPromptText("Select Chipset");
        chipsetComboBox.setMaxWidth(200);

        // Add CPU-specific fields to the VBox
        if (cpuDetailsBox.getChildren().isEmpty()) {
            cpuDetailsBox.getChildren().addAll(lblCPUCore, coreCountField, lblCPUClock, coreClockField, lblChipset,
                    chipsetComboBox);
        }
        setUpMainContent(cpuDetailsBox);
    }

    // Function to display fields related to CPU Cooler
    private void showCpuCoolerFields() {
        // Create CPU-Cooler-specific labels and text fields
        Label lblCoolerFanRPM = new Label("Fan RPM:");
        fanRPMField = new TextField();
        configTextFieldForInts(fanRPMField);
        fanRPMField.setMaxWidth(200);

        Label lblCoolerNoiseLevel = new Label("Noise Level:");
        noiseLevelField = new TextField();
        configTextFieldForDoubles(noiseLevelField);
        noiseLevelField.setMaxWidth(200);

        Label lblCoolerRadiatorSize = new Label("Radiator Size:");
        radiatorSizeField = new TextField();
        configTextFieldForInts(radiatorSizeField);
        radiatorSizeField.setMaxWidth(200);

        // Add CPU-Cooler-specific fields to the VBox
        if (cpuCoolerDetailsBox.getChildren().isEmpty()) {
            cpuCoolerDetailsBox.getChildren().addAll(lblCoolerFanRPM, fanRPMField, lblCoolerNoiseLevel, noiseLevelField,
                    lblCoolerRadiatorSize, radiatorSizeField);
        }
        setUpMainContent(cpuCoolerDetailsBox);
    }

    // Function to display fields related to Motherboard
    private void showMotherboardFields() {
        // Create Motherboard-specific labels and text fields
        Label lblChipset = new Label("Chipset:");
        chipsetComboBox = new ComboBox<>();
        for (CPUChipset type : CPUChipset.values()) {
            chipsetComboBox.getItems().add(type);
        }
        chipsetComboBox.setPromptText("Select Chipset");
        chipsetComboBox.setMaxWidth(200);

        Label lblFormFactor = new Label("Form Factor:");
        formFactorComboBox = new ComboBox<>();
        for (FormFactor type : FormFactor.values()) {
            formFactorComboBox.getItems().add(type);
        }
        formFactorComboBox.setPromptText("Select Form Factor");
        formFactorComboBox.setMaxWidth(200);

        // Add Motherboard-specific fields to the VBox
        if (motherboardDetailsBox.getChildren().isEmpty()) {
            motherboardDetailsBox.getChildren().addAll(lblChipset, chipsetComboBox, lblFormFactor, formFactorComboBox);
        }
        setUpMainContent(motherboardDetailsBox);
    }

    // Function to display fields related to RAM
    private void showRamFields() {
        // Create RAM-specific labels and text fields
        Label lblRAMMemory = new Label("Memory:");
        ramMemoryField = new TextField();
        configTextFieldForInts(ramMemoryField);
        ramMemoryField.setMaxWidth(200);

        Label lblRAMSpeed = new Label("Speed:");
        ramSpeedField = new TextField();
        configTextFieldForInts(ramSpeedField);
        ramSpeedField.setMaxWidth(200);

        // Add RAM-specific fields to the VBox
        if (ramDetailsBox.getChildren().isEmpty()) {
            ramDetailsBox.getChildren().addAll(lblRAMMemory, ramMemoryField, lblRAMSpeed, ramSpeedField);
        }
        setUpMainContent(ramDetailsBox);
    }

    // Function to display fields related to Storage
    private void showStorageFields() {
        // Create Storage-specific labels and text fields
        Label lblStorageCapacity = new Label("Capacity:");
        storageCapacityField = new TextField();
        configTextFieldForInts(storageCapacityField);
        storageCapacityField.setMaxWidth(200);

        Label lblStorageType = new Label("Storage Type:");
        storageTypeComboBox = new ComboBox<>();
        for (StorageType type : StorageType.values()) {
            storageTypeComboBox.getItems().add(type);
        }
        storageTypeComboBox.setPromptText("Select Storage Type");
        storageTypeComboBox.setMaxWidth(200);

        // Add Storage-specific fields to the VBox
        if (storageDetailsBox.getChildren().isEmpty()) {
            storageDetailsBox.getChildren().addAll(lblStorageCapacity, storageCapacityField, lblStorageType,
                    storageTypeComboBox);
        }
        setUpMainContent(storageDetailsBox);
    }

    // Function to display fields related to GPU
    private void showGpuFields() {
        // Create GPU-specific labels and text fields
        Label lblGPUVRAM = new Label("VRAM:");
        gpuVRAMField = new TextField();
        configTextFieldForInts(gpuVRAMField);
        gpuVRAMField.setMaxWidth(200);

        Label lblGPUCoreClock = new Label("GPU Core Clock:");
        gpuCoreClockField = new TextField();
        configTextFieldForDoubles(gpuCoreClockField);
        gpuCoreClockField.setMaxWidth(200);

        // Add GPU-specific fields to the VBox
        if (gpuDetailsBox.getChildren().isEmpty()) {
            gpuDetailsBox.getChildren().addAll(lblGPUVRAM, gpuVRAMField, lblGPUCoreClock, gpuCoreClockField);
        }
        setUpMainContent(gpuDetailsBox);
    }

    // Function to display fields related to PC Case
    private void showPCCaseFields() {
        // Create PCCase-specific labels and text fields
        Label lblFormFactor = new Label("Form Factor:");
        formFactorComboBox = new ComboBox<>();
        for (FormFactor type : FormFactor.values()) {
            formFactorComboBox.getItems().add(type);
        }
        formFactorComboBox.setPromptText("Select Form Factor");
        formFactorComboBox.setMaxWidth(200);

        Label lblPCCaseColour = new Label("Case Colour:");
        pcCaseColourField = new TextField();
        pcCaseColourField.setMaxWidth(200);

        // Add PCCase-specific fields to the VBox
        if (caseDetailsBox.getChildren().isEmpty()) {
            caseDetailsBox.getChildren().addAll(lblFormFactor, formFactorComboBox, lblPCCaseColour, pcCaseColourField);
        }
        setUpMainContent(caseDetailsBox);
    }

    // Function to display fields related to Power Supply
    private void showPowerSupplyFields() {
        // Create PowerSupply-specific labels and text fields
        Label lblPowerSupplyWattage = new Label("Wattage");
        wattageField = new TextField();
        configTextFieldForInts(wattageField);
        wattageField.setMaxWidth(200);

        Label lblPowerSupplyEffRating = new Label("Efficiency Rating");
        powerSupplyEffRatingComboBox = new ComboBox<>();
        for (EfficiencyRating type : EfficiencyRating.values()) {
            powerSupplyEffRatingComboBox.getItems().add(type);
        }
        powerSupplyEffRatingComboBox.setPromptText("Select Rating");
        powerSupplyDetailsBox.setMaxWidth(200);

        // Add PowerSupply-specific fields to the VBox
        if (powerSupplyDetailsBox.getChildren().isEmpty()) {
            powerSupplyDetailsBox.getChildren().addAll(lblPowerSupplyWattage, wattageField, lblPowerSupplyEffRating,
                    powerSupplyEffRatingComboBox);
        }
        setUpMainContent(powerSupplyDetailsBox);
    }

    // Function to display fields related to Monitor
    private void showMonitorFields() {
        // Create Monitor-specific labels and text fields
        Label lblMonitorScreenSize = new Label("Screen Size:");
        screenSizeComboBox = new ComboBox<>();
        for (ScreenSize type : ScreenSize.values()) {
            screenSizeComboBox.getItems().add(type);
        }
        screenSizeComboBox.setPromptText("Select Screen Size");
        screenSizeComboBox.setMaxWidth(200);

        Label lblMonitorResolution = new Label("Resolution:");
        monitorResolutionComboBox = new ComboBox<>();
        for (MonitorResolution type : MonitorResolution.values()) {
            monitorResolutionComboBox.getItems().add(type);
        }
        monitorResolutionComboBox.setPromptText("Select Resolution");
        monitorResolutionComboBox.setMaxWidth(200);

        Label lblMonitorRefreshRate = new Label("Refresh Rate:");
        monitorRefreshRateField = new TextField();
        monitorRefreshRateField.setMaxWidth(200);
        configTextFieldForInts(monitorRefreshRateField);

        // Add Monitor-specific fields to the VBox
        if (monitorDetailsBox.getChildren().isEmpty()) {
            monitorDetailsBox.getChildren().addAll(lblMonitorScreenSize, screenSizeComboBox, lblMonitorResolution,
                    monitorResolutionComboBox, lblMonitorRefreshRate, monitorRefreshRateField);
        }
        setUpMainContent(monitorDetailsBox);
    }

    // Function to add a product to inventory based on the selected type.
    private void addProductToInventory() {
        try {
            ComponentType selectedType = componentTypeComboBox.getValue();
            ComponentBrand selectedBrand = componentBrandComboBox.getValue();
            String name = componentNameField.getText();
            double price = Double.parseDouble(componentPriceField.getText());
            int quantity = Integer.parseInt(componentQuantityField.getText());

            Component newComponent = null;

            // For each selected type there is a different class for it. A switch case is
            // implemeted to cover everthing
            switch (selectedType) {
                case CPU:
                    int core = Integer.parseInt(coreCountField.getText());
                    double coreClock = Double.parseDouble(coreClockField.getText());
                    CPUChipset chipset = chipsetComboBox.getValue();
                    newComponent = new CPU(selectedBrand, name, price, core, coreClock, chipset, quantity);
                    break;
                case CPU_COOLER:
                    int fanRPM = Integer.parseInt(fanRPMField.getText());
                    double noiseLevel = Double.parseDouble(noiseLevelField.getText());
                    int radiatorSize = Integer.parseInt(radiatorSizeField.getText());
                    newComponent = new CPUCooler(selectedBrand, name, price, fanRPM, noiseLevel, radiatorSize,
                            quantity);
                    break;
                case MOTHERBOARD:
                    CPUChipset motherboardChipset = chipsetComboBox.getValue();
                    FormFactor formFactor = formFactorComboBox.getValue();
                    newComponent = new Motherboard(selectedBrand, name, price, motherboardChipset, formFactor,
                            quantity);
                    break;
                case RAM:
                    int memory = Integer.parseInt(ramMemoryField.getText());
                    int speed = Integer.parseInt(ramSpeedField.getText());
                    newComponent = new RAM(selectedBrand, name, price, memory, speed, quantity);
                    break;
                case STORAGE:
                    int capacity = Integer.parseInt(storageCapacityField.getText());
                    StorageType storageType = storageTypeComboBox.getValue();
                    newComponent = new Storage(selectedBrand, name, price, capacity, storageType, quantity);
                    break;
                case GPU:
                    int vram = Integer.parseInt(gpuVRAMField.getText());
                    double gpuCoreClock = Double.parseDouble(gpuCoreClockField.getText());
                    newComponent = new GPU(selectedBrand, name, price, vram, gpuCoreClock, quantity);
                    break;
                case CASE:
                    FormFactor caseFormFactor = formFactorComboBox.getValue();
                    String colour = pcCaseColourField.getText();
                    newComponent = new PCCase(selectedBrand, name, price, caseFormFactor, colour, quantity);
                    break;
                case POWER_SUPPLY:
                    int wattage = Integer.parseInt(wattageField.getText());
                    EfficiencyRating efficiencyRating = powerSupplyEffRatingComboBox.getValue();
                    newComponent = new PowerSupply(selectedBrand, name, price, wattage, efficiencyRating, quantity);
                    break;
                case MONITOR:
                    ScreenSize screenSize = screenSizeComboBox.getValue();
                    MonitorResolution resolution = monitorResolutionComboBox.getValue();
                    int refreshRate = Integer.parseInt(monitorRefreshRateField.getText());
                    newComponent = new Monitor(selectedBrand, name, price, screenSize, resolution, refreshRate,
                            quantity);
                    break;
                default:
                    break;
            }

            // Checks if a new component is created or not
            if (newComponent != null) {
                // Prompt user for confirmation
                boolean isConfirmed = promptUserConfirmation(this.primaryStage, newComponent);
                if (isConfirmed) {
                    controller.addComponent(newComponent);
                    notifyProductAdded(primaryStage);
                }
            }
        } catch (NumberFormatException e) {
            notEnoughInformationAlert(primaryStage);
        } catch (NullPointerException e) {
            notEnoughInformationAlert(primaryStage);
        }
    }

    // Function to clear all the fields after user comfirms to add a profuct
    private void clearFieldsInputs() {
        // Clear fields in mainContent
        clearFieldsInputsInContainer(mainContent);

        // Clear fields in nested VBoxes
        clearFieldsInputsInContainer(cpuDetailsBox);
        clearFieldsInputsInContainer(cpuCoolerDetailsBox);
        clearFieldsInputsInContainer(motherboardDetailsBox);
        clearFieldsInputsInContainer(ramDetailsBox);
        clearFieldsInputsInContainer(storageDetailsBox);
        clearFieldsInputsInContainer(gpuDetailsBox);
        clearFieldsInputsInContainer(caseDetailsBox);
        clearFieldsInputsInContainer(powerSupplyDetailsBox);
        clearFieldsInputsInContainer(monitorDetailsBox);

        componentTypeComboBox.setPromptText("Select Component Type");
        componentBrandComboBox.setPromptText("Select Brand");
    }

    // Clear fields sub functions
    // What it basically does is find all of the text fields and combo boxes in the
    // parent container, do a type cast and then clear it.
    private void clearFieldsInputsInContainer(Parent container) {
        for (Node child : container.getChildrenUnmodifiable()) {
            if (child instanceof TextField) {
                ((TextField) child).clear();
            } else if (child instanceof ComboBox<?>) {
                ((ComboBox<?>) child).getSelectionModel().clearSelection();
            } else if (child instanceof Parent) {
                clearFieldsInputsInContainer((Parent) child);
            }
        }
    }

    // Prompt users to confirm the adding action. Returns a Boolean type to decide
    // where to add the product or not
    private boolean promptUserConfirmation(Stage motherStage, Component c) {
        boolean[] confirmed = new boolean[1];
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
            confirmed[0] = true;
            // Clear the fields inputs and clear the text fields or combo boxes types of the
            // added component
            clearFieldsInputs();
            clearPreviousFieldsSeletion();
            stage.close();
        });
        Button noBtn = new Button("No");
        noBtn.setOnAction(e -> {
            stage.close();
        });

        // Button rows, main view and scene
        HBox buttonRow = new HBox(20, yesBtn, noBtn);
        buttonRow.setAlignment(Pos.CENTER);
        VBox root = new VBox(10, new Label("Do you wish to add this product?"), productInfo, buttonRow);
        root.setAlignment(Pos.CENTER);
        Scene confirmationScene = new Scene(root, 300, 600);

        // Set the scene and show it
        stage.setScene(confirmationScene);
        stage.showAndWait();

        return confirmed[0];
    }

    private void notifyProductAdded(Stage motherStage) {
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
        VBox root = new VBox(10, new Label("Product Added Successfully!"), okBtn);
        root.setAlignment(Pos.CENTER);
        Scene confirmationScene = new Scene(root, 300, 200);

        // Set the scene and show it
        stage.setScene(confirmationScene);
        stage.showAndWait();
    }

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

    private void createAndConfigurePane() {
        mainContent = new VBox(5);
        mainContent.setAlignment(Pos.TOP_CENTER);

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

    // Function to confire text fields to accept int doubles only.
    private void configTextFieldForDoubles(TextField field) {
        field.setTextFormatter(new TextFormatter<Integer>((Change c) -> {
            if (c.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return c;
            }
            return null;
        }));
    }
}
