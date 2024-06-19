package de.featjar.feature.model;

import de.featjar.base.data.identifier.IIdentifier;

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
    private final IIdentifier identifier;
    private final String name;
    private SelectionType manual = SelectionType.UNDEFINED;
    private SelectionType automatic = SelectionType.UNDEFINED;

    public SelectableFeature(IIdentifier identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    public IIdentifier getIdentifier() {
//        return identifier;
//    }

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

    public SelectionType getSelection() {
        return manual != SelectionType.UNDEFINED ? manual : automatic;
    }
}
