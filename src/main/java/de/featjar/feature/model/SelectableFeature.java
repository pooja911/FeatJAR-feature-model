package de.featjar.feature.model;

/**
 * SelectableFeature represents a feature within a feature model with manual and automatic selection states.
 * It provides methods to get and set these selection states, as well as to retrieve the feature name and identifier.
 * The overall selection state is determined by the manual state, if defined, otherwise by the automatic state.
 *
 * Example usage:
 *     IFeature feature = ...; // some feature implementation
 *     SelectableFeature selectableFeature = new SelectableFeature(feature.getIdentifier(), feature.getName());
 *     selectableFeature.setManual(SelectionType.SELECTED);
 *     Selection selection = selectableFeature.getSelection(); // returns SelectionType.SELECTED
 *
 * @see IFeature
 * @see SelectionType
 *
 * Author: Pooja Garg
 */
public class SelectableFeature {
    private final String name;
    private Selection manual = Selection.UNDEFINED;
    private Selection automatic = Selection.UNDEFINED;

    public SelectableFeature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
