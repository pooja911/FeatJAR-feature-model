package de.featjar.feature.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class ConfigTestCases {

    private ConfigurationXMLFormat configFormat;
    private FeatureModel featureModel;

    @BeforeEach
    void setUp() {
        featureModel = new FeatureModel();
        configFormat = new ConfigurationXMLFormat(featureModel);
        configFormat.features = new HashMap<>(); // Initialize the features map
        configFormat.selectableFeatures = new HashMap<>(); // Initialize the selectableFeatures map
    }

    @Test
    void testAddFeature() {
        SelectableFeature feature = new SelectableFeature("Feature1");
        configFormat.addFeature("Feature1", 10, feature);

        assertEquals(10, configFormat.getFeatures().get("Feature1"));
        assertEquals(feature, configFormat.getSelectableFeatures().get("Feature1"));
    }
    
    @Test
    void testGetFeatures() {
        SelectableFeature feature1 = new SelectableFeature("Feature1");
        configFormat.addFeature("Feature1", 10, feature1);

        SelectableFeature feature2 = new SelectableFeature("Feature2");
        configFormat.addFeature("Feature2", "Test", feature2);

        assertEquals(2, configFormat.getFeatures().size());
        assertEquals(10, configFormat.getFeatures().get("Feature1"));
        assertEquals("Test", configFormat.getFeatures().get("Feature2"));
    }
    
    @Test
    void testGetSelectableFeatures() {
        SelectableFeature feature1 = new SelectableFeature("Feature1");
        configFormat.addFeature("Feature1", 10, feature1);

        SelectableFeature feature2 = new SelectableFeature("Feature2");
        configFormat.addFeature("Feature2", "Test", feature2);

        assertEquals(2, configFormat.getSelectableFeatures().size());
        assertEquals(feature1, configFormat.getSelectableFeatures().get("Feature1"));
        assertEquals(feature2, configFormat.getSelectableFeatures().get("Feature2"));
    }
    
    @Test
    void testAddAndGetSelectableFeatures() {
        // Test adding and retrieving selectable features
        SelectableFeature feature1 = new SelectableFeature("Feature1");
        feature1.setManual(SelectionType.MANUAL);
        feature1.setAutomatic(SelectionType.AUTOMATIC);
        configFormat.addFeature("Feature1", 10, feature1);

        SelectableFeature retrievedFeature1 = configFormat.getSelectableFeatures().get("Feature1");

        assertEquals(SelectionType.MANUAL, retrievedFeature1.getManual());
        assertEquals(SelectionType.AUTOMATIC, retrievedFeature1.getAutomatic());
    }
    
    @Test
    void testWriteDocumentWithEmptyFeatureMaps() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            configFormat.writeDocument(null, doc);

            StringWriter writer = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            String xmlOutput = writer.toString();

            assertTrue(xmlOutput.contains("<FeatureModelConfiguration/>"), "Expected empty FeatureModelConfiguration element");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
    
    @Test
    void testAddFeatureWithNullValue() {
        // Add a feature with null value
        SelectableFeature feature1 = new SelectableFeature("Feature1");
        configFormat.addFeature("Feature1", null, feature1);

        assertNull(configFormat.getFeatures().get("Feature1"));
    }

}