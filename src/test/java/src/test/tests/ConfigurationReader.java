package src.test.tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static java.lang.System.getProperty;

public class ConfigurationReader {

    public static Properties properties;
    public  String propertyFilePath = getProperty("user.dir")+"/configs/API configuration.properties";


    public ConfigurationReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String Getcookie() {
        String Run_ENV = properties.getProperty("Run_ENV");

        if (Run_ENV.equalsIgnoreCase("DEV"))
            return properties.getProperty("DEV_cookie");
        else if (Run_ENV.equalsIgnoreCase("STG"))
            return properties.getProperty("STG_cookie");
        else if (Run_ENV.equalsIgnoreCase("Live global"))
            return properties.getProperty("Live_cookie");
        else throw new RuntimeException("Run_ENV not specified in the API Configuration.properties file.");
    }
    public String GetRun_ENV() {
        String Run_ENV = properties.getProperty("Run_ENV");

        if (Run_ENV.equalsIgnoreCase("DEV")){
            return properties.getProperty("DEV_Link");

        }
        else if (Run_ENV.equalsIgnoreCase("STG"))
            return properties.getProperty("STG_Link");
        else if (Run_ENV.equalsIgnoreCase("Live global"))
            return properties.getProperty("Live_Global_Link");
        else throw new RuntimeException("Run_ENV not specified in the API Configuration.properties file.");
    }
    public String GetAPPLICATION_NAME() {
        String APPLICATION_NAME = properties.getProperty("APPLICATION_NAME");

        if (APPLICATION_NAME!= null)
            return properties.getProperty("APPLICATION_NAME");

        else throw new RuntimeException("APPLICATION_NAME not specified in the API Configuration.properties file.");
    }

    public String GetSPREADSHEET_ID() {
        String SPREADSHEET_ID = properties.getProperty("SPREADSHEET_ID");

        if (SPREADSHEET_ID!=null)
            return properties.getProperty("SPREADSHEET_ID");

        else throw new RuntimeException("SPREADSHEET_ID not specified in the API Configuration.properties file.");
    }
    public String GetMainSearchPath() {
        String Main_Search_Path = properties.getProperty("Main_Search_Path");
        if (Main_Search_Path != null)
            return Main_Search_Path;

        else throw new RuntimeException("Main_Search_Path not specified in the API Configuration.properties file.");
    }

    public String GetNewsSearchPath() {
        String NewsSearchPath = properties.getProperty("News_Search_Path");
        if (NewsSearchPath != null)
            return NewsSearchPath;

        else throw new RuntimeException("News_Search_Path not specified in the API Configuration.properties file.");
    }
    public String Content_Page() {
        String Content_Page = properties.getProperty("Content_Page");
        if (Content_Page != null)
            return Content_Page;

        else throw new RuntimeException("Content_Page not specified in the API Configuration.properties file.");
    }
    public String GetCreatePublicationPath() {
        String CreatePublicationPath = properties.getProperty("CreatePublicationPath");
        if (CreatePublicationPath != null)
            return CreatePublicationPath;

        else throw new RuntimeException("CreatePublicationPath not specified in the API Configuration.properties file.");
    }
    public String GetCreateNewsPath() {
        String CreatePublicationPath = properties.getProperty("CreateNewsPath");
        if (CreatePublicationPath != null)
            return CreatePublicationPath;

        else throw new RuntimeException("CreateNewsPath not specified in the API Configuration.properties file.");
    }
    public  String DeleteNodePath() {
        String Delete_Content_Path = properties.getProperty("Delete_Content_Path");
        if (Delete_Content_Path != null)
            return Delete_Content_Path;

        else throw new RuntimeException("Delete_Content_Path not specified in the API Configuration.properties file.");
    }
    public   String GetNodeID() {
        String NodeID = properties.getProperty("NodeID");
        if (NodeID != null)
            return NodeID;

        else throw new RuntimeException("NodeID not specified in the API Configuration.properties file.");
    }
}
