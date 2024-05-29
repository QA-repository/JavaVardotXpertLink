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
        String Project_Name = properties.getProperty("Project_Name");

        if (Run_ENV.equalsIgnoreCase("DEV")&&Project_Name.equalsIgnoreCase(("UNHCR")))
            return properties.getProperty("DEV_cookie");
        else if (Run_ENV.equalsIgnoreCase("STG")&&Project_Name.equalsIgnoreCase(("UNHCR")))
            return properties.getProperty("STG_cookie");
        else if (Run_ENV.equalsIgnoreCase("DEV")&&Project_Name.equalsIgnoreCase(("Refworld")))
            return properties.getProperty("Refworld_DEVCookie_rup_ga");
        else if (Run_ENV.equalsIgnoreCase("Live global")&&Project_Name.equalsIgnoreCase(("UNHCR")))
            return properties.getProperty("Live_cookie");
        else if (Run_ENV.equalsIgnoreCase("DEV")&&Project_Name.equalsIgnoreCase(("EnlyteSTG")))
            return properties.getProperty("Enlyte_STG_Cookie");


        if (Run_ENV.equalsIgnoreCase("DEV")&&Project_Name.equalsIgnoreCase(("ISG")))
            return properties.getProperty("ISG_DEVCookie");
        else if (Run_ENV.equalsIgnoreCase("STG")&&Project_Name.equalsIgnoreCase(("ISG")))
            return properties.getProperty("ISG_STGCookie");
        else if (Run_ENV.equalsIgnoreCase("Live global")&&Project_Name.equalsIgnoreCase(("ISG")))
            return properties.getProperty("ISG_LiveCookie");


        if (Run_ENV.equalsIgnoreCase("DEV")&&Project_Name.equalsIgnoreCase(("EHB")))
            return properties.getProperty("EHB_DEV_Cookie");


        if (Run_ENV.equalsIgnoreCase("DEV")&&Project_Name.equalsIgnoreCase(("CEH")))
            return properties.getProperty("CEH_DEV_Cookie");

        else throw new RuntimeException("Run_ENV not specified in the API Configuration.properties file.");
    }
    public String GETCEH_DevLink() {
        String CEH_DEV = properties.getProperty("CEH_DEV");
        if (CEH_DEV != null)
            return CEH_DEV;

        else throw new RuntimeException("CEH_DEV not specified in the API Configuration.properties file.");
    }
    public String GetCreateNewMenuPath() {
        String createNewMenuPath = properties.getProperty("CreateNewMenu");
        if (createNewMenuPath != null)
            return createNewMenuPath;

        else throw new RuntimeException("CreateNewMenu not specified in the API Configuration.properties file.");

    }
    public String GetEditMenuPath() {
        String GetEditMenuPath = properties.getProperty("EditMenu");
        if (GetEditMenuPath != null)
            return GetEditMenuPath;

        else throw new RuntimeException("EditMenu not specified in the API Configuration.properties file.");

    }
    public String GetDeleteMenuPath() {
        String GetDeleteMenuPath = properties.getProperty("DeleteMenu");
        if (GetDeleteMenuPath != null)
            return GetDeleteMenuPath;

        else throw new RuntimeException("DeleteMenu not specified in the API Configuration.properties file.");

    }
    public String GetRun_ENV() {
        String Run_ENV = properties.getProperty("Run_ENV");
        String Project_Name = properties.getProperty("Project_Name");


        if (Run_ENV.equalsIgnoreCase("DEV")&&(Project_Name.equalsIgnoreCase("ISG"))){
            return properties.getProperty("ISG_Dev");

        }
        if (Run_ENV.equalsIgnoreCase("STG")&&(Project_Name.equalsIgnoreCase("ISG"))){

        }
        if (Run_ENV.equalsIgnoreCase("LIVE")&&(Project_Name.equalsIgnoreCase("ISG"))){

        }
        if (Run_ENV.equalsIgnoreCase("DEV")&&(Project_Name.equalsIgnoreCase("EHB"))){
            return properties.getProperty("EHB_DEV_URL");

        }


        if (Run_ENV.equalsIgnoreCase("DEV")&&(Project_Name.equalsIgnoreCase("UNHCR"))){
            return properties.getProperty("DEV_Link");

        }   if (Run_ENV.equalsIgnoreCase("STG")&&(Project_Name.equalsIgnoreCase("UNHCR"))){
            return properties.getProperty("STG_Link");

        }   if (Run_ENV.equalsIgnoreCase("Live_Global")&&(Project_Name.equalsIgnoreCase("UNHCR"))){
            return properties.getProperty("Live_Global_Link");

        }
        if (Run_ENV.equalsIgnoreCase("DEV")&&(Project_Name.equalsIgnoreCase("Refworld"))){
            return properties.getProperty("Refworld_DEV");
        }
        if (Run_ENV.equalsIgnoreCase("DEV")&&(Project_Name.equalsIgnoreCase("EnlyteSTG"))){
            return properties.getProperty("Enlyte_STG");
        }
        if (Run_ENV.equalsIgnoreCase("DEV")&&(Project_Name.equalsIgnoreCase("CEH"))){
            return properties.getProperty("CEH_DEV");
        }


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
    public String GetEHB_SearchAPI() {
        String EHB_SearchAPI = properties.getProperty("EHB_SearchAPI");
        if (EHB_SearchAPI != null)
            return EHB_SearchAPI;

        else throw new RuntimeException("EHB_SearchAPI not specified in the API Configuration.properties file.");
    }
    public String GetMainTaxonomy() {
        String Main_Taxonomy = properties.getProperty("Main_Taxonomy");
        if (Main_Taxonomy != null)
            return Main_Taxonomy;

        else throw new RuntimeException("Main_Taxonomy not specified in the API Configuration.properties file.");
    }
    public String GetContentNodeID(String TranslatedLang) {
        String Main_Taxonomy = "";
        if(TranslatedLang.equals("RU"))
            Main_Taxonomy = properties.getProperty("Translate_RU_Content_Path");
        else if (TranslatedLang.equals("ES")) {
            Main_Taxonomy = properties.getProperty("Translate_ES_Content_Path");
        }
        else
            Main_Taxonomy = properties.getProperty(TranslatedLang);

        if (Main_Taxonomy != null)
            return Main_Taxonomy;

        else throw new RuntimeException("Page not specified in the API Configuration.properties file.");
    }

    public String GetNewsSearchPath() {
        String NewsSearchPath = properties.getProperty("News_Search_Path");
        if (NewsSearchPath != null)
            return NewsSearchPath;

        else throw new RuntimeException("News_Search_Path not specified in the API Configuration.properties file.");
    }
    public String GetCreateUserPath() {
        String CreateUserPath = properties.getProperty("CreateUserPath");
        if (CreateUserPath != null)
            return CreateUserPath;

        else throw new RuntimeException("CreateUserPath not specified in the API Configuration.properties file.");
    }
    public String GetRegisterPath() {
        String RegisterPath = properties.getProperty("RegisterPath");
        if (RegisterPath != null)
            return RegisterPath;

        else throw new RuntimeException("RegisterPath not specified in the API Configuration.properties file.");
    }
    public String GetAdminContentPath() {
        String AdminContentPath = properties.getProperty("AdminContent");
        if (AdminContentPath != null)
            return AdminContentPath;

        else throw new RuntimeException("AdminContentPath not specified in the API Configuration.properties file.");
    }
    public String GetTaxonomyPath() {
        String TaxonomyPath = properties.getProperty("TaxonomyPath");
        if (TaxonomyPath != null)
            return TaxonomyPath;

        else throw new RuntimeException("TaxonomyPath not specified in the API Configuration.properties file.");
    }
    public String GetTaxonomyVocabularyPath() {
        String TaxonomyVocabularyPath = properties.getProperty("TaxonomyVocabularyPath");
        if (TaxonomyVocabularyPath != null)
            return TaxonomyVocabularyPath;

        else throw new RuntimeException("TaxonomyVocabularyPath not specified in the API Configuration.properties file.");
    }
    public String GetMenuHearderPath() {
        String MenuHeaderLink = properties.getProperty("MenuHeaderLink");
        if (MenuHeaderLink != null)
            return MenuHeaderLink;

        else throw new RuntimeException("MenuHeaderLink not specified in the API Configuration.properties file.");
    }
    public String GETISG_DevLink() {
        String ISG_Dev = properties.getProperty("ISG_Dev");
        if (ISG_Dev != null)
            return ISG_Dev;

        else throw new RuntimeException("ISG_Dev not specified in the API Configuration.properties file.");
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
    public String GetEHB_Add_RemoteVideoPath() {
        String EHB_Add_RemoteVideo = properties.getProperty("EHB_Add_RemoteVideo");
        if (EHB_Add_RemoteVideo != null)
            return EHB_Add_RemoteVideo;

        else throw new RuntimeException("EHB_Add_Remote Video not specified in the API Configuration.properties file.");
    }
    public String GetCreateNewsPath() {
        String CreatePublicationPath = properties.getProperty("CreateNewsPath");
        if (CreatePublicationPath != null)
            return CreatePublicationPath;

        else throw new RuntimeException("CreateNewsPath not specified in the API Configuration.properties file.");
    }
    public String GetCreatePartnerPath() {
        String CreatePartnerPath = properties.getProperty("CreatePartnerPath");
        if (CreatePartnerPath != null)
            return CreatePartnerPath;

        else throw new RuntimeException("CreatePartnerPath not specified in the API Configuration.properties file.");
    }
    public  String DeleteNodePath() {
        String Delete_Content_Path = properties.getProperty("Delete_Content_Path");
        if (Delete_Content_Path != null)
            return Delete_Content_Path;

        else throw new RuntimeException("Delete_Content_Path not specified in the API Configuration.properties file.");
    }
    public  String DeleteTaxonomyPath() {
        String Delete_Taxonomy_Path = properties.getProperty("Delete_Taxonomy_Path");
        if (Delete_Taxonomy_Path != null)
            return Delete_Taxonomy_Path;

        else throw new RuntimeException("Delete_Taxonomy_Path not specified in the API Configuration.properties file.");
    }
    public  String CloneNodePath() {
        String Clone_Taxonomy_Term = properties.getProperty("Clone_Taxonomy_Term");
        if (Clone_Taxonomy_Term != null)
            return Clone_Taxonomy_Term;

        else throw new RuntimeException("Clone_Taxonomy_Term not specified in the API Configuration.properties file.");
    }
    public  String EditTaxonomyNodePath() {
        String Edit_Taxonomy_Term = properties.getProperty("Edit_Taxonomy_Term");
        if (Edit_Taxonomy_Term != null)
            return Edit_Taxonomy_Term;

        else throw new RuntimeException("Edit_Taxonomy_Term not specified in the API Configuration.properties file.");
    }
    public   String GetNodeID() {
        String NodeID = properties.getProperty("NodeID");
        if (NodeID != null)
            return NodeID;

        else throw new RuntimeException("NodeID not specified in the API Configuration.properties file.");
    }
}