package de.featjar.feature.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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

import de.featjar.base.io.format.IFormat;
import de.featjar.base.io.format.ParseException;
import de.featjar.base.io.xml.AXMLFormat;

/**
 * This class provides methods to manage a configuration of features stored in an XML file.
 * Features can be added, retrieved, saved to an XML file, and loaded from an XML file.
 */
public class ConfigurationXMLFormat extends AXMLFormat<FeatureModelConfiguration>  implements IFormat<FeatureModelConfiguration> {
    private Map<String, Object> features;
    private Map<String, SelectableFeature> selectableFeatures;

    /**
     * Constructs a new ConfigurationXMLFormat object with empty feature maps.
     */
    private final FeatureModel featureModel;
    public ConfigurationXMLFormat(FeatureModel featureModel) {
    this.featureModel = featureModel;}

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
                config.addFeature(name);
                config.setManual(name, manual);
                config.setAutomatic(name, automatic);
                //config.getFeatureState().put(selectableFeature); // Update featureStates directly
                //config.getFeaturesBySelection(); // Update features map
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }

    /**
     * Saves the configuration to an XML file.
     *
     * @param filePath The path to the XML file.
     */
    @Override
    protected void writeDocument(FeatureModelConfiguration object, Document doc) {
        // Implement the logic to convert the FeatureModelConfiguration object into XML elements and add to the document
        
        // Add other elements representing the configuration state
        try {
         
            Element rootElement = doc.createElement("FeatureModelConfiguration");
            doc.appendChild(rootElement);
            //writeFeatureModel(object.featureStates(), rootElement);

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
    /*protected Document parseDocument(File file) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }*/

    /**
     * Creates a new Document object.
     *
     * @return A new Document object.
     * @throws Exception if an error occurs while creating the Document object.
     */
    /*protected Document createDocument() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.newDocument();
    }*/

  

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

<<<<<<< HEAD
	/*@Override
	protected FeatureModelConfiguration parseDocument(Document document) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/*@Override
	protected void writeDocument(FeatureModelConfiguration object, Document doc) {
		// TODO Auto-generated method stub
	}*/
=======
	@Override
	protected FeatureModelConfiguration parseDocument(Document document) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void writeDocument(FeatureModelConfiguration object, Document doc) {
		// TODO Auto-generated method stub
	}
>>>>>>> 7c2849ff7aac31ce0bd97ba171f1d334d687e8bf

	@Override
	protected Pattern getInputHeaderPattern() {
		// TODO Auto-generated method stub
		return null;
	}

}
