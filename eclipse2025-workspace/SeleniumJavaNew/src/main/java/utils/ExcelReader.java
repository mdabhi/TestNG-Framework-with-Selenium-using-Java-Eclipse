package utils;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	public static Object[][] getTestData(String filePath, String sheetName) {
        Object[][] data = null;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum();
            int colCount = sheet.getRow(0).getLastCellNum();

            data = new Object[rowCount][colCount];
            DataFormatter formatter = new DataFormatter();

            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i + 1); // Skip header row
                for (int j = 0; j < colCount; j++) {
                    data[i][j] = formatter.formatCellValue(row.getCell(j));
                }
            }
        } catch (Exception e) {
            System.out.println("Excel File not found or corrupted.");
        }
        return data;

	}
}
