package de.featjar.feature.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class provides methods to manage a configuration of features stored in an XML file.
 * Features can be added, retrieved, saved to an XML file, and loaded from an XML file.
 */
public class ConfigurationXMLFormat {
    private Map<String, Object> features;
    private Map<String, SelectableFeature> selectableFeatures;

    /**
     * Constructs a new ConfigurationXMLFormat object with empty feature maps.
     */
    public ConfigurationXMLFormat() {
        this.features = new HashMap<>();
        this.selectableFeatures = new HashMap<>();
    }

    /**
     * Adds a feature to the configuration.
     *
     * @param name The name of the feature.
     * @param value The value of the feature.
     * @param feature The SelectableFeature object associated with the feature.
     */
    public void addFeature(String name, Object value, SelectableFeature feature) {
        this.features.put(name, value);
        this.selectableFeatures.put(name, feature);
    }

    /**
     * Retrieves the map of features.
     *
     * @return A map containing the features.
     */
    public Map<String, Object> getFeatures() {
        return this.features;
    }

    /**
     * Retrieves the map of selectable features.
     *
     * @return A map containing the selectable features.
     */
    public Map<String, SelectableFeature> getSelectableFeatures() {
        return this.selectableFeatures;
    }

    /**
     * Loads the configuration from an XML file.
     *
     * @param filePath The path to the XML file.
     */
    public void loadFromXML(String filePath) {
        try {
            File file = new File(filePath);
            Document doc = parseDocument(file);

            NodeList featureNodes = doc.getElementsByTagName("feature");

            for (int i = 0; i < featureNodes.getLength(); i++) {
                Element featureElement = (Element) featureNodes.item(i);
                String name = featureElement.getAttribute("name");
                String type = featureElement.getElementsByTagName("type").item(0).getTextContent();
                String value = featureElement.getElementsByTagName("value").item(0).getTextContent();
                SelectionType manual = featureElement.hasAttribute("manual") ?
                        SelectionType.valueOf(featureElement.getAttribute("manual").toUpperCase()) :
                        SelectionType.UNDEFINED;
                SelectionType automatic = featureElement.hasAttribute("automatic") ?
                        SelectionType.valueOf(featureElement.getAttribute("automatic").toUpperCase()) :
                        SelectionType.UNDEFINED;

                Object typedValue;
                switch (type) {
                    case "Double":
                        typedValue = Double.parseDouble(value);
                        break;
                    case "Float":
                        typedValue = Float.parseFloat(value);
                        break;
                    case "Integer":
                        typedValue = Integer.parseInt(value);
                        break;
                    case "Boolean":
                        typedValue = Boolean.parseBoolean(value);
                        break;
                    case "String":
                    default:
                        typedValue = value;
                        break;
                }

                SelectableFeature selectableFeature = new SelectableFeature(name);
                selectableFeature.setManual(manual);
                selectableFeature.setAutomatic(automatic);
                this.features.put(name, typedValue);
                this.selectableFeatures.put(name, selectableFeature);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the configuration to an XML file.
     *
     * @param filePath The path to the XML file.
     */
    public void saveToXML(String filePath) {
        try {
            Document doc = createDocument();

            // Root element
            Element rootElement = doc.createElement("configuration");
            doc.appendChild(rootElement);

            // Features
            for (String name : this.features.keySet()) {
                Element featureElement = doc.createElement("feature");

                featureElement.setAttribute("name", name);
                featureElement.setAttribute("manual", this.selectableFeatures.get(name).getManual() != null ?
                        this.selectableFeatures.get(name).getManual().name().toLowerCase() : "undefined");
                featureElement.setAttribute("automatic", this.selectableFeatures.get(name).getAutomatic() != null ?
                        this.selectableFeatures.get(name).getAutomatic().name().toLowerCase() : "undefined");

                Element typeElement = doc.createElement("type");
                typeElement.appendChild(doc.createTextNode(this.features.get(name).getClass().getSimpleName()));
                featureElement.appendChild(typeElement);

                Element valueElement = doc.createElement("value");
                valueElement.appendChild(doc.createTextNode(this.features.get(name).toString()));
                featureElement.appendChild(valueElement);

                rootElement.appendChild(featureElement);
            }

            writeDocument(doc, filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses an XML file into a Document object.
     *
     * @param file The XML file to parse.
     * @return A Document object representing the parsed XML file.
     * @throws Exception if an error occurs while parsing the XML file.
     */
    protected Document parseDocument(File file) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }

    /**
     * Creates a new Document object.
     *
     * @return A new Document object.
     * @throws Exception if an error occurs while creating the Document object.
     */
    protected Document createDocument() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.newDocument();
    }

    /**
     * Writes a Document object to an XML file.
     *
     * @param doc The Document object to write.
     * @param filePath The path to the XML file.
     * @throws Exception if an error occurs while writing the XML file.
     */
    protected void writeDocument(Document doc, String filePath) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }

    /**
     * Main method for testing the ConfigurationXMLFormat class.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        ConfigurationXMLFormat config = new ConfigurationXMLFormat();

        SelectableFeature feature1 = new SelectableFeature("Feature1");
        feature1.setManual(SelectionType.MANUAL);
        feature1.setAutomatic(SelectionType.AUTOMATIC);
        config.addFeature("Feature1", "StringValue", feature1);

        SelectableFeature feature2 = new SelectableFeature("Feature2");
        feature2.setManual(SelectionType.MANUAL);
        feature2.setAutomatic(SelectionType.AUTOMATIC);
        config.addFeature("Feature2", 43, feature2);

        SelectableFeature feature3 = new SelectableFeature("Feature3");
        feature3.setManual(SelectionType.MANUAL);
        feature3.setAutomatic(SelectionType.AUTOMATIC);
        config.addFeature("Feature3", 123.45, feature3);

        config.saveToXML("Featuremodelconfig.xml");

        ConfigurationXMLFormat loadedConfig = new ConfigurationXMLFormat();
        loadedConfig.loadFromXML("Featuremodelconfig.xml");
        System.out.println(loadedConfig.getFeatures());
        System.out.println(loadedConfig.getSelectableFeatures());
    }
}