package de.featjar.feature.model;

import java.util.*;

/**
 * FeatureModelConfiguration manages the selection state of features within a feature model.
 * It supports adding and removing features, manually or automatically setting their selection state,
 * and resetting their selection state. It also allows retrieving sets of features based on their selection state.
 * 
 * Example usage:
 *     FeatureModel featureModel = new FeatureModel();
 *     FeatureModelConfiguration config = new FeatureModelConfiguration(featureModel);
 *     IFeature feature = featureModel.addFeature("FeatureA"); // Adding feature to the model
 *     config.setManual(feature, Selection.SELECTED);
 *     Set<IFeature> selectedFeatures = config.getSelectedFeatures();
 *
 * Note: Throws FeatureNotFoundException if a feature is not found during selection state changes.
 * 
 * @see IFeature
 * @see SelectableFeature
 * @see Selection
 * @see FeatureNotFoundException
 * 
 * Author: Pooja Garg
 *
 */
public class FeatureModelConfiguration {
    private final FeatureModel featureModel; // The feature model instance
    private final Map<IFeature, SelectableFeature> featureStates = new HashMap<>();

    public FeatureModelConfiguration(FeatureModel featureModel) {
        this.featureModel = Objects.requireNonNull(featureModel, "FeatureModel cannot be null");

        // Initialize configuration with features from the feature model
        for (IFeature feature : featureModel.getFeatures()) {
            featureStates.put(feature, new SelectableFeature(feature));
        }
    }

    /**
     * Adds a feature to the configuration if it exists in the feature model.
     * 
     * @param feature the feature to add
     * @throws FeatureNotFoundException if the feature is not found in the feature model
     */
    public void addFeature(IFeature feature) {
        if (!featureModel.hasFeature(feature.getIdentifier())) {
            throw new FeatureNotFoundException(feature.getName().orElse("Unknown feature"));
        }
        featureStates.putIfAbsent(feature, new SelectableFeature(feature));
    }

    /**
     * Removes a feature from the configuration.
     * 
     * @param feature the feature to remove
     */
    public void removeFeature(IFeature feature) {
        featureStates.remove(feature);
    }

    /**
     * Sets the manual selection state of a feature.
     * 
     * @param feature   the feature to update
     * @param selection the new selection state
     * @throws FeatureNotFoundException if the feature is not found in the configuration
     */
    public void setManual(IFeature feature, Selection selection) {
        SelectableFeature selectableFeature = getFeatureState(feature);
        selectableFeature.setManual(selection);
    }

    /**
     * Sets the automatic selection state of a feature.
     * 
     * @param feature   the feature to update
     * @param selection the new selection state
     * @throws FeatureNotFoundException if the feature is not found in the configuration
     */
    public void setAutomatic(IFeature feature, Selection selection) {
        SelectableFeature selectableFeature = getFeatureState(feature);
        selectableFeature.setAutomatic(selection);
    }

    /**
     * Resets the manual selection state of a feature.
     * 
     * @param feature the feature to reset
     */
    public void resetManual(IFeature feature) {
        setManual(feature, Selection.UNDEFINED);
    }

    /**
     * Resets the automatic selection state of a feature.
     * 
     * @param feature the feature to reset
     */
    public void resetAutomatic(IFeature feature) {
        setAutomatic(feature, Selection.UNDEFINED);
    }

    /**
     * Returns a set of features with the specified selection state.
     * 
     * @param selection the selection state to filter by
     * @return a set of features with the specified selection state
     */
    public Set<IFeature> getFeaturesBySelection(Selection selection) {
        Set<IFeature> result = new HashSet<>();
        for (SelectableFeature selectableFeature : featureStates.values()) {
            if (selectableFeature.getSelection() == selection) {
                result.add(selectableFeature.getFeature());
            }
        }
        return result;
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

    /**
     * Gets the selection state of a feature. Throws FeatureNotFoundException if the feature is not found.
     *
     * @param feature the feature to find
     * @return the selectable feature state
     * @throws FeatureNotFoundException if the feature is not found
     */
    private SelectableFeature getFeatureState(IFeature feature) {
        SelectableFeature selectableFeature = featureStates.get(feature);
        if (selectableFeature == null) {
            throw new FeatureNotFoundException(feature.getName().orElse("Unknown feature"));
        }
        return selectableFeature;
    }
}
