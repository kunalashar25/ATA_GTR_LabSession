package com.gtr.externalize.fileReaders.excel;

import com.gtr.externalize.bo.TestcaseBO;
import com.gtr.externalize.fileReaders.properties.PropertyReader;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestcaseExcelReader {

    // testcase file path
    private String testcaseFilePath = "src/test/resources/testCases.xlsx";

    public List<TestcaseBO> read() {

        // to store list of testcases to be executed
        List<TestcaseBO> testcaseList = new ArrayList<>();
        String sheetName = "InitialDraft";

        // get suite name
        new PropertyReader();
        String suiteName = PropertyReader.getProperty("suiteToRun").toLowerCase();
        System.out.println("\nInitiating Execution for " + suiteName.toUpperCase() + " Suite\n");

        // creating workbook instance
        XSSFWorkbook workbook = null;

        // initialize workbook
        try {
            workbook = new XSSFWorkbook(testcaseFilePath);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // get testcase sheet
        XSSFSheet sheet = workbook.getSheet(sheetName);

        // find total number of rows
        int totalRows = sheet.getPhysicalNumberOfRows();

        // iterating on each row
        for (int i = 0; i < totalRows; i++) {

            // creating map to store header and value of each row
            Map<String, String> testcaseMap = new HashMap<>();

            // get column count in row 1
            int colNum = sheet.getRow(1).getLastCellNum();

            // iterating on each column
            for (int j = 0; j < colNum; j++) {

                String key = sheet.getRow(0).getCell(j).getStringCellValue().toLowerCase();
                String value = null;

                // fetch value from the cell. If NPE, then store as blank.
                try {
                    CellType cellType = sheet.getRow(i + 1).getCell(j).getCellType();

                    switch (cellType) {
                        case STRING:
                            value = sheet.getRow(i + 1).getCell(j).getStringCellValue();
                            break;
                        case NUMERIC:
                            value = String.valueOf(sheet.getRow(i + 1).getCell(j).getNumericCellValue());
                            break;
                        default:
                            value = "";
                            break;
                    }
                } catch (NullPointerException npe) {
                    value = "";
                }

                // add value to the map
                testcaseMap.put(key, value);
            }

            // check if testcase execution flag for given suite
            if (testcaseMap.get(suiteName).equalsIgnoreCase("Y")) {

                // set BO values
                TestcaseBO testcaseBO = new TestcaseBO();
                testcaseBO.setTestcaseName(testcaseMap.get("testcase_name"));
                testcaseBO.setPageName(testcaseMap.get("page_name"));

                // add testcase to the list for execution
                testcaseList.add(testcaseBO);
            }
        }

        // close workbook
        try {
            workbook.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.println("Testcases to execute:: "+testcaseList+"\n");

        return testcaseList;
    }

    public static void main(String[] args) {
        TestcaseExcelReader reader = new TestcaseExcelReader();
        reader.read();
    }
}
