package com.dimipet.excelmerger.properties;

import com.dimipet.excelmerger.util.AppProperties;
import com.dimipet.excelmerger.util.XMLValidator;
import java.util.Properties;

public class XMLPropertiesValidator {
    
    private static final String SCHEMA_FILE = "com/dimipet/excelmerger/application.properties.xsd";
    
    public boolean validate(){
        Properties prop = AppProperties.getInstance();
        XMLValidator XMLValidator = new XMLValidator();
        return XMLValidator.validate(prop.getProperty("application.properties.file"), SCHEMA_FILE); 
    }
    
    
}
