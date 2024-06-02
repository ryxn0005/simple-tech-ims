package IMS.model.enumerations;

public enum ComponentBrand {
    INTEL("Intel"),
    EVGA("EVGA"),
    SAPPHIRE("Sappire"),
    ASUS("ASUS"),
    WESTERN_DIGITAL("Western Digital"),
    CORSAIR("Corsair"),
    G_SKILL("G.SKILL"),
    NZXT("NZXT"),
    FRACTAL_DESIGN("Fractal Design"),
    COOLER_MASTER("Cooler Master"),
    GIGABYTE("GIGABYTE"),
    SEAGATE("Seagate"),
    SAMSUNG("SAMSUNG"),
    NOCTUA("Noctua"),
    LOGITECH("Logitech"),
    BENQ("BenQ"),
    RAZER("Razer"),
    LIAN_LI("Lian Li"),
    LG("LG"),
    AMD("AMD");

    private String name;

    ComponentBrand(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
