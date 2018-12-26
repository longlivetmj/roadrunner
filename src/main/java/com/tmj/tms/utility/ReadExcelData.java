package com.tmj.tms.utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadExcelData {

    public List<List<Cell>> readFromExcel(InputStream in) {
        List<List<Cell>> sheetData = new ArrayList<List<Cell>>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow firstRow = sheet.getRow(0);
            if (firstRow.getPhysicalNumberOfCells() != 17) {
                throw new NullPointerException();
            }
            int maxNoOfCells = 17;
            Iterator<Row> rowIterator = sheet.iterator();
            System.out.println();
            while (rowIterator.hasNext()) {
                List<Cell> data = new ArrayList<Cell>();
                Row row = rowIterator.next();
                if (row.getRowNum() > 0) {
                    for (int cellCounter = 0; cellCounter < maxNoOfCells; cellCounter++) {
                        Cell cell1;
                        if (row.getCell(cellCounter) == null) {
                            cell1 = row.createCell(cellCounter);
                        } else {
                            cell1 = row.getCell(cellCounter);
                        }
                        cell1.setCellType(1);
                        data.add(cell1);
                    }
                    sheetData.add(data);
                }
            }
        } catch (NullPointerException ne) {
            ne.getStackTrace();
            System.out.println("Excel FORMAT ERROR");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetData;
    }
}
