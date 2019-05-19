//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.19 at 09:19:48 PM EEST 
//


package com.dimipet.excelmerger.properties;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.dimipet.excelmerger.properties package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.dimipet.excelmerger.properties
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExcelFiles }
     * 
     */
    public ExcelFiles createExcelFiles() {
        return new ExcelFiles();
    }

    /**
     * Create an instance of {@link ExcelFiles.InputFile }
     * 
     */
    public ExcelFiles.InputFile createExcelFilesInputFile() {
        return new ExcelFiles.InputFile();
    }

    /**
     * Create an instance of {@link ExcelFiles.InputFile.Content }
     * 
     */
    public ExcelFiles.InputFile.Content createExcelFilesInputFileContent() {
        return new ExcelFiles.InputFile.Content();
    }

    /**
     * Create an instance of {@link ExcelFiles.InputFile.Header }
     * 
     */
    public ExcelFiles.InputFile.Header createExcelFilesInputFileHeader() {
        return new ExcelFiles.InputFile.Header();
    }

    /**
     * Create an instance of {@link ExcelFiles.OutputFile }
     * 
     */
    public ExcelFiles.OutputFile createExcelFilesOutputFile() {
        return new ExcelFiles.OutputFile();
    }

    /**
     * Create an instance of {@link ExcelFiles.InputFile.Content.Autoresize }
     * 
     */
    public ExcelFiles.InputFile.Content.Autoresize createExcelFilesInputFileContentAutoresize() {
        return new ExcelFiles.InputFile.Content.Autoresize();
    }

    /**
     * Create an instance of {@link ExcelFiles.InputFile.Content.Rowsheights }
     * 
     */
    public ExcelFiles.InputFile.Content.Rowsheights createExcelFilesInputFileContentRowsheights() {
        return new ExcelFiles.InputFile.Content.Rowsheights();
    }

    /**
     * Create an instance of {@link ExcelFiles.InputFile.Header.Autoresize }
     * 
     */
    public ExcelFiles.InputFile.Header.Autoresize createExcelFilesInputFileHeaderAutoresize() {
        return new ExcelFiles.InputFile.Header.Autoresize();
    }

}
