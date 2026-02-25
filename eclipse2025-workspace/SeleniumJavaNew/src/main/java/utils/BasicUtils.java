package utils;

//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

//import java.io.File;

//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.IOException;

public class BasicUtils {

    // You can change the information of your data file here
    public static String FILE_PATH = "C:\\Users\\t470\\eclipse2025-workspace\\SeleniumJavaNew\\src\\test\\resources\\TestData\\testData.xlsx"; // File Path
    public static String SHEET_NAME = "Data"; // Sheet name
    public static String TABLE_NAME = "testData"; // Name of data table

    public static int WAIT_TIME = 30; // Delay time to wait the website

    // Expected output
    public static String EXPECT_TITLE = "Guru99 Bank Manager HomePage";
    public static String EXPECT_ERROR = "User or Password is not valid";

    /* You can change the Path of FireFox base on your environment here */
    public static String FIREFOX_PATH = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";

    // launch completely

	public static String baseURL = "https://www.demo.guru99.com/V4/";
//	public static final String usrName = "mngr654806";
//	public static final String pass = "amYtesu";
	public static String VALID = "valid";
	public static String INVALID = "invalid";

	
	public static String[][] getDataFromExcel(String filePath, String sheetName, 
			String tableName) throws Exception {
        
		String[][] data = null;
		FileInputStream fis = null;
		
		try {
				fis = new FileInputStream(filePath);
			}
		catch (FileNotFoundException e){
			e.printStackTrace();
			}
		
		try {
				Workbook workbook = WorkbookFactory.create(fis); // Works for both .xls and .xlsx
				Sheet sheet = workbook.getSheet(sheetName);
				data = new String[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
				
		        for(int i=0; i < sheet.getLastRowNum(); i++) {
		        	for (int k=0; k< sheet.getRow(0).getLastCellNum(); k++) {
		        			data[i][k] = sheet.getRow(i+1).getCell(k).toString();
		        			System.out.printf("Data:",data[i][k]);
		        	}
		        }
		        workbook.close();
			}
		catch (Exception e) {
			e.printStackTrace();
			}
		
  

        			
//        System.out.println("Rows found: " + data.length);
//        System.out.println("Columns found: " + data[0].length);
        
        
        return data;
	}
  
}