package project;

import junit.framework.TestCase;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class MagnitBeanTest extends TestCase {

    private MagnitBean magnitBean = new MagnitBean();
    {
        magnitBean.setN(10000);
        magnitBean.setURL("jdbc:postgresql://localhost:5432/TEST");
        magnitBean.setUsername("root");
        magnitBean.setUsername("password");
    }
    @Test
    public void testUpdateSQLN() {
        assertEquals(magnitBean.loadBase().size(), 10000);
    }

    @Test
    public void testCreatXmlFile() {
        File file = new File("C:/magnitTestNew/1.xml");
        assertTrue(!file.isDirectory() && !file.canRead());
    }

    @Test
    public void testTransformations() {
        File file = new File("C:/magnitTestNew/2.xml");
        assertTrue(!file.isDirectory() && !file.canRead());
    }

    @Test
    public void testSumAttributes() throws IOException, SAXException, ParserConfigurationException {
        assertEquals(magnitBean.sumAttributes(), 50005000);
    }

    @Test(timeout = 300000) // = 5 min
    public void testMagnitBean() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        magnitBean.updateBase();
        magnitBean.creatXmlFile(magnitBean.loadBase());
        magnitBean.transformations();
        System.out.println(magnitBean.sumAttributes());
        assertEquals(magnitBean.sumAttributes(), 50005000);
    }


}