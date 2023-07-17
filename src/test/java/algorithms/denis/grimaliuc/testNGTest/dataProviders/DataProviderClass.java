package algorithms.denis.grimaliuc.testNGTest.dataProviders;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;

public class DataProviderClass {


    @DataProvider(name = "excel-data")
    public Object[][] excelDP() {
        //We are creating an object from the excel sheet data by calling a method that reads data from the excel stored locally in our system
        Object[][] arrObj = getExcelData(System.getProperty("user.dir") + "/src/main/resources/DataProvider.xlsx", "Sheet_NR1");
        return arrObj;
    }

    //This method handles the excel - opens it and reads the data from the respective cells using a for-loop & returns it in the form of a string array
    public String[][] getExcelData(String fileName, String sheetName) {

        String[][] data;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet(sheetName);
            XSSFRow row = sh.getRow(0);
            int noOfRows = sh.getPhysicalNumberOfRows(); // May not work if you create an empty cell
            int noOfCols = row.getLastCellNum();
            DataFormatter formatter = new DataFormatter();
            data = new String[noOfRows - 1][noOfCols];
            for (int rowNum = 1; rowNum < noOfRows; rowNum++) {
                for (int cellNum = 0; cellNum < noOfCols; cellNum++) {
                    row = sh.getRow(rowNum);
                    data[rowNum - 1][cellNum] = formatter.formatCellValue(row.getCell(cellNum));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    @Test(dataProvider = "excel-data")
    public void test(String id, String fName, String lName, String age, String salary) {
        System.out.printf("Id=%s, fName=%s, lName=%s, Age=%s, Salary=%s\n", id, fName, lName, age, salary);
    }

}
