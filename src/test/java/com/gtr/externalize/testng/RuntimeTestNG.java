package com.gtr.externalize.testng;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RuntimeTestNG {

    private static String testcasePackagePath = "com.externalization.testcases";

    public TestNG create(){

        TestNG testNg = new TestNG();

        // list suite
        List<XmlSuite> suiteList = new ArrayList<>();

        // suite
        XmlSuite suite = new XmlSuite();
        suite.setName("GTR testcase demo");
        suite.setThreadCount(2);
        suite.setParallel(XmlSuite.ParallelMode.METHODS);

        //test
        List<XmlTest> testList = new ArrayList<>();

        XmlTest test = new XmlTest(suite);
        test.setName("Externalize");

        // list classes
        List<XmlClass> classList = new ArrayList<>();

        // Reflections
        Reflections reflections = getAllTestcaseClass();
        Set<Class<?>> allClasses =  reflections.getSubTypesOf(Object.class);

        // class
        for(Class c: allClasses){
            XmlClass cls = new XmlClass(c);

            Method[] allMethods = c.getDeclaredMethods();

            List<XmlInclude> includeMethods = new ArrayList<>();

            for (Method m : allMethods){
                String methodName = m.getName();

                // filtering logic

                includeMethods.add(new XmlInclude(methodName));
            }

            // setting method to the class
            cls.setIncludedMethods(includeMethods);

            // adding class to class list
            classList.add(cls);

            // adding class list to test
            test.setXmlClasses(classList);

            // test to the test list
            testList.add(test);

            // adding to the suite
            suite.setTests(testList);

            // suite the suite list
            suiteList.add(suite);


        }


        // runtime filers include or exclude testcases

    }

    // list suite

    // suite

    //test

    // list classes

    // class


    // runtime filers include or exclude testcases


    private static Reflections getAllTestcaseClass() {

        final ConfigurationBuilder config = new ConfigurationBuilder()
                .setScanners(new ResourcesScanner(), new SubTypesScanner(false))
                .setUrls(ClasspathHelper.forPackage(testcasePackagePath))
                .filterInputsBy(new FilterBuilder().includePackage(testcasePackagePath));

        final Reflections reflect = new Reflections(config);

        return reflect;
    }

}
