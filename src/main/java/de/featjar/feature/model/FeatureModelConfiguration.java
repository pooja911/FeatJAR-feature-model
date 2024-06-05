package de.featjar.feature.model;

import java.util.*;

//@author : PoojaGarg

public class FeatureModelConfiguration {
    private final Map<IFeature, SelectableFeature> features = new HashMap<>();

    public void addFeature(IFeature feature) {
        if (!features.containsKey(feature)) {
            features.put(feature, new SelectableFeature(feature));
        }
    }

    public void removeFeature(IFeature feature) {
        features.remove(feature);
    }
    
    public void setManual(IFeature feature, Selection selection) {
        SelectableFeature selectableFeature = features.get(feature);
        if (selectableFeature == null) {
            throw new FeatureNotFoundException(feature.getName().orElse("Unknown feature"));
        }
        selectableFeature.setManual(selection);
    }

    public void setAutomatic(IFeature feature, Selection selection) {
        SelectableFeature selectableFeature = features.get(feature);
        if (selectableFeature == null) {
            throw new FeatureNotFoundException(feature.getName().orElse("Unknown feature"));
        }
        selectableFeature.setAutomatic(selection);
    }

    public void resetManual(IFeature feature) {
        setManual(feature, Selection.UNDEFINED);
    }

    public void resetAutomatic(IFeature feature) {
        setAutomatic(feature, Selection.UNDEFINED);
    }

    public Set<IFeature> getSelectedFeatures() {
        return getFeaturesBySelection(Selection.SELECTED);
    }

    public Set<IFeature> getUnselectedFeatures() {
        return getFeaturesBySelection(Selection.UNSELECTED);
    }

    public Set<IFeature> getUndefinedFeatures() {
        return getFeaturesBySelection(Selection.UNDEFINED);
    }
    private Set<IFeature> getFeaturesBySelection(Selection selection) {
        Set<IFeature> result = new HashSet<>();
        for (SelectableFeature feature : features.values()) {
            if (feature.getSelection() == selection) {
                result.add(feature.getFeature());
            }
        }
        return result;
    }

}
