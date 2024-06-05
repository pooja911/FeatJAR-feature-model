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
    private IFeature feature1;
    private IFeature feature2;

    @BeforeEach
    void setUp() {
        featureConfiguration = new FeatureModelConfiguration();
        feature1 = new TestFeature("Feature1");
        feature2 = new TestFeature("Feature2");
        featureConfiguration.addFeature(feature1);
        featureConfiguration.addFeature(feature2);
    }

    @Test
    void testAddFeature() {
        assertEquals(2, featureConfiguration.getSelectedFeatures().size() +
                featureConfiguration.getUnselectedFeatures().size() +
                featureConfiguration.getUndefinedFeatures().size());
    }

    @Test
    void testRemoveFeature() {
        featureConfiguration.removeFeature(feature1);
        assertFalse(featureConfiguration.getSelectedFeatures().contains(feature1));
        assertFalse(featureConfiguration.getUnselectedFeatures().contains(feature1));
        assertFalse(featureConfiguration.getUndefinedFeatures().contains(feature1));
    }
    
    @Test
    void testSetManualSelection() {
        featureConfiguration.setManual(feature1, Selection.SELECTED);
        assertTrue(featureConfiguration.getSelectedFeatures().contains(feature1));
        featureConfiguration.setManual(feature1, Selection.UNSELECTED);
        assertTrue(featureConfiguration.getUnselectedFeatures().contains(feature1));
        featureConfiguration.resetManual(feature1);
        assertTrue(featureConfiguration.getUndefinedFeatures().contains(feature1));
    }

    @Test
    void testSetAutomaticSelection() {
        featureConfiguration.setAutomatic(feature2, Selection.SELECTED);
        assertTrue(featureConfiguration.getSelectedFeatures().contains(feature2));
        featureConfiguration.setAutomatic(feature2, Selection.UNSELECTED);
        assertTrue(featureConfiguration.getUnselectedFeatures().contains(feature2));
        featureConfiguration.resetAutomatic(feature2);
        assertTrue(featureConfiguration.getUndefinedFeatures().contains(feature2));
    }

    static class TestFeature implements IFeature {
        private final String name;

        TestFeature(String name) {
            this.name = name;
        }


        @Override
        public Result<IFeatureTree> getFeatureTree() {
            return Result.of(null);
        }

        @Override
        public Class<?> getType() {
            return null;
        }

        @Override
        public IFeature clone() {
            return new TestFeature(name);
        }

        @Override
        public IFeature clone(IFeatureModel newFeatureModel) {
            return new TestFeature(name);
        }

        @Override
        public boolean isAbstract() {
            return false;
        }

        @Override
        public boolean isHidden() {
            return false;
        }

        @Override
        public LinkedHashSet<IConstraint> getReferencingConstraints() {
            return new LinkedHashSet<>();
        }

        @Override
        public IFeatureModel getFeatureModel() {
            return null;
        }

        @Override
        public IMutableFeature mutate() {
            return null;
        }

		@Override
		public IIdentifier getIdentifier() {
			return null;
		}

		@Override
		public Optional<Map<IAttribute<?>, Object>> getAttributes() {
			return Optional.empty();
		}
    }
}
