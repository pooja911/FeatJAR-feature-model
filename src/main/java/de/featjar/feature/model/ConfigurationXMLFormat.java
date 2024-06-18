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

public class ConfigurationXMLFormat {
    private Map<String, Object> features;
    private Map<String, SelectableFeature> selectableFeatures;

    public ConfigurationXMLFormat() {
        this.features = new HashMap<>();
        this.selectableFeatures = new HashMap<>();
    }

    public void addFeature(String name, Object value, SelectableFeature feature) {
        this.features.put(name, value);
        this.selectableFeatures.put(name, feature);
    }

    public Map<String, Object> getFeatures() {
        return this.features;
    }

    public Map<String, SelectableFeature> getSelectableFeatures() {
        return this.selectableFeatures;
    }

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

    // Override parseDocument method
    protected Document parseDocument(File file) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }

    // Override createDocument method
    protected Document createDocument() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.newDocument();
    }

    // Override writeDocument method
    protected void writeDocument(Document doc, String filePath) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }

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

        config.saveToXML("Featuremodelconfig1.xml");

        ConfigurationXMLFormat loadedConfig = new ConfigurationXMLFormat();
        loadedConfig.loadFromXML("Featuremodelconfig1.xml");
        System.out.println(loadedConfig.getFeatures());
        System.out.println(loadedConfig.getSelectableFeatures());
    }
}


