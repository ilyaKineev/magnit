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
import java.io.*;
import java.util.List;
import java.util.logging.Logger;


public class MagnitBean implements Serializable {

    private static Logger log = Logger.getLogger(MagnitBean.class.getName());

    private static final String INPUT_FILE_XML = "1.xml";
    private static final String OUTPUT_FILE_XML = "2.xml";
    private static final String INPUT_FILE_XSL = "src/main/resources/file.xsl";

    private String URL;
    private String username;
    private String password;
    private int N;

    public MagnitBean() {
    }

    public void setURL(String URL) {
        log.info("Set URL: " + URL);
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void setUsername(String username) {
        log.info("Set username: " + username);
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getN() {
        return N;
    }

    public void setN(int n) {
        log.info("Set N: " + N);
        N = n;
    }

    /**
     * The method updates the connected database, if it contains data, it resets it.
     */
    public void updateBase() {
        log.info("Start update SQL");
        ServiceEntity serviceEntity = new ServiceEntity();
        if (serviceEntity.countRowsTest() > 0) {
            serviceEntity.deleteAll();
        }
        for(int i = 1 ; i <= N ; i++){
            serviceEntity.saveTest(new EntityTest(i));
        }
        log.info("Start update SQL is finished");
    }

    /**
     * The method loads data from the database
     * @return - Returns a list of Test objects
     */
    public List<EntityTest> loadBase() {
        ServiceEntity serviceEntity = new ServiceEntity();
        log.info("Load base is finished");
        return serviceEntity.findAllTest();
    }

    /**
     * Creates an XML file
     * @param N - List of received objects of the Test type
     */
    public static void creatXmlFile(List<EntityTest> N){
        log.info("Start create file 1.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            document = factory.newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            log.info("Error:" + e.getMessage());
        }
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
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            log.info("Error:" + e.getMessage());
        }
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        try {
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(INPUT_FILE_XML)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.info("Error:" + e.getMessage());
        }
        catch (TransformerException e) {
            e.printStackTrace();
            log.info("Error:" + e.getMessage());
        }
        log.info("Start create file 1.xml is finished");
    }

    /**
     * The method converts 1.xml to 2.xml using the file.xsl template
     */
    public static void transformations() {
        log.info("Start file transformation 1.xml");
        Source inputXml = new StreamSource(new File(INPUT_FILE_XML));
        Source inputXsl = new StreamSource(new File(INPUT_FILE_XSL));
        Result outputXml = new StreamResult(new File(OUTPUT_FILE_XML));
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer(inputXsl);
            transformer.transform(inputXml, outputXml);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            log.info("Error:" + e.getMessage());
        } catch (TransformerException e) {
            e.printStackTrace();
            log.info("Error:" + e.getMessage());
        }
        log.info("Start file transformation 1.xml is completed, created is file 2.xml");
    }

    /**
     * The method determines the number of "field" attributes in the 2.xml file
     * @return - type Long
     */
    public static long sumAttributes() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            log.info("Error:" + e.getMessage());
        }
        Document document = null;
        try {
            document = builder.parse(new File(OUTPUT_FILE_XML));
        } catch (SAXException e) {
            e.printStackTrace();
            log.info("Error:" + e.getMessage());
        }catch (IOException e) {
            e.printStackTrace();
            log.info("Error:" + e.getMessage());
        }
        NodeList employeeElements = document.getDocumentElement().getElementsByTagName("entry");
        int sumField = 0;
        for (int i = 0; i < employeeElements.getLength(); i++) {
            Node employee = employeeElements.item(i);
            sumField += Integer.parseInt(employee.getAttributes().getNamedItem("field").getNodeValue());
        }
        log.info("The sum of the field attributes is " + sumField);
        return sumField;
    }

}
