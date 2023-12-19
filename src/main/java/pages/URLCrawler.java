package pages;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class URLCrawler {

    private static int scannedLinks = 0;

    public static void main(String[] args) {
      //  String startingUrl = "https://unhcr-dev.unhcr.info/admin/content"; // Replace with your starting URL
      //  String cookie="geo_redirect_closed=1; _fbp=fb.1.1697985671735.265970786; _gcl_au=1.1.327770529.1698049924; _rup_ga=GA1.1.1286796730.1698049924; _ga=GA1.3.1286796730.1698049924; cookie_consent=true; cookie_consent=true; __qca=P0-853754207-1701264865442; m_ses=20231206135750; _ga_RDNCXLXWYH=GS1.1.1701860813.4.0.1701860822.51.0.0; __utmc=104234436; __utmz=104234436.1701869901.44.2.utmcsr=acnur-dev.unhcr.info|utmccn=(referral)|utmcmd=referral|utmcct=/; __utma=104234436.1286796730.1698049924.1702368709.1702453113.48; _ga_TNZM2XR4ZN=GS1.1.1702456058.63.0.1702456058.0.0.0; _gid=GA1.2.733259734.1702792235; _dc_gtm_UA-1473340-18=1; _dc_gtm_UA-1473340-17=1; _dc_gtm_UA-1473340-123=1; _ga=GA1.1.1286796730.1698049924; m_cnt=74; _uetsid=324a6f009ca011eebc2e3ddf47f6266c; _uetvid=b9d795e0419a11ee974f859fad821d29; SSESS38cb940190729d4ba0648db288282ad3=Ys1N6ifkfAcb6crjdpFtGFsFtVu%2CzUTGqUAd8G7C0%2C9Fkj8j; _rup_ga_EVDQTJ4LMY=GS1.1.1702792234.105.1.1702792246.0.0.0; _ga_KD4J9DLPE3=GS1.1.1702792234.75.1.1702792246.0.0.0";
        String startingUrl = "https://unhcr-dev.unhcr.info/"; // Replace with your starting URL
String cookie="_ga=GA1.4.1339341658.1696849433; _gid=GA1.4.1573632222.1702798552; SSESSed8661224080393694611044fff862d9=0-R6oxG9SMp%2CxPtNhUXXbp44tEE8kEJi20CYdSc4e-QQFQ76; _gat_gtag_UA_1473340_46=1";
        Set<String> processedURLs = new HashSet<>();
        Workbook workbook = new XSSFWorkbook();
        Sheet resultsSheet = workbook.createSheet("unhcr result1");

        crawlAndVerifyURLs(startingUrl, cookie, resultsSheet);

        try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Vardot QA\\Downloads\\test 2.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

            private static void crawlAndVerifyURLs(String startingUrl, String cookie, Sheet resultsSheet) {
                Queue<String> queue = new LinkedList<>();
                Set<String> processedURLs = new HashSet<>();

                queue.add(startingUrl);

                while (!queue.isEmpty()) {
                    String currentUrl = queue.poll();

                    try {
                        Document document = Jsoup.connect(currentUrl).header("Cookie", cookie).get();
                        Elements links = document.select("a[href]");

                        int remainingLinks = links.size() - scannedLinks;
                        System.out.println("Scanned links: " + scannedLinks + ", Remaining links: " + Math.max(0, remainingLinks));

                        for (Element link : links) {
                            String url = link.absUrl("href");

                            if (processedURLs.contains(url) || !isSameDomain(startingUrl, url) || url.matches(".*[a-zA-Z]+\\?.*")) {
                                System.out.println("URL: " + url + " already processed, different domain, or contains excludePattern. Skipping...");
                                continue;
                            }

                            scannedLinks++;

                            String[] apiResponse = apiResponse(url, cookie);
                            String responseCode = apiResponse[0];
                            String responseText = apiResponse[1];

                            String result = getResult(url, responseCode, responseText);

                            processedURLs.add(url);

                            // Write to Excel
                            Row row = resultsSheet.createRow(resultsSheet.getLastRowNum() + 1);
                            createCell(row, 0, url);
                            createCell(row, 1, result);

                            // Add the new URL to the queue
                            queue.add(url);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

    private static boolean isSameDomain(String startingUrl, String url) {
        try {
            URI startingUri = new URI(startingUrl);
            URI uri = new URI(url);
            return Objects.equals(startingUri.getHost(), uri.getHost());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void createCell(Row row, int index, String value) {
        Cell cell = row.createCell(index);
        int maxLength = 2000;
        if (value.length() > maxLength) {
            value = value.substring(0, maxLength);
        }
        cell.setCellValue(value);
    }

        public static String[] apiResponse(String urlString, String Cookie) {
            StringBuilder responseText = new StringBuilder();
            String[] apiResponse = new String[2];

            if (urlString.contains("/logout")) {
                apiResponse[0] = "200";
                apiResponse[1] = "Skipped";
            } else {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Cookie", Cookie);
                    connection.setRequestMethod("GET");

                    try (InputStream inputStream = connection.getInputStream()) {
                        // Check if the content type is an image or PDF
                        String contentType = connection.getContentType();
                        if (contentType != null && (contentType.startsWith("image/") || contentType.endsWith("/pdf"))) {
                            apiResponse[0] = String.valueOf(connection.getResponseCode());
                            apiResponse[1] = "Skipped - Image/PDF";
                            return apiResponse;
                        }

                        // Read the response text for non-image/PDF content
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                responseText.append(line);
                            }
                        }
                    }

                    apiResponse[0] = String.valueOf(connection.getResponseCode());
                    apiResponse[1] = responseText.toString();
                } catch (IOException e) {
                    // Handle specific exceptions for image/PDF content
                    if (e instanceof FileNotFoundException || e instanceof UnknownServiceException) {
                        apiResponse[0] = "200";
                        apiResponse[1] = "Skipped - Image/PDF";
                    } else {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        String stackTrace = sw.toString();
                        apiResponse[0] = stackTrace;
                    }
                }
            }

            return apiResponse;
        }


    private static String getResult(String url, String responseCode, String responseText) {
        if (!Objects.equals(responseCode, "200")) {
            System.out.println("URL: " + url + ", Verification Failed. Response Code: " + responseCode);
            return "Verification Failed. Response Code:" + responseCode;
        } else if (responseText.contains("Fatal error")) {
            System.out.println("URL: " + url + ", Verification Failed. Response Text: Fatal error");
            return "Verification Failed. Response Text: Fatal error";
        } else if (responseText.contains("Page not found")) {
            System.out.println("URL: " + url + ", Verification Failed. Response Text: Page not found");
            return " Verification Failed. Response Text: Page not found";
        } else if (responseText.contains("error message")) {
            System.out.println("URL: " + url + ", Verification Failed. Response Text: Page not found");
            return " Verification Failed. Response Text: error message";
        } else if (responseText.contains("The website encountered an unexpected error")) {
            System.out.println("URL: " + url + ", Verification Failed. Response Text: The website encountered an unexpected error");
            return "failed";
        } else {
            System.out.println("URL: " + url + ", Verification Passed");
            return "Passed";
        }
    }
}
