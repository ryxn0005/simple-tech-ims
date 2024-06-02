package IMS.model.components;

import IMS.model.enumerations.ComponentBrand;
import IMS.model.enumerations.ComponentType;
import IMS.model.enumerations.FormFactor;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PCCase extends Component {
    private final SimpleObjectProperty<FormFactor> formFactor;
    private final SimpleStringProperty caseColour;

    public PCCase(ComponentBrand brand, String name, double price, FormFactor formFactor,
            String caseColour, int quantity) {
        super(ComponentType.CASE, brand, name, price, quantity);
        this.formFactor = new SimpleObjectProperty<>(formFactor);
        this.caseColour = new SimpleStringProperty(caseColour);
    }

    public FormFactor getFormFactor() {
        return this.formFactor.get();
    }

    public void setFormFactor(FormFactor formFactor) {
        this.formFactor.set(formFactor);
    }

    public SimpleObjectProperty<FormFactor> formFactorProperty() {
        return this.formFactor;
    }

    public String getCaseColour() {
        return this.caseColour.get();
    }

    public void setCaseColour(String caseColour) {
        this.caseColour.set(caseColour);
    }

    public SimpleStringProperty caseColourProperty() {
        return this.caseColour;
    }
}