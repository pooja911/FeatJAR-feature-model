package de.featjar.feature.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import de.featjar.base.io.format.IFormat;
import de.featjar.base.io.format.ParseException;
import de.featjar.base.io.xml.AXMLFormat;

/**
 * Manages a configuration of features stored in an XML format. 
 * Allows adding, retrieving, saving, and loading features to and from an XML file.
 * This class extends {@link AXMLFormat} and implements {@link IFormat} for handling 
 * XML formatting and feature model configuration.
 * 
 * Features are represented as elements within a "FeatureModelConfiguration" XML structure.
 * Each feature element contains attributes for name, manual selection state, and automatic 
 * selection state, along with additional metadata such as type.
 */
public class ConfigurationXMLFormat extends AXMLFormat<FeatureModelConfiguration> implements IFormat<FeatureModelConfiguration> {
    private Map<String, Object> features = new HashMap<>();
    private Map<String, SelectableFeature> selectableFeatures = new HashMap<>();

    private final FeatureModel featureModel;

    /**
     * Constructs a new ConfigurationXMLFormat instance with a specified feature model.
     *
     * @param featureModel The feature model associated with this configuration format.
     */
    public ConfigurationXMLFormat(FeatureModel featureModel) {
        this.featureModel = featureModel;
    }

    /**
     * Adds a feature to the configuration map.
     *
     * @param name The name of the feature.
     * @param value The value associated with the feature.
     * @param feature The SelectableFeature object representing the feature.
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
     * Parses an XML document into a {@link FeatureModelConfiguration} object.
     *
     * @param document The XML document to parse.
     * @return A configured FeatureModelConfiguration object.
     * @throws ParseException If an error occurs during parsing.
     */
    @Override
    protected FeatureModelConfiguration parseDocument(Document document) throws ParseException {
        Element root = getDocumentElement(document, "FeatureModelConfiguration");

        FeatureModelConfiguration config = new FeatureModelConfiguration(featureModel);

        try {
            NodeList featureNodes = document.getElementsByTagName("feature");

            for (int i = 0; i < featureNodes.getLength(); i++) {
                Element featureElement = (Element) featureNodes.item(i);
                String name = featureElement.getAttribute("name");
                String type = featureElement.getElementsByTagName("type").item(0).getTextContent();
                String value = featureElement.getElementsByTagName("value").item(0).getTextContent();
                SelectionType manual = featureElement.hasAttribute("manual") ? SelectionType.valueOf(featureElement.getAttribute("manual").toUpperCase()) : null;
                SelectionType automatic = featureElement.hasAttribute("automatic") ? SelectionType.valueOf(featureElement.getAttribute("automatic").toUpperCase()) : null;

                Object typedValue;
                switch (type) {
                    case "Double":
                        typedValue = Double.parseDouble(name);
                        break;
                    case "Float":
                        typedValue = Float.parseFloat(name);
                        break;
                    case "Integer":
                        typedValue = Integer.parseInt(name);
                        break;
                    case "Boolean":
                        typedValue = Boolean.parseBoolean(name);
                        break;
                    case "String":
                    default:
                        typedValue = value;
                        break;
                }
                SelectableFeature selectableFeature = new SelectableFeature(name);
                if (manual != null) {
                    if (manual == SelectionType.MANUAL) {
                        config.setManual(name, Selection.SELECTED);
                    }
                } else {
                    config.setManual(name, Selection.UNDEFINED);
                }

                if (automatic != null) {
                    if (automatic == SelectionType.AUTOMATIC) {
                        config.setAutomatic(name, Selection.SELECTED);
                    }
                } else {
                    config.setAutomatic(name, Selection.UNDEFINED);
                }

                addFeature(name, typedValue, selectableFeature);
            }

        } catch (Exception e) {
            FeatJarLogger.logError("Exception occured please check log file");
        }
        return config;
    }

    /**
     * Writes a {@link FeatureModelConfiguration} object to an XML document.
     *
     * @param config The FeatureModelConfiguration object to write.
     * @param doc The XML Document object to write to.
     */
    @Override
    protected void writeDocument(FeatureModelConfiguration config, Document doc) {
        try {
            Element rootElement = doc.createElement("FeatureModelConfiguration");
            doc.appendChild(rootElement);

            for (String name : config.getAllFeatures()) {
                Element featureElement = doc.createElement("feature");

                featureElement.setAttribute("name", name);
                if (config.isManualSelected(name)) {
                    featureElement.setAttribute("manual", SelectionType.MANUAL.name().toLowerCase());
                }
                if (config.isAutomaticSelected(name)) {
                    featureElement.setAttribute("automatic", SelectionType.AUTOMATIC.name().toLowerCase());
                }

                Element typeElement = doc.createElement("type");
                typeElement.appendChild(doc.createTextNode(this.features.get(name).getClass().getSimpleName()));
                featureElement.appendChild(typeElement);

                rootElement.appendChild(featureElement);

            }

        } catch (Exception e) {
            FeatJarLogger.logError("Exception occured please check log file");
        }
    }
    
    /**
     * Gets the name of this XML format configuration.
     *
     * @return The name of the XML format configuration.
     */
    @Override
    public String getName() {
        return "ConfigurationXMLFormat";
    }

    /**
     * Retrieves the input header pattern used for this XML format configuration.
     *
     * @return The input header pattern, or null if not applicable.
     */

    @Override
    protected Pattern getInputHeaderPattern() {
        return null;
    }
}
