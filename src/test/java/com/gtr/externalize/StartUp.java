package com.gtr.externalize;

import com.gtr.externalize.bo.TestcaseBO;
import com.gtr.externalize.fileReaders.excel.TestcaseExcelReader;
import com.gtr.externalize.fileReaders.properties.PropertyReader;
import com.gtr.externalize.testng.RuntimeTestNG;

import java.util.List;

public class StartUp {

    public static void main(String[] args) {

        // read properties file
        new PropertyReader();

        // read testcase file
        TestcaseExcelReader testCaseFileReader = new TestcaseExcelReader();
        List<TestcaseBO> testcaseList = testCaseFileReader.read();

        // Start testNG execution
        RuntimeTestNG testNG = new RuntimeTestNG();
        testNG.create(testcaseList).run();
    }
}
