package com.gtr.externalize.fileReaders.properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private String propertiesFilePath = "src/test/resources/config.properties";

    private static Properties properties = new Properties();


    public PropertyReader() {
        readFile();
    }

    /*
     * read and store all properties
     */
    private void readFile() {
        File file = new File(propertiesFilePath);

        try (FileReader reader = new FileReader(file)) {

            //load all values
            properties.load(reader);

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Get selected property
     *
     * @param propertyKey property key
     * @return value
     */
    public static String getProperty(String propertyKey) {
        return properties.getProperty(propertyKey);
    }

}
