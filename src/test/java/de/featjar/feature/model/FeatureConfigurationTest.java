package de.featjar.feature.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.featjar.base.data.IAttribute;
import de.featjar.base.data.Result;
import de.featjar.base.data.identifier.IIdentifier;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/**
 * FeatureConfigurationTest tests the FeatureModelConfiguration class.
 * It verifies adding, removing, and setting selection states (manual and automatic) for features.
 * 
 * Example tests:
 *     - Adding features and verifying the count
 *     - Removing features and checking they are not present
 *     - Setting manual selection state and verifying
 *     - Setting automatic selection state and verifying

 * A nested TestFeature class is used as a mock implementation of the IFeature interface for testing purposes.
 * 
 * @see FeatureModelConfiguration
 * @see IFeature
 * @see SelectableFeature
 * @see Selection
 * 
 * Author: Pooja Garg
 */
class FeatureConfigurationTest {
    private FeatureModelConfiguration featureConfiguration;
    private FeatureModel featureModel;
    private IFeature feature1;
    private IFeature feature2;
    
	/**
	 * Set up before each test case
	 * featureModel is a FeatureModel object which is created
	 * feature1 and feature2 are IFeature object which are added as features to the fm
	 * featureConfiguration is a FeatureModelConfiguration object which is created with the fm
	 */
    @BeforeEach
    void setUp() {
    	featureModel = new FeatureModel();
        feature1 = featureModel.addFeature("Feature1");
        feature2 = featureModel.addFeature("Feature2");
        featureConfiguration = new FeatureModelConfiguration(featureModel);       
    }

    /**
     * Test case to see if features have been added
     * Assert statement checks if 2 features have been added to the FMConfiguration
     */
    @Test
    void testAddFeature() {
        assertEquals(2, featureConfiguration.getSelectedFeatures().size() +
                featureConfiguration.getUnselectedFeatures().size() +
                featureConfiguration.getUndefinedFeatures().size());
    }
    
    /**
     * Test case to see if features have been removed
     * Assert statement checks if feature1 is still there in the FMConfiguration
     */
    @Test
    void testRemoveFeature() {
        featureConfiguration.removeFeature(feature1);
        assertFalse(featureConfiguration.getSelectedFeatures().contains(feature1));
        assertFalse(featureConfiguration.getUnselectedFeatures().contains(feature1));
        assertFalse(featureConfiguration.getUndefinedFeatures().contains(feature1));
    }
    
    /**
     * Test case to see if features has been set manual
     * Assert statement checks if feature1 has been selected and then unselected
     */
    @Test
    void testSetManualSelection() {
        featureConfiguration.setManual(feature1, Selection.SELECTED);
        assertTrue(featureConfiguration.getSelectedFeatures().contains(feature1));
        featureConfiguration.setManual(feature1, Selection.UNSELECTED);
        assertTrue(featureConfiguration.getUnselectedFeatures().contains(feature1));
        featureConfiguration.resetManual(feature1);
        assertTrue(featureConfiguration.getUndefinedFeatures().contains(feature1));
    }

    /**
     * Test case to see if feature has been set automatic
     * Assert statement checks if feature2 has been selected and then unselected
     */
    @Test
    void testSetAutomaticSelection() {
        featureConfiguration.setAutomatic(feature2, Selection.SELECTED);
        assertTrue(featureConfiguration.getSelectedFeatures().contains(feature2));
        featureConfiguration.setAutomatic(feature2, Selection.UNSELECTED);
        assertTrue(featureConfiguration.getUnselectedFeatures().contains(feature2));
        featureConfiguration.resetAutomatic(feature2);
        assertTrue(featureConfiguration.getUndefinedFeatures().contains(feature2));
    }
}
