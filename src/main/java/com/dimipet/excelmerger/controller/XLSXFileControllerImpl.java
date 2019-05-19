package com.dimipet.excelmerger.controller;

import com.dimipet.excelmerger.properties.ExcelFiles.InputFile.Content.Rowsheights;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXFileControllerImpl implements XLSXFileController {
    
    @Override
    public void prepareFile(String outputFile, String workbookName, String heading) {
        FileOutputStream outputStream = null;
        Workbook outputWorkbook = null;
        Sheet outputSheet = null;
        
        outputWorkbook = new XSSFWorkbook();
        outputSheet = outputWorkbook.createSheet(workbookName);
        
        int num = outputSheet.getLastRowNum();
        Row row = null;
        row = outputSheet.createRow(num++);
        row.createCell(0).setCellValue(heading);
//        row = outputSheet.createRow(num++);
//        row.createCell(0).setCellValue("DATE / TIME");

        try {
            outputStream = new FileOutputStream(outputFile);
            outputWorkbook.write(outputStream);
            outputWorkbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void mergeHeader(
            String inFile, String inWb, String inStartCell, String inLastCell, 
            List<String> autoSizeColumns, String mergeFile, String mWb) {
        
        InputStream inputStream = null;
        XSSFWorkbook inputWorkbook = null;
        XSSFSheet inputSheet = null;
        
        FileOutputStream outputStream = null;
        
        try {
            inputStream = new FileInputStream(inFile);
            inputWorkbook = new XSSFWorkbook(OPCPackage.open(inputStream));
            inputSheet = inputWorkbook.getSheet(inWb);
            
            InputStream mergeStream = new FileInputStream(mergeFile);
            Workbook mergeWorkbook = WorkbookFactory.create(mergeStream);
            Sheet mergeSheet = mergeWorkbook.getSheet(mWb);

            //CellReference ( row column ) 
            //CellReference ( 27 A ) 
            CellReference inStartCellRef = new CellReference(inStartCell);
            String endCell = inLastCell;
            CellReference inEndCellRef = new CellReference(endCell);
            System.out.println("### reading from " + inStartCellRef.toString() + "-" + inEndCellRef.toString());
            
            System.out.println("### lastRowNum of out.xlsx [0-based] " + mergeSheet.getLastRowNum());
            
            CellReference outStartCellRef = new CellReference(mergeSheet.getLastRowNum() + 1, mergeSheet.getLeftCol());
            System.out.println("### will write to " + outStartCellRef.toString());
            
            copier(inputSheet, inStartCellRef, inEndCellRef, mergeWorkbook, mergeSheet, outStartCellRef);
            columnAutoSizer(mergeSheet, autoSizeColumns);
            
            outputStream = new FileOutputStream(mergeFile);
            mergeWorkbook.write(outputStream);
            mergeWorkbook.close();
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(XLSXFileControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void mergeContent(
            String inFile, String inWb, String inStartCell, String inLastColumn, 
            List<String> autoSizeColumns, List<Rowsheights> rowsHeights,
            String mergeFile, String mWb) {
        
        InputStream inputStream = null;
        XSSFWorkbook inputWorkbook = null;
        XSSFSheet inputSheet = null;
        
        FileOutputStream outputStream = null;
        
        try {
            inputStream = new FileInputStream(inFile);
            inputWorkbook = new XSSFWorkbook(OPCPackage.open(inputStream));
            inputSheet = inputWorkbook.getSheet(inWb);
            
            InputStream mergeStream = new FileInputStream(mergeFile);
            Workbook mergeWorkbook = WorkbookFactory.create(mergeStream);
            Sheet mergeSheet = mergeWorkbook.getSheet(mWb);

            //CellReference ( row column ) 
            //CellReference ( 27 A ) 
            CellReference inStartCellRef = new CellReference(inStartCell);
            String endCell = inLastColumn + "" + (inputSheet.getLastRowNum() + 1);
            CellReference inEndCellRef = new CellReference(endCell);
            System.out.println("### reading from " + inStartCellRef.toString() + "-" + inEndCellRef.toString());
            
            System.out.println("### lastRowNum of out.xlsx [0-based] " + mergeSheet.getLastRowNum());
            
            CellReference outStartCellRef = new CellReference(mergeSheet.getLastRowNum() + 1, mergeSheet.getLeftCol());
            System.out.println("### will write to " + outStartCellRef.toString());
            
            copier(inputSheet, inStartCellRef, inEndCellRef, mergeWorkbook, mergeSheet, outStartCellRef);
            
            rowHeightSetter(mergeSheet, rowsHeights);
            columnAutoSizer(mergeSheet, autoSizeColumns);
            
            outputStream = new FileOutputStream(mergeFile);
            mergeWorkbook.write(outputStream);
            mergeWorkbook.close();
            
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(XLSXFileControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void copier(Sheet inputSheet, CellReference inStartCellRef, CellReference inEndCellRef, Workbook mergeWorkbook, Sheet mergeSheet, CellReference outStartCellRef) {
        
        int outRowIdx = 0;
        int outColIdx = 0;
        
        for (int rowIdx = inStartCellRef.getRow(); rowIdx <= inEndCellRef.getRow(); rowIdx++) {
            
            Row inRow = inputSheet.getRow(rowIdx);
            Row outRow = mergeSheet.createRow(outStartCellRef.getRow() + outRowIdx++);
            outColIdx = 0;
            
            for (int cellIdx = inStartCellRef.getCol(); cellIdx <= inEndCellRef.getCol(); cellIdx++) {
                
                Cell inCell = inRow.getCell(cellIdx);
                Cell outCell = outRow.createCell(outStartCellRef.getCol() + outColIdx++);
                
                CellType inCellType = inCell.getCellType();
                
                if (inCellType == CellType.STRING) {
                    //System.out.println(inCell.getStringCellValue());
                    outCell.setCellValue(inCell.getStringCellValue());
                    cellStyleCloner(inCell, outCell, mergeWorkbook);
                } else if (inCellType == CellType.NUMERIC) {
                    if (DateUtil.isCellDateFormatted(inCell)) {
                        //System.out.println(inCell.getDateCellValue());
                        outCell.setCellValue(inCell.getDateCellValue());
                        cellStyleCloner(inCell, outCell, mergeWorkbook);
                    } else {
                        //System.out.println(inCell.getNumericCellValue());
                        outCell.setCellValue(inCell.getNumericCellValue());
                        cellStyleCloner(inCell, outCell, mergeWorkbook);
                    }
                } else if (inCellType == CellType.BOOLEAN) {
                    //System.out.println(inCell.getBooleanCellValue());
                    outCell.setCellValue(inCell.getBooleanCellValue());
                    cellStyleCloner(inCell, outCell, mergeWorkbook);
                } else if (inCellType == CellType.FORMULA) {
                    //System.out.println(inCell.getCellFormula());
                    outCell.setCellValue(inCell.getCellFormula());
                    cellStyleCloner(inCell, outCell, mergeWorkbook);
                } else if (inCellType == CellType.BLANK) {
                    //System.out.println("");
                    outCell.setCellValue("");
                    cellStyleCloner(inCell, outCell, mergeWorkbook);
                }
            }
            
        }
    }
    
    private void cellStyleCloner(Cell inCell, Cell outCell, Workbook outputWorkbook) {     
        CellStyle origStyle = inCell.getCellStyle();
        CellStyle newStyle = outputWorkbook.createCellStyle();
        newStyle.cloneStyleFrom(origStyle);
        outCell.setCellStyle(newStyle);
    }
    
    private void columnAutoSizer(Sheet mergeSheet, List<String> autosizeColumns) {
        CellReference crf = null;
        for (String column : autosizeColumns) {
            crf = new CellReference(column + "1");
            mergeSheet.autoSizeColumn(crf.getCol());
        }
        
    }
    
    private void rowHeightSetter(Sheet mergeSheet, List<Rowsheights> rowsHeights) {
        for (Rowsheights row : rowsHeights) {
            ////TODO max row / big integer  BigInteger a = row.getRow();
           //mergeSheet.getRow(row.getRow()).setHeight((short)-1);
           mergeSheet.getRow(row.getRow()).setHeight((short)row.getHeight());
        }
        
    }
    
    @Override
    public void saveToDB(InputStream inp, String cell) {
        
    }
    
    @Override
    public void makePDF(InputStream inp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
