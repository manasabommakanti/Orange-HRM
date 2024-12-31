package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public static List<String[]> readExcel(String filePath, String sheetName) throws Exception {
        List<String[]> data = new ArrayList<>();
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        for (Row row : sheet) {
            String[] rowData = new String[row.getLastCellNum()];
            for (Cell cell : row) {
                rowData[cell.getColumnIndex()] = cell.toString();
            }
            data.add(rowData);
        }

        workbook.close();
        return data;
    }
}
