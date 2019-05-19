package com.dimipet.excelmerger;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.dimipet.excelmerger.controller.XLSXFileController;
import com.dimipet.excelmerger.controller.XLSXFileControllerImpl;
import com.dimipet.excelmerger.properties.ExcelFiles;
import com.dimipet.excelmerger.properties.ExcelFiles.InputFile;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger logger = Logger.getLogger( App.class.getName() ); 
    
    public static void main(String[] args) {
        
       


        XLSXFileController xcontoller = new XLSXFileControllerImpl();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ExcelFiles.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ExcelFiles excelFiles = (ExcelFiles) unmarshaller.unmarshal(new File("application.properties.xml"));

            xcontoller.prepareFile(
                    excelFiles.getOutputFile().getPath(),
                    excelFiles.getOutputFile().getWorkbook(),
                    excelFiles.getOutputFile().getHeading()
            );          

            for (InputFile inputFile : excelFiles.getInputFile()) {
                logger.log(Level.INFO, "\n\n\n" );
                logger.log(Level.INFO, inputFile.getPath() + " : parsing ..." );
                
                logger.log(Level.INFO, inputFile.getPath() + " : parsing header ..." );
                if (inputFile.getHeader().isParse()) {
                    xcontoller.mergeHeader(
                            inputFile.getPath(),
                            inputFile.getWorkbook(),
                            inputFile.getHeader().getStart(),
                            inputFile.getHeader().getEnd(),
                            inputFile.getHeader().getAutoresize().getColumns(),
                            excelFiles.getOutputFile().getPath(),
                            excelFiles.getOutputFile().getWorkbook()
                    );
                }   
                
                logger.log(Level.INFO, inputFile.getPath() + " : parsing contents ..." );
                xcontoller.mergeContent(
                        inputFile.getPath(),
                        inputFile.getWorkbook(),
                        inputFile.getContent().getStart(),
                        inputFile.getContent().getEnd(),
                        inputFile.getContent().getAutoresize().getColumns(),
                        inputFile.getContent().getRowsheights(),
                        excelFiles.getOutputFile().getPath(),
                        excelFiles.getOutputFile().getWorkbook()
                );
                
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        
    }

}
