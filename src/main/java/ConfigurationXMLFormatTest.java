import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

import de.featjar.feature.model.SelectionType;

public class ConfigurationXMLFormatTest {

    private static final String TEST_XML_FILE = "config.xml";
    private ConfigurationXMLFormat config;

    @BeforeEach
    public void setUp() {
        config = new ConfigurationXMLFormat();
    }

    @AfterEach
    public void tearDown() {
        File file = new File(TEST_XML_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAddAndGetFeature() {
        config.addFeature("Feature1", "Value1", SelectionType.MANUAL, SelectionType.AUTOMATIC);
        Object value = config.getFeatures().get("Feature1");
        SelectionType manual = config.getManualSelections().get("Feature1");
        SelectionType automatic = config.getAutomaticSelections().get("Feature1");

        assertNotNull(value);
        assertEquals("Value1", value);
        assertEquals(SelectionType.MANUAL, manual);
        assertEquals(SelectionType.AUTOMATIC, automatic);
    }

    @Test
    public void testSaveToXML() {
        config.addFeature("Feature1", "Value1", SelectionType.MANUAL, SelectionType.AUTOMATIC);
        config.addFeature("Feature2", 42.0, SelectionType.MANUAL, SelectionType.AUTOMATIC);
        config.addFeature("Feature3", true, SelectionType.MANUAL, SelectionType.AUTOMATIC);

        config.saveToXML(TEST_XML_FILE);

        File file = new File(TEST_XML_FILE);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void testLoadFromXML() {
        config.addFeature("Feature1", "Value1", SelectionType.MANUAL, SelectionType.AUTOMATIC);
        config.addFeature("Feature2", 42.0, SelectionType.MANUAL, SelectionType.AUTOMATIC);
        config.addFeature("Feature3", true, SelectionType.MANUAL, SelectionType.AUTOMATIC);
        config.saveToXML(TEST_XML_FILE);

        ConfigurationXMLFormat loadedConfig = new ConfigurationXMLFormat();
        loadedConfig.loadFromXML(TEST_XML_FILE);

        Map<String, Object> features = loadedConfig.getFeatures();
        Map<String, SelectionType> manualSelections = loadedConfig.getManualSelections();
        Map<String, SelectionType> automaticSelections = loadedConfig.getAutomaticSelections();

        assertEquals("Value1", features.get("Feature1"));
        assertEquals(42.0, features.get("Feature2"));
        assertEquals(true, features.get("Feature3"));

        assertEquals(SelectionType.MANUAL, manualSelections.get("Feature1"));
        assertEquals(SelectionType.AUTOMATIC, automaticSelections.get("Feature1"));
    }

    @Test
    public void testOverwriteExistingFeature() {
        config.addFeature("Feature1", "Value1", SelectionType.MANUAL, SelectionType.AUTOMATIC);
        config.addFeature("Feature1", "NewValue", SelectionType.AUTOMATIC, SelectionType.MANUAL);

        Object value = config.getFeatures().get("Feature1");
        SelectionType manual = config.getManualSelections().get("Feature1");
        SelectionType automatic = config.getAutomaticSelections().get("Feature1");

        assertNotNull(value);
        assertEquals("NewValue", value);
        assertEquals(SelectionType.AUTOMATIC, manual);
        assertEquals(SelectionType.MANUAL, automatic);
    }

    @Test
    public void testLoadNonExistentXMLFile() {
        ConfigurationXMLFormat loadedConfig = new ConfigurationXMLFormat();
        assertDoesNotThrow(() -> loadedConfig.loadFromXML("nonexistent.xml"));
        assertTrue(loadedConfig.getFeatures().isEmpty());
    }

    @Test
    public void testLoadFromEmptyXMLFile() throws IOException {
        Files.writeString(new File(TEST_XML_FILE).toPath(), "");

        ConfigurationXMLFormat loadedConfig = new ConfigurationXMLFormat();
        assertDoesNotThrow(() -> loadedConfig.loadFromXML(TEST_XML_FILE));
        assertTrue(loadedConfig.getFeatures().isEmpty());
    }

    
}
