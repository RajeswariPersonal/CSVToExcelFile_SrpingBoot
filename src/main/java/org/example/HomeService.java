package org.example;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

@org.springframework.stereotype.Service
public class HomeService {
    public String converCSVToExcelFile() throws IOException {
        ArrayList<ArrayList<String>> allRowAndColData = null;
        ArrayList<String> oneRowData = null;
        String fName = "C:\\Users\\User\\Documents\\test.csv";
        String strExcelFileName = "C:\\Users\\User\\Documents\\sample.xls";
        String currentLine;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        DataInputStream myInput = new DataInputStream(fis);

        allRowAndColData = new ArrayList<ArrayList<String>>();
        while ((currentLine = myInput.readLine()) != null) {
            oneRowData = new ArrayList<String>();
            String oneRowArray[] = currentLine.split(",");
            for (int j = 0; j < oneRowArray.length; j++) {
                oneRowData.add(oneRowArray[j]);
            }
            allRowAndColData.add(oneRowData);
            System.out.println();

        }

        try {
            HSSFWorkbook workBook = new HSSFWorkbook();
            HSSFSheet sheet = workBook.createSheet("sheet1");
            for (int i = 0; i < allRowAndColData.size(); i++) {
                ArrayList<String> ardata = (ArrayList<String>) allRowAndColData.get(i);
                HSSFRow row = sheet.createRow(0 + i);
                for (int k = 0; k < ardata.size(); k++) {
                    System.out.print(ardata.get(k));
                    HSSFCell cell = row.createCell(k);
                    cell.setCellValue(ardata.get(k).toString());
                }
                System.out.println();
            }
            FileOutputStream fileOutputStream =  new FileOutputStream(strExcelFileName);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("File Exported Successfully in Excel file");
        } catch (Exception ex) {
        }

        return  strExcelFileName;
    }

}
