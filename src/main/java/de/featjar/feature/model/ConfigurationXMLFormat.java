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
import de.featjar.feature.model.SelectionType;

public class ConfigurationXMLFormat {
    private Map<String, Object> features;
    private Map<String, SelectionType> manualSelections;
    private Map<String, SelectionType> automaticSelections;

    public ConfigurationXMLFormat() {
        this.features = new HashMap<>();
        this.manualSelections = new HashMap<>();
        this.automaticSelections = new HashMap<>();
    }

    public void addFeature(String name, Object value, SelectionType manual, SelectionType automatic) {
        this.features.put(name, value);
        this.manualSelections.put(name, manual);
        this.automaticSelections.put(name, automatic);
    }

    public Map<String, Object> getFeatures() {
        return this.features;
    }

    public Map<String, SelectionType> getManualSelections() {
        return this.manualSelections;
    }

    public Map<String, SelectionType> getAutomaticSelections() {
        return this.automaticSelections;
    }

    public void loadFromXML(String filePath) {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList featureNodes = doc.getElementsByTagName("feature");

            for (int i = 0; i < featureNodes.getLength(); i++) {
                Element featureElement = (Element) featureNodes.item(i);
                String name = featureElement.getElementsByTagName("name").item(0).getTextContent();
                String value = featureElement.getElementsByTagName("value").item(0).getTextContent();
                String type = featureElement.getElementsByTagName("type").item(0).getTextContent();
                SelectionType manual = SelectionType.valueOf(featureElement.getAttribute("manual").toUpperCase());
                SelectionType automatic = SelectionType.valueOf(featureElement.getAttribute("automatic").toUpperCase());

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

                this.features.put(name, typedValue);
                this.manualSelections.put(name, manual);
                this.automaticSelections.put(name, automatic);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToXML(String filePath) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Root element
            Element rootElement = doc.createElement("ConfigurationXMLFormat");
            doc.appendChild(rootElement);

            // Features
            for (String name : this.features.keySet()) {
                Element featureElement = doc.createElement("feature");

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(name));
                featureElement.appendChild(nameElement);

                Element valueElement = doc.createElement("value");
                valueElement.appendChild(doc.createTextNode(this.features.get(name).toString()));
                featureElement.appendChild(valueElement);

                Element typeElement = doc.createElement("type");
                typeElement.appendChild(doc.createTextNode(this.features.get(name).getClass().getSimpleName()));
                featureElement.appendChild(typeElement);

                featureElement.setAttribute("manual", this.manualSelections.get(name).name().toLowerCase());
                featureElement.setAttribute("automatic", this.automaticSelections.get(name).name().toLowerCase());

                rootElement.appendChild(featureElement);
            }

            // Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConfigurationXMLFormat config = new ConfigurationXMLFormat();
        config.addFeature("Feature1", "Value1", SelectionType.MANUAL, SelectionType.AUTOMATIC);
        config.addFeature("Feature2", 42.0, SelectionType.MANUAL, SelectionType.AUTOMATIC);
        config.addFeature("Feature3", true, SelectionType.MANUAL, SelectionType.AUTOMATIC);
        config.saveToXML("config.xml");

        ConfigurationXMLFormat loadedConfig = new ConfigurationXMLFormat();
        loadedConfig.loadFromXML("config.xml");
        System.out.println(loadedConfig.getFeatures());
        System.out.println(loadedConfig.getManualSelections());
        System.out.println(loadedConfig.getAutomaticSelections());
    }
}