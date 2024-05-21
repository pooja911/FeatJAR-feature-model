package de.featjar.feature.model;

import java.util.HashMap;
import java.util.Map;

public class FeatureModelConfiguration {

    private Map<IFeature, SelectionType> featureStates;

    public FeatureModelConfiguration() {
        featureStates = new HashMap<>();
    }

    public void select(IFeature feature, SelectionType selectionType) {
        featureStates.put(feature, selectionType);
    }

    public void deselect(IFeature feature) {
        featureStates.remove(feature);
    }

    public void undefine(IFeature feature) {
        featureStates.remove(feature);
    }

    public SelectionType getFeatureSelectionType(IFeature feature) {
        return featureStates.get(feature);
    }

    public void selectAll(SelectionType selectionType) {
        for (IFeature feature : featureStates.keySet()) {
            featureStates.put(feature, selectionType);
        }
    }

    public void deselectAll() {
        featureStates.clear();
    }

    // public enum SelectionType {
    // MANUAL,
    // AUTOMATIC
    // }

}
