package com.dimipet.excelmerger.controller;

import com.dimipet.excelmerger.properties.ExcelFiles.InputFile.Content.Rowsheights;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXFileControllerImpl implements XLSXFileController {
    
    private static final Logger logger = Logger.getLogger( XLSXFileControllerImpl.class.getName() );    
    @Override
    public void prepareFile(String outputFile, String workbookName, String heading) {
        FileOutputStream outputStream = null;
        Workbook outputWorkbook = null;
        Sheet outputSheet = null;
        
        outputWorkbook = new XSSFWorkbook();
        outputSheet = outputWorkbook.createSheet(workbookName);
        
        int num = getLastRowNum(outputSheet);// outputSheet.getLastRowNum();
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
            
            logger.log(Level.INFO, "reading from " + inStartCellRef.formatAsString() + "-" + inEndCellRef.formatAsString());
            
            logger.log(Level.INFO, "finding output's file lastRowNum [0-based] " + mergeSheet.getLastRowNum());
            
            CellReference outStartCellRef = new CellReference(mergeSheet.getLastRowNum() + 1, mergeSheet.getLeftCol());
            logger.log(Level.INFO, "will write to " + outStartCellRef.formatAsString());
            
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
            logger.log(Level.INFO, "reading from " + inStartCellRef.formatAsString() + "-" + inEndCellRef.formatAsString());
            
            logger.log(Level.INFO, "finding output's file lastRowNum [0-based] " + mergeSheet.getLastRowNum());
            
            CellReference outStartCellRef = new CellReference(mergeSheet.getLastRowNum() + 1, mergeSheet.getLeftCol());
            logger.log(Level.INFO, "will write to " + outStartCellRef.formatAsString());
            
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
        
        int outRowIdx = 0;          // index counting output's file rows
        int outColIdx = 0;          // index counting output's file column
        
        Row inRow = null;           // input's file row reading
        Row outRow = null;          // output's file row writing
        Cell inCell = null;         // input's file cell reading
        Cell outCell = null;        // output's file writing
        CellType inCellType = null; // input's file cell type
        
        /**
         * all three variables above are used to create a unique ID using SHA512 
         * for each line parsed. 
         * */
        List<String> values = null;
        String valuesToString = null;
        String SHA512MessageDigest = null;
        
        for (int rowIdx = inStartCellRef.getRow(); rowIdx <= inEndCellRef.getRow(); rowIdx++) {
            
            outColIdx = 0;
            
            inRow = inputSheet.getRow(rowIdx);
            outRow = mergeSheet.createRow(outStartCellRef.getRow() + outRowIdx++);
            
            inCell = null;
            outCell = null;
            inCellType = null;
            
            values = new ArrayList<String>();
            valuesToString = null;
            SHA512MessageDigest = null;
            
            for (int cellIdx = inStartCellRef.getCol(); cellIdx <= inEndCellRef.getCol(); cellIdx++) {
                
                inCell = inRow.getCell(cellIdx);
                outCell = outRow.createCell(outStartCellRef.getCol() + outColIdx++);
                inCellType = inCell.getCellType();
                
                if (inCellType == CellType.STRING) {
                    logger.log(Level.FINE, "CellType.STRING : " + inCell.getStringCellValue());
                    outCell.setCellValue(inCell.getStringCellValue());
                    values.add(inCell.getStringCellValue());
                    cellStyleCloner(inCell, outCell, mergeWorkbook);
                } else if (inCellType == CellType.NUMERIC) {
                    if (DateUtil.isCellDateFormatted(inCell)) {
                        logger.log(Level.FINE, String.valueOf("CellType.NUMERIC (Date) : " + inCell.getDateCellValue()));
                        outCell.setCellValue(inCell.getDateCellValue());
                        values.add(String.valueOf(inCell.getDateCellValue()));
                        cellStyleCloner(inCell, outCell, mergeWorkbook);
                    } else {
                        logger.log(Level.FINE, String.valueOf("CellType.NUMERIC (Numeric) : " +inCell.getNumericCellValue()));
                        outCell.setCellValue(inCell.getNumericCellValue());
                        values.add(String.valueOf(inCell.getNumericCellValue()));
                        cellStyleCloner(inCell, outCell, mergeWorkbook);
                    }
                } else if (inCellType == CellType.BOOLEAN) {
                    logger.log(Level.FINE, String.valueOf("CellType.BOOLEAN : " +inCell.getBooleanCellValue()));
                    outCell.setCellValue(inCell.getBooleanCellValue());
                    values.add(String.valueOf(inCell.getBooleanCellValue()));
                    cellStyleCloner(inCell, outCell, mergeWorkbook);
                } else if (inCellType == CellType.FORMULA) {
                    logger.log(Level.FINE, String.valueOf("CellType.FORMULA : " +inCell.getCellFormula()));
                    outCell.setCellValue(inCell.getCellFormula());
                    values.add(inCell.getCellFormula());
                    cellStyleCloner(inCell, outCell, mergeWorkbook);
                } else if (inCellType == CellType.BLANK) {
                    logger.log(Level.FINE, "CellType.BLANK : ");
                    outCell.setCellValue("");
                    values.add("");
                    cellStyleCloner(inCell, outCell, mergeWorkbook);
                }
            }
            
            /**
             * Be careful.
             * 
             * The Arraylist<String> returns its values with in [ ] bracket and
             * delimited by , comma. After each comma there is a space. These 
             * 3 characters plus the spaces should not be added in the SHA512 
             * message digest. We should not depend on an implementation i.e. 
             * ArrayList.toStrin() to produce a unique ID for each line.
             */
//            logger.log(Level.INFO, values.toString());
//            valuesToString = values.toString()
//                    .replace(", ", "")  //remove the commas and the space after
//                    .replace("[", "")   //remove the right bracket
//                    .replace("]", "")   //remove the left bracket
//                    .trim();            //remove trailing spaces
//            logger.log(Level.FINE, valuesToString);
//            SHA512MessageDigest = SHA2.getSHA512(valuesToString);
//            outCell = outRow.createCell(outStartCellRef.getCol() + outColIdx++);
//            outCell.setCellValue(SHA512MessageDigest);
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
    
	private int getLastRowNum(Sheet sheet) {
		int ret = 1;
		for (int rowNum = sheet.getLastRowNum(); rowNum >= 0; rowNum--) {
			final Row row = sheet.getRow(rowNum);
			if (row != null && row.getCell(0) != null) {
				ret = rowNum;
				break;
			}
		}
		return ret;
	}
    
}
