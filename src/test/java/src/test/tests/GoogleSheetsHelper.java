package src.test.tests;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static src.test.tests.APICaller.CR;

public class GoogleSheetsHelper {
    static String APPLICATION_NAME = "Vardot E2E Testing Tool";
    static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    static String SERVICE_ACCOUNT_KEY_PATH = System.getProperty("user.dir")+"/automationtool-helper-ef1299b041fc.json";
    static String SPREADSHEET_ID = ConfigurationReader.properties.getProperty("SPREADSHEET_ID");
 String RANGE = ConfigurationReader.properties.getProperty("RANGE");
    static  String Read_Range= ConfigurationReader.properties.getProperty("Read_Range");

    public static Sheets GoogleHelper_Initializer() throws IOException, GeneralSecurityException {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        FileInputStream credentialsStream = new FileInputStream(SERVICE_ACCOUNT_KEY_PATH);
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS));

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        return  new Sheets.Builder(httpTransport, JSON_FACTORY, requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static String determineNextEmptyRow(Sheets sheetsService, String spreadsheetId, String column)
            throws IOException {

        String range = column + "1:" + column;
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            return column + "1";
        }

        int lastRow = values.size();
        String value = (String) values.get(lastRow - 1).get(0);

        if (value == null || value.isEmpty()) {
            return column + lastRow;
        }

        return column + (lastRow + 1);
    }
    private static String determineLastExistRow(Sheets sheetsService, String spreadsheetId, String column)
            throws IOException {

        String range = column + "1:" + column;
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            return column + "1";
        }

        int lastRow = values.size();
        String value = (String) values.get(lastRow - 1).get(0);

        if (value == null || value.isEmpty()) {
            return column + lastRow;
        }

        return column + (lastRow);
    }
    public  void writeDataInNextEmptyRow(List<Object> data) throws IOException, GeneralSecurityException {
        String range = determineNextEmptyRow(GoogleHelper_Initializer(), SPREADSHEET_ID, "A");
        if (range == null) {
            System.out.println("No empty row found in column " + "A");
            return;
        }

        List<List<Object>> values = new ArrayList<>();
        values.add(data);

        ValueRange body = new ValueRange().setValues(values);
        UpdateValuesResponse result = GoogleHelper_Initializer().spreadsheets().values()
                .update(SPREADSHEET_ID, range, body)
                .setValueInputOption("RAW")
                .execute();

    }
    public  void deleteDataInLastRow(List<Object> data) throws IOException, GeneralSecurityException {
        String range = determineLastExistRow(GoogleHelper_Initializer(), SPREADSHEET_ID, "A");
        if (range == null) {
            System.out.println("No empty row found in column " + "A");
            return;
        }

        List<List<Object>> values = new ArrayList<>();
        values.add(data);

        ValueRange body = new ValueRange().setValues(values);
        UpdateValuesResponse result = GoogleHelper_Initializer().spreadsheets().values()
                .update(SPREADSHEET_ID, range, body)
                .setValueInputOption("RAW")
                .execute();

    }
    public static String prepareNode_For_deletion(String NodeType){
        int firstUnderscoreIndex = NodeType.indexOf("_");

        int secondUnderscoreIndex = NodeType.indexOf("_", firstUnderscoreIndex + 1);

        if (firstUnderscoreIndex != -1 && secondUnderscoreIndex != -1) {
            String extractedValue = NodeType.substring(firstUnderscoreIndex + 1, secondUnderscoreIndex);
            return "node_"+extractedValue+"_delete_form";
        } else {
            return "Node type is not valid";

        }
    }
    public static String GetNodeID() throws IOException, GeneralSecurityException {
        ValueRange response = GoogleHelper_Initializer().spreadsheets().values()
                .get(SPREADSHEET_ID, Read_Range)
                .execute();
        List<List<Object>> values = response.getValues();
        int lastRowIndex = values.size() - 1;
       String lastRow=(values.get(lastRowIndex).toString());

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        }
        return lastRow;
    }


    public static void clearCell(Sheets sheetsService)throws IOException, GeneralSecurityException {
        String range = determineNextEmptyRow(GoogleHelper_Initializer(), SPREADSHEET_ID, "A");
        if (range == null) {
            System.out.println("No empty row found in column " + "A");
            return;
        }
        List<Object> data = new ArrayList<>();;

        List<List<Object>> values = new ArrayList<>();
        data.add("");
        values.add(data);

        ValueRange body = new ValueRange().setValues(values);
        UpdateValuesResponse result = GoogleHelper_Initializer().spreadsheets().values()
                .update(SPREADSHEET_ID, String.valueOf("A"+(Integer.parseInt(range.replace("A",""))-1)), body)

                .setValueInputOption("RAW")
                .execute();

        System.out.println(String.valueOf("A"+(Integer.parseInt(range.replace("A",""))-1)));
    }


}



