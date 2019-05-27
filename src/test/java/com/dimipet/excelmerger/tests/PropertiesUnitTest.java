package com.dimipet.excelmerger.tests;

import com.dimipet.excelmerger.App;
import com.dimipet.excelmerger.properties.XMLPropertiesValidator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PropertiesUnitTest {

    private static final Logger logger = Logger.getLogger(PropertiesUnitTest.class.getName());

    public PropertiesUnitTest() {
    }

    @Test
    public void testPropertiesXMLValidation() {
        XMLPropertiesValidator validator = new XMLPropertiesValidator();
        boolean testVal = validator.validate();
        logger.log(Level.INFO, "properties XML file validation against XSD = " + testVal);
        assert testVal = true;

    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
