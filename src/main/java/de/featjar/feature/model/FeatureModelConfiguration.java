package de.featjar.feature.model;

import java.util.*;

import de.featjar.base.data.identifier.IIdentifiable;
import de.featjar.base.data.identifier.IIdentifier;

/**
 * FeatureModelConfiguration manages the selection state of features within a feature model.
 * It supports adding and removing features, manually or automatically setting their selection state,
 * and resetting their selection state. It also allows retrieving sets of features based on their selection state.
 * 
 * Example usage:
 *     FeatureModel featureModel = new FeatureModel();
 *     FeatureModelConfiguration config = new FeatureModelConfiguration(featureModel);
 *     IIdentifier featureId = ...; // Obtain feature identifier
 *     config.setManual(featureId, SelectionType.SELECTED);
 *     Set<IIdentifier> selectedFeatures = config.getSelectedFeatures();
 *
 * Note: Throws FeatureNotFoundException if a feature is not found during selection state changes.
 * 
 * @see IFeature
 * @see SelectableFeature
 * @see SelectionType
 * @see FeatureNotFoundException
 * 
 * Author: Pooja Garg
 *
 */
public class FeatureModelConfiguration {
    private final FeatureModel featureModel; // The feature model instance
    private final Map<String, SelectableFeature> featureStates = new HashMap<>();

    public FeatureModelConfiguration(FeatureModel featureModel) {
        this.featureModel = Objects.requireNonNull(featureModel, "FeatureModel cannot be null");

        // Initialize configuration with features from the feature model
        for (IFeature feature : featureModel.getFeatures()) {
            featureStates.put(feature.getName().get(), new SelectableFeature(feature.getName().get()));
        }
    }

    /**
     * Adds a feature to the configuration if it exists in the feature model.
     * 
     * @param featureId the identifier of the feature to add
     * @throws FeatureNotFoundException if the feature is not found in the feature model
     */
    public void addFeature(String featureName) {
        if (!featureModel.getFeature(featureName).isPresent()) {
            throw new FeatureNotFoundException();
        }
        featureStates.putIfAbsent(featureName, new SelectableFeature(featureName));
    }

    /**
     * Removes a feature from the configuration.
     * 
     * @param featureId the identifier of the feature to remove
     */
    public void removeFeature(String featureId) {
        featureStates.remove(featureId);
    }

    /**
     * Sets the manual selection state of a feature.
     * 
     * @param featureId the identifier of the feature to update
     * @param selection the new selection state
     * @throws FeatureNotFoundException if the feature is not found in the configuration
     */
    public void setManual(String featureId, SelectionType selection) {
        SelectableFeature selectableFeature = getFeatureState(featureId);
        selectableFeature.setManual(selection);
    }

    /**
     * Sets the automatic selection state of a feature.
     * 
     * @param featureId the identifier of the feature to update
     * @param selection the new selection state
     * @throws FeatureNotFoundException if the feature is not found in the configuration
     */
    public void setAutomatic(String featureId, SelectionType selection) {
        SelectableFeature selectableFeature = getFeatureState(featureId);
        selectableFeature.setAutomatic(selection);
    }
    
    /**
     * Resets the manual selection state of a feature.
     * 
     * @param featureId the identifier of the feature to reset
     */
    public void resetManual(String featureId) {
        setManual(featureId, SelectionType.UNDEFINED);
    }

    /**
     * Resets the automatic selection state of a feature.
     * 
     * @param featureId the identifier of the feature to reset
     */
    public void resetAutomatic(String featureId) {
        setAutomatic(featureId, SelectionType.UNDEFINED);
    }

    /**
     * Returns a set of feature identifiers with the specified selection state.
     * 
     * @param selection the selection state to filter by
     * @return a set of feature identifiers with the specified selection state
     */
    public Set<String> getFeaturesBySelection(SelectionType selection) {
        Set<String> result = new HashSet<>();
        for (SelectableFeature selectableFeature : featureStates.values()) {
            if (selectableFeature.getSelection() == selection) {
                result.add(selectableFeature.getName());
            }
        }
        return result;
    }

    public Set<String> getSelectedFeatures() {
        return getFeaturesBySelection(SelectionType.SELECTED);
    }

    public Set<String> getUnselectedFeatures() {
        return getFeaturesBySelection(SelectionType.UNSELECTED);
    }

    public Set<String> getUndefinedFeatures() {
        return getFeaturesBySelection(SelectionType.UNDEFINED);
    }

    /**
     * Gets the selection state of a feature by its identifier. Throws FeatureNotFoundException if the feature is not found.
     *
     * @param featureId the identifier of the feature to find
     * @return the selectable feature state
     * @throws FeatureNotFoundException if the feature is not found
     */
    private SelectableFeature getFeatureState(String featureId) {
        SelectableFeature selectableFeature = featureStates.get(featureId);
        if (selectableFeature == null) {
            throw new FeatureNotFoundException();
        }
        return selectableFeature;
    }
    
    public boolean isManualSelected(String featureName) {
    	return getFeatureState(featureName).getManual() == SelectionType.SELECTED;
	}
    
    public boolean isAutomaticSelected(String featureName)
    {
    	return getFeatureState(featureName).getAutomatic() == SelectionType.SELECTED;
    }
    
    public Set<String> getAllFeatures()
    {
    	Set<String> features = new HashSet<>();
    	features.addAll(getSelectedFeatures());
    	features.addAll(getUnselectedFeatures());
    	features.addAll(getUndefinedFeatures());
    	return features;
    }
}
