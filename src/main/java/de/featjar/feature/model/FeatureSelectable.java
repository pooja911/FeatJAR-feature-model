package de.featjar.feature.model;
<<<<<<< HEAD
=======
import de.featjar.feature.model.SelectionType;
>>>>>>> c2967bbded58cfa281c179b5e1e97c3778e5ed94

public class FeatureSelectable {
    private Object value;
    private SelectionType manual;
    private SelectionType automatic;

    public FeatureSelectable(Object value, SelectionType manual, SelectionType automatic) {
        this.value = value;
        this.manual = manual;
        this.automatic = automatic;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SelectionType getManual() {
        return manual;
    }

    public void setManual(SelectionType manual) {
        this.manual = manual;
    }

    public SelectionType getAutomatic() {
        return automatic;
    }

    public void setAutomatic(SelectionType automatic) {
        this.automatic = automatic;
    }
}