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
	private String featureName1="Feature1";
	private String featureName2="Feature2";

	@BeforeEach
	void setUp() {
	    featureModel = new FeatureModel();
	    featureModel.addFeature(featureName1);
		featureModel.addFeature(featureName2);
	    featureConfiguration = new FeatureModelConfiguration(featureModel);
	}
	
	@Test
	void testAddFeature() {
	    assertEquals(2, featureConfiguration.getAllFeatures().size());
	    assertFalse(featureConfiguration.getAllFeatures().contains("Feature3"));
	    featureModel.addFeature("Feature3");
	    featureConfiguration = new FeatureModelConfiguration(featureModel);
	    assertTrue(featureConfiguration.getAllFeatures().contains("Feature3"));
	    assertEquals(3, featureConfiguration.getAllFeatures().size());
	}

	@Test
	void testRemoveFeature() {
	    featureConfiguration.removeFeature(featureName1);
	    assertFalse(featureConfiguration.getAllFeatures().contains(featureName1));
	}

	@Test
	void testSetManualSelection() {
	    featureConfiguration.setManual(featureName1, SelectionType.SELECTED);
	    assertTrue(featureConfiguration.isManualSelected(featureName1));
	    featureConfiguration.setManual(featureName1, SelectionType.UNSELECTED);
	    assertFalse(featureConfiguration.isManualSelected(featureName1));
	    featureConfiguration.resetManual(featureName1);
	    assertFalse(featureConfiguration.isManualSelected(featureName1));
	}

	@Test
	void testSetAutomaticSelection() {
	    featureConfiguration.setAutomatic(featureName2, SelectionType.SELECTED);
	    assertTrue(featureConfiguration.isAutomaticSelected(featureName2));
	    featureConfiguration.setAutomatic(featureName2, SelectionType.UNSELECTED);
	    assertFalse(featureConfiguration.isAutomaticSelected(featureName2));
	    featureConfiguration.resetAutomatic(featureName2);
	    assertFalse(featureConfiguration.isAutomaticSelected(featureName2));
	}

	/*
	 * @Test void testSeeAllFeatures() {
	 * 
	 * System.out.println(featureConfiguration.getAllFeatures()); }
	 */

}
