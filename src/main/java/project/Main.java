package project;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import project.model.EntityTest;
import project.service.ServiceEntity;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        // Входные данные (необходимо понять как загрузить параметры для подключения базы данных через класс)
        // jdbc:postgresql://localhost:5432/TEST
        String URL = "jdbc:postgresql://localhost:5432/TEST";
        // root
        String username = "root";
        // password
        String password = "password";
        // N = 50
        int N = 100; // переделать на список

        start(URL , username, password ,N);
    }

    private static void start(String url, String username, String password, int n) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        final String INPUT_FILE_XML = "1.xml";
        final String OUTPUT_FILE_XML = "2.xml";
        final String INPUT_FILE_XSL = "file.xsl";

        creatXmlFile(INPUT_FILE_XML, updateSQL(n));
        transformations(INPUT_FILE_XML, OUTPUT_FILE_XML, INPUT_FILE_XSL);
        System.out.println(sumAttributes(OUTPUT_FILE_XML));
    }

    private static List<EntityTest> updateSQL(int N) {
        ServiceEntity serviceEntity = new ServiceEntity();
        if (serviceEntity.countRowsTest() > 0) {
            serviceEntity.deleteAll();
        }
        for(int i = 1 ; i <= N ; i++){
            serviceEntity.saveTest(new EntityTest(i));
        }
    return serviceEntity.findAllTest();
    }

    private static void creatXmlFile(String inputFileXml, List<EntityTest> N) throws ParserConfigurationException, IOException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = factory.newDocumentBuilder().newDocument();
        Element entries = document.createElement("entries");
        document.appendChild(entries);
        for (int i = 0; i < N.size(); i++) {
            Element entry = document.createElement("entry");
            entries.appendChild(entry);
            Element field = document.createElement("field");
            entry.appendChild(field);
            Text text = document.createTextNode("text");
            field.appendChild(text);
            text.setTextContent(String.valueOf(N.get(i).getField()));
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(inputFileXml)));
    }

    private static void transformations(String pathXmlFileOne, String pathXmlFileTwo, String pathXslFile) throws TransformerException {
        Source inputXml = new StreamSource(new File(pathXmlFileOne));
        Source inputXsl = new StreamSource(new File(pathXslFile));
        Result outputXml = new StreamResult(new File(pathXmlFileTwo));
        Transformer transformer = TransformerFactory.newInstance().newTransformer(inputXsl);
        transformer.transform(inputXml, outputXml);
    }

    private static int sumAttributes(String outputFileXml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(outputFileXml));
        NodeList employeeElements = document.getDocumentElement().getElementsByTagName("entry");
        int sumField = 0;
        for (int i = 0; i < employeeElements.getLength(); i++) {
            Node employee = employeeElements.item(i);
            sumField += Integer.parseInt(employee.getAttributes().getNamedItem("field").getNodeValue());
        }
        return sumField;
    }
}