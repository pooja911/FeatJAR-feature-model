package de.featjar.feature.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import de.featjar.base.data.identifier.IIdentifier;



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
 *
 * A nested TestFeature class is used as a mock implementation of the IFeature interface for testing purposes.
 * 
 * @see FeatureModelConfiguration
 * @see IFeature
 * @see SelectableFeature
 * @see SelectionType
 * 
 * Author: Pooja Garg
 */
class FeatureConfigurationTest {
	private FeatureModelConfiguration featureConfiguration;
	private FeatureModel featureModel;
	private String featureId1;
	private String featureId2;
	private IFeature feature1;
	private IFeature feature2;

	@BeforeEach
	void setUp() {
	    featureModel = new FeatureModel();
	    feature1 = featureModel.addFeature("Feature1");
		feature2 = featureModel.addFeature("Feature2");
		featureId1 = feature1.getName().orElseThrow();
		featureId2 = feature2.getName().orElseThrow();
	    featureConfiguration = new FeatureModelConfiguration(featureModel);
	    System.out.println();
	}
	
	
	@Test
	void testAddFeature() {
	    assertEquals(2, featureConfiguration.getSelectedFeatures().size() +
	            featureConfiguration.getUnselectedFeatures().size() +
	            featureConfiguration.getUndefinedFeatures().size());
	}

	@Test
	void testRemoveFeature() {
	    featureConfiguration.removeFeature(featureId1);
	    assertFalse(featureConfiguration.getSelectedFeatures().contains(featureId1));
	    assertFalse(featureConfiguration.getUnselectedFeatures().contains(featureId1));
	    assertFalse(featureConfiguration.getUndefinedFeatures().contains(featureId1));
	}

	@Test
	void testSetManualSelection() {
	    featureConfiguration.setManual(featureId1, SelectionType.SELECTED);
	    assertTrue(featureConfiguration.getSelectedFeatures().contains(featureId1));
	    featureConfiguration.setManual(featureId1, SelectionType.UNSELECTED);
	    assertTrue(featureConfiguration.getUnselectedFeatures().contains(featureId1));
	    featureConfiguration.resetManual(featureId1);
	    assertTrue(featureConfiguration.getUndefinedFeatures().contains(featureId1));
	}

	@Test
	void testSetAutomaticSelection() {
	    featureConfiguration.setAutomatic(featureId2, SelectionType.SELECTED);
	    assertTrue(featureConfiguration.getSelectedFeatures().contains(featureId2));
	    featureConfiguration.setAutomatic(featureId2, SelectionType.UNSELECTED);
	    assertTrue(featureConfiguration.getUnselectedFeatures().contains(featureId2));
	    featureConfiguration.resetAutomatic(featureId2);
	    assertTrue(featureConfiguration.getUndefinedFeatures().contains(featureId2));
	}


}
