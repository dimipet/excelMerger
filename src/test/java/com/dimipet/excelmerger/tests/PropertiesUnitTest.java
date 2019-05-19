package com.dimipet.excelmerger.tests;

import com.dimipet.excelmerger.properties.XMLPropertiesValidator;
import static com.dimipet.excelmerger.properties.XMLPropertiesValidator.SCHEMA_FILE;
import static com.dimipet.excelmerger.properties.XMLPropertiesValidator.XML_FILE;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dimipet.excelmerger.util.XMLValidator;

public class PropertiesUnitTest {

    public PropertiesUnitTest() {
    }

    @Test
    public void testPropertiesXMLValidation() {
        XMLPropertiesValidator validator = new XMLPropertiesValidator();
        System.out.printf("%s properties XML file validation against XSD =", validator.validate());

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
