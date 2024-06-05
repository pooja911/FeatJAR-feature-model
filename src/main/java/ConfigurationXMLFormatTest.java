import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test //Add and get the feature
    public void testAddAndGetFeature() {
        config.addFeature("Feature1", "Value1");
        assertEquals("Value1", config.getFeature("Feature1"));
    }

    @Test //check whether features are stored in the file or not
    public void testSaveToXML() { 
        config.addFeature("Feature1", "Value1");
        config.addFeature("Feature2", "Value2");

        config.saveToXML(TEST_XML_FILE);

        File file = new File(TEST_XML_FILE);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }


    @Test //feature can get from xml
    public void testLoadNonExistentXMLFile() {
        ConfigurationXMLFormat loadedConfig = new ConfigurationXMLFormat();
        assertDoesNotThrow(() -> loadedConfig.loadFromXML("config.xml"));
        assertTrue(loadedConfig.getFeatures().isEmpty());
    }

    @Test //feature with an existing name overwrites the previous value.
    public void testOverwriteExistingFeature() {
        config.addFeature("Feature1", "Value1");
        config.addFeature("Feature1", "Value2");

        assertEquals("Value2", config.getFeature("Feature1"));
    }

    @Test //handling of an empty xml file 
    public void testLoadFromEmptyXMLFile() throws IOException {
        Files.writeString(new File(TEST_XML_FILE).toPath(), "");

        ConfigurationXMLFormat loadedConfig = new ConfigurationXMLFormat();
        assertDoesNotThrow(() -> loadedConfig.loadFromXML(TEST_XML_FILE));
        assertTrue(loadedConfig.getFeatures().isEmpty());
    }

    @Test //feature can be removed 
    public void testRemoveFeature() {
        config.addFeature("Feature1", "Value1");
        config.getFeatures().remove("Feature1");

        assertNull(config.getFeature("Feature1"));
    }

   

    
}
