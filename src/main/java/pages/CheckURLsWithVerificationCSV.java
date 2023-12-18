package pages;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CheckURLsWithVerificationCSV {


    public static void main(String[] args) {
        String csvFilePath = "C:\\Users\\Vardot QA\\Downloads\\test.csv";
String cookie="geo_redirect_closed=1; _fbp=fb.1.1697985671735.265970786; _gcl_au=1.1.327770529.1698049924; _rup_ga=GA1.1.1286796730.1698049924; _ga=GA1.3.1286796730.1698049924; cookie_consent=true; cookie_consent=true; __qca=P0-853754207-1701264865442; m_ses=20231206135750; _ga_RDNCXLXWYH=GS1.1.1701860813.4.0.1701860822.51.0.0; __utmc=104234436; __utmz=104234436.1701869901.44.2.utmcsr=acnur-dev.unhcr.info|utmccn=(referral)|utmcmd=referral|utmcct=/; __utma=104234436.1286796730.1698049924.1702368709.1702453113.48; _ga_TNZM2XR4ZN=GS1.1.1702456058.63.0.1702456058.0.0.0; _gid=GA1.2.733259734.1702792235; _dc_gtm_UA-1473340-18=1; _dc_gtm_UA-1473340-17=1; _dc_gtm_UA-1473340-123=1; _ga=GA1.1.1286796730.1698049924; m_cnt=74; _uetsid=324a6f009ca011eebc2e3ddf47f6266c; _uetvid=b9d795e0419a11ee974f859fad821d29; SSESS38cb940190729d4ba0648db288282ad3=Ys1N6ifkfAcb6crjdpFtGFsFtVu%2CzUTGqUAd8G7C0%2C9Fkj8j; _rup_ga_EVDQTJ4LMY=GS1.1.1702792234.105.1.1702792246.0.0.0; _ga_KD4J9DLPE3=GS1.1.1702792234.75.1.1702792246.0.0.0";
        //String cookie = "_ga=GA1.4.1339341658.1696849433; _gid=GA1.4.1573632222.1702798552; SSESSed8661224080393694611044fff862d9=0-R6oxG9SMp%2CxPtNhUXXbp44tEE8kEJi20CYdSc4e-QQFQ76";
        Set<String> processedURLs = new HashSet<>();

        try (BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath, StandardCharsets.UTF_8))) {
            String headerLine = csvReader.readLine();

            Workbook workbook = new XSSFWorkbook();
            Sheet resultsSheet = workbook.createSheet("unhcr result1");

            Row headerRow = resultsSheet.createRow(0);
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("URL");
            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("Result");

            String line;
            int rowNum = 1;

            while ((line = csvReader.readLine()) != null) {
                String[] nextRecord = line.split(",");
                String url = nextRecord[0];

                if (processedURLs.contains(url)) {
                    System.out.println("URL: " + url + " already processed. Skipping...");
                    continue;
                }
String[] apiresponse=apiResponse(url, cookie);

                String responseCode = apiresponse[0];

                String responseText = apiresponse[1];

                String result;
                if (!Objects.equals(responseCode, "200")) {
                    System.out.println("URL: " + url + ", Verification Failed. Response Code: " + responseCode);
                    result = "Verification Failed. Response Code:" + responseCode;
                } else if (responseText.contains("Fatal error")) {
                    System.out.println("URL: " + url + ", Verification Failed. Response Text: Fatal error");
                    result = "Verification Failed. Response Text: Fatal error";
                } else if (responseText.contains("Page not found")) {
                    System.out.println("URL: " + url + ", Verification Failed. Response Text: Page not found");
                    result = " Verification Failed. Response Text: Page not found";
                } else if (responseText.contains("error message")) {
                    System.out.println("URL: " + url + ", Verification Failed. Response Text: Page not found");
                    result = " Verification Failed. Response Text: error message";

                } else if (responseText.contains("The website encountered an unexpected error")) {
                    System.out.println("URL: " + url + ", Verification Failed. Response Text: The website encountered an unexpected error");
                    result = "failed";
                } else {
                    System.out.println("URL: " + url + ", Verification Passed");
                    result = "Passed";
                }


                processedURLs.add(url);

                Row row = resultsSheet.createRow(rowNum);
                Cell urlCell = row.createCell(0);
                urlCell.setCellValue(url);
                Cell resultCell = row.createCell(1);
                resultCell.setCellValue(result);

                rowNum++;
            }

            try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Vardot QA\\Downloads\\test 2.xlsx")) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static String[] apiResponse(String urlString, String Cookie) {

        StringBuilder responseText = new StringBuilder();
        String[] apiResponse = new String[2];
        if (urlString.contains("/logout")) {

            apiResponse[0] = ("200");
            apiResponse[1] = "Skipped";
        } else {
            apiResponse = new String[2];
            try {
                URL url = new URL(urlString);


                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Cookie", Cookie);

                connection.setRequestMethod("GET");

                try (InputStream inputStream = connection.getInputStream();
                     InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                     BufferedReader reader = new BufferedReader(inputStreamReader)) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseText.append(line);
                    }
                    apiResponse[0] = String.valueOf(connection.getResponseCode());
                    apiResponse[1] = responseText.toString();
                }
            } catch (IOException e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();
                apiResponse[0] = stackTrace;
                return apiResponse;
            }

            return apiResponse;

        }
        return apiResponse;
    }
}
