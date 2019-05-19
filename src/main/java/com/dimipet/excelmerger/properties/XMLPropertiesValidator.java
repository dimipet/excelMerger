package com.dimipet.excelmerger.properties;

import com.dimipet.excelmerger.util.XMLValidator;

public class XMLPropertiesValidator {
    public static final String XML_FILE = "application.properties.xml";
    public static final String SCHEMA_FILE = "com/dimipet/excelmerger/application.properties.xsd";
    
    public boolean validate(){
        XMLValidator XMLValidator = new XMLValidator();
        return XMLValidator.validate(XML_FILE, SCHEMA_FILE); 
    }
    
    
}
