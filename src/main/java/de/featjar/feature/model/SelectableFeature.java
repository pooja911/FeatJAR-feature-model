package de.featjar.feature.model;

public class SelectableFeature {
    private final IFeature feature;
    private Selection manual = Selection.UNDEFINED;
    private Selection automatic = Selection.UNDEFINED;

    public SelectableFeature(IFeature feature) {
        this.feature = feature;
    }

    public String getName() {
        return feature.getName().orElse("Unnamed feature");
    }

    public IFeature getFeature() {
        return feature;
    }

    public Selection getManual() {
        return manual;
    }

    public void setManual(Selection manual) {
        this.manual = manual;
    }

    public Selection getAutomatic() {
        return automatic;
    }

    public void setAutomatic(Selection automatic) {
        this.automatic = automatic;
    }

    public Selection getSelection() {
        return manual != Selection.UNDEFINED ? manual : automatic;
    }


}
