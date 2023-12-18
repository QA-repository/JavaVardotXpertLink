package pages;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.apache.poi.ss.usermodel.CellType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class loading_Nodes_Time {
    static String filePath="C:/Users/Vardot QA/Desktop/Event samples.xlsx";
    @Test
    public void TestA(){

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException ex)

    {
        throw new RuntimeException(ex);
    }
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Sheet sheet = workbook.getSheetAt(0);

            // Step 2: Process each row
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String url = row.getCell(0).getStringCellValue();

                // Step 3: Make requests to dev and staging servers
                String cookie_435 = "_ga=GA1.4.1097673704.1700655228; _gid=GA1.4.1888723227.1700990105; SSESS6a9be30a6e93bed3721d74b7842f4faf=oRCoq7xxMgBC8YOUQz9tHkDSKPFevGCYnidyLtEp9zSGlfPW";

                String cookie_DEV ="_ga=GA1.4.827294163.1697977884; _gid=GA1.4.1138058600.1700984680; SSESS47ba257f7fd3779338541517126221e7=SxtdV7FI3JG5nGVhR5IuH1CDDJohQ3DarrXVpbKMCgRI8jj%2C";
                long DEVTime = 0;
                try {
                   DEVTime = measureResponseTime(url, "https://development-q5nzhaa-qtvuyshw65fam.eu-4.platformsh.site",cookie_DEV);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                long layoutDEVTime = 0;
                try {
                //    layoutDEVTime = measureResponseTime(url, "http://dev-server",cookie_DEV);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                long layout435Time = 0;
                try {
              //      layout435Time = measureResponseTime(url, "https://gcr-435-klf2bgi-qtvuyshw65fam.eu-4.platformsh.site",cookie_435);
                } catch (Exception ex) {
//                 throw new RuntimeException(ex);
                }

                long Time_435 = 0;
                try {
                   Time_435 = measureResponseTime(url, "https://gcr-435-klf2bgi-qtvuyshw65fam.eu-4.platformsh.site",cookie_DEV);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                // Step 4: Write results to Excel
                Cell DEVTimeCell = row.createCell(1);
                DEVTimeCell.setCellValue(DEVTime);
                Cell Time_435Cell = row.createCell(2);
                Time_435Cell.setCellValue(Time_435);
             //   Cell layoutDEVTimeCell = row.createCell(3);
               // layoutDEVTimeCell.setCellValue(layoutDEVTime);
//Cell layout435TimeCell = row.createCell(4);
//layout435TimeCell.setCellValue(layout435Time);

            }

            // Step 5: Write the updated Excel file
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        try {
            workbook.write(outputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            workbook.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("Excel file has been updated successfully.");
        //errorWriter("<br /><b>Fatal error</b>:  Maximum execution time of 30 seconds exceeded in <b>/app/docroot/core/lib/Drupal/Core/Plugin/Context/ContextHandler.php</b> on line <b>27</b><br />\n");

        }
    private static long measureResponseTime(String url, String server, String cookie) throws IOException {
        System.setProperty("http.cachePolicy", "no-store");
        long startTime = System.currentTimeMillis();
        URL serverUrl = new URL(server + url);
        System.out.println(serverUrl);
        HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Cookie", cookie);
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Pragma", "no-cache");
        connection.setRequestProperty("Expires", "0");

        connection.connect();

        String responseText = readResponseText(connection); // Store the response in a variable

        long endTime = System.currentTimeMillis();
        connection.disconnect();
        System.out.println(connection.getResponseCode());

        if (url.contains("/node/1242/layout"))
            System.out.println(responseText);

        if (responseText.contains("Fatal error")||connection.getResponseCode()!=200||responseText.contains("404")) {
            return 0000000;
        }



        return (endTime - startTime);
    }





    private static String readResponseText(HttpURLConnection connection) throws IOException {
        StringBuilder responseText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseText.append(line);
            }
        }

        return responseText.toString();
    }
}
