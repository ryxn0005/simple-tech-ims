package IMS.controller;

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
import IMS.model.enumerations.EfficiencyRating;
import IMS.model.enumerations.FormFactor;
import IMS.model.enumerations.MonitorResolution;
import IMS.model.enumerations.ScreenSize;
import IMS.model.enumerations.StorageType;

// The UpdateProductInfoController class is responsible for updating the information of components in the inventory.
public class UpdateProductInfoController {
    // Declare the model
    private InventoryModel invModel;

    // Constructor for the UpdateProductInfoController class
    public UpdateProductInfoController(InventoryModel invModel) {
        // Initialize the model
        this.invModel = invModel;
    }

    // Method to find a product in the inventory by its ID
    public Component findProduct(String id) {
        // Convert the ID from string to integer and find the product
        return invModel.findProductByID(convertStringToInt(id));
    }

    // Method to update the general information of a component
    public void updateGeneralInformation(Component component, String price, String quantity) {
        // Set the price and quantity of the component
        component.setPrice(convertStringToDouble(price));
        component.setQuantity(convertStringToInt(quantity));
    }

    // Method to update the information of a CPU
    public void updateCPU(CPU cpu, String core, String coreClock, CPUChipset chipset) {
        // Set the core, core clock, and chipset of the CPU
        cpu.setCore(convertStringToInt(core));
        cpu.setCoreClock(convertStringToDouble(coreClock));
        cpu.setChipset(chipset);
    }

    // Method to update the information of a CPU cooler
    public void updateCooler(CPUCooler cooler, String fanRPM, String noiseLevel, String radiatorSize) {
        // Set the fan RPM, noise level, and radiator size of the cooler
        cooler.setFanRPM(convertStringToInt(fanRPM));
        cooler.setNoiseLevel(convertStringToDouble(noiseLevel));
        cooler.setRadiatorSize(convertStringToInt(radiatorSize));
    }

    // Method to update the information of a motherboard
    public void updateMotherboard(Motherboard mtb, CPUChipset chipset, FormFactor formFactor) {
        // Set the chipset and form factor of the motherboard
        mtb.setChipset(chipset);
        mtb.setFormFactor(formFactor);
    }

    // Method to update the information of a RAM
    public void updateRAM(RAM ram, String memory, String speed) {
        // Set the memory and speed of the RAM
        ram.setMemory(convertStringToInt(memory));
        ram.setSpeed(convertStringToInt(speed));
    }

    // Method to update the information of a storage
    public void updateStorage(Storage strg, String capacity, StorageType storageType) {
        // Set the capacity and type of the storage
        strg.setCapacity(convertStringToInt(capacity));
        strg.setStorageType(storageType);
    }

    // Method to update the information of a GPU
    public void updateGPU(GPU gpu, String vram, String coreClock) {
        // Set the VRAM and core clock of the GPU
        gpu.setVRAM(convertStringToInt(vram));
        gpu.setCoreClock(convertStringToDouble(coreClock));
    }

    // Method to update the information of a PC case
    public void updateCase(PCCase pcCase, FormFactor formFactor, String colour) {
        // Set the form factor and colour of the case
        pcCase.setFormFactor(formFactor);
        pcCase.setCaseColour(colour);
    }

    // Method to update the information of a power supply
    public void updatePowerSupply(PowerSupply ps, String wattage, EfficiencyRating efficiencyRating) {
        // Set the wattage and efficiency rating of the power supply
        ps.setWattage(convertStringToInt(wattage));
        ps.setEfficiencyRating(efficiencyRating);
    }

    // Method to update the information of a monitor
    public void updateMonitor(Monitor monitor, ScreenSize screenSize, MonitorResolution resolution,
            String refreshRate) {
        // Set the screen size, resolution, and refresh rate of the monitor
        monitor.setScreenSize(screenSize);
        monitor.setResolution(resolution);
        monitor.setRefreshRate(convertStringToInt(refreshRate));
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

    // Private method to convert a string to a double
    private double convertStringToDouble(String s) {
        // If the string is null or empty, return 0
        if (s == null || s.isEmpty()) {
            return 0;
        }
        // If the string is "-", return 0
        if ("-".equals(s)) {
            return 0;
        }
        // Convert the string to a double and return it
        return Double.parseDouble(s);
    }
}