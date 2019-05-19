package com.dimipet.excelmerger.controller;

import com.dimipet.excelmerger.properties.ExcelFiles.InputFile.Content.Rowsheights;
import java.io.InputStream;
import java.util.List;

public interface XLSXFileController {
    
    public void mergeHeader(String inputFile, String inputWorkbook, String inputStartCell, String inputLastColumn, List<String> autoSizeColumns, String mergeFile, String mergeWorkbook);
    
    public void mergeContent(String inputFile, String inputWorkbook, String inputStartCell, String inputLastColumn, List<String> autoSizeColumns, List<Rowsheights> rowsHeights, String mergeFile, String mergeWorkbook);
          
    public void saveToDB(InputStream inp, String cell);
    
    public void makePDF(InputStream inp);

    void prepareFile(String outputFile, String workbookName, String heading);
    
    
    
}
