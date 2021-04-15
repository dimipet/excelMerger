package com.dimipet.excelmerger.properties;

import com.dimipet.excelmerger.App;
import com.dimipet.excelmerger.util.AppProperties;
import com.dimipet.excelmerger.util.XMLValidator;
import java.util.Properties;

public class XMLPropertiesValidator {
    
    private static final String SCHEMA_FILE = "com/dimipet/excelmerger/application.properties.xsd";
    
    public boolean validate(){
        Properties prop = AppProperties.getInstance();
        XMLValidator XMLValidator = new XMLValidator();
        
        String appPropFileVal = null;
        if(prop.containsKey("application.properties.file")) {
        	appPropFileVal = prop.getProperty("application.properties.file");
        } else {
        	appPropFileVal = App.SAMPLE_APP_PROPEPTIES_FILE;
        }
        return XMLValidator.validate(appPropFileVal, SCHEMA_FILE); 
    }
    
    
}
