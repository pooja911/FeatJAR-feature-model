package de.featjar.feature.model;


/**
 * SelectableFeature represents a feature within a feature model with manual and automatic selection states.
 * It provides methods to get and set these selection states, as well as to retrieve the feature and its name.
 * The overall selection state is determined by the manual state, if defined, otherwise by the automatic state.
 * 
 * 
 * Example usage:
 *     IFeature feature = ...; // some feature implementation
 *     SelectableFeature selectableFeature = new SelectableFeature(feature);
 *     selectableFeature.setManual(Selection.SELECTED);
 *     Selection selection = selectableFeature.getSelection(); // returns Selection.SELECTED
 *
 * @see IFeature
 * @see Selection
 * 
 *  @author: Pooja Garg
 */

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
