package src.test.tests;

import com.google.common.io.Files;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static src.test.tests.SendMail.Sendthereport;

public class APICaller extends TestBases {
    static ConfigurationReader CR = new ConfigurationReader();

    public static Response Public_GetAPI_Caller(String URL, String Path, List<String> Requestheaders) throws Exception {
        RestAssured.baseURI = URL;
        RequestSpecification request = RestAssured.given();
        Response response;
        if (!Requestheaders.isEmpty()) {
            for (int count = 1; count < Requestheaders.size(); count += 2) {
                Path = Path + Requestheaders.get(count - 1) + "=" + Requestheaders.get(count) + "&";

            }

        }

        System.out.println("Testing Started for: " + URL + Path);
        response = request.headers("user-agent", "Application").auth().basic("", "").given().when().get(Path);


        System.out.println("Status code: " + response.getStatusCode());

        return response;
    }

    public static Response Private_GetAPI_Caller(String URL, String Path, List<String> Requestheaders) throws Exception {
        RestAssured.baseURI = URL;
        RequestSpecification request = RestAssured.given();
        Response response;
        if (!Requestheaders.isEmpty()) {
            for (int count = 1; count < Requestheaders.size(); count += 2) {
                Path = Path + Requestheaders.get(count - 1) + "=" + Requestheaders.get(count) + "&";
            }
        }

        System.out.println("Testing Started for: " + URL + Path);

        response = request.headers("user-agent", "Application").auth().basic("", "").given().cookie(CR.Getcookie()).when().get(Path);
        System.out.println("Status code: " + response.getStatusCode());

        return response;
    }

    private static <unknown> Set<unknown> findDuplicates(List<unknown> list) {
        Set<unknown> seen = new HashSet<>();
        return list.stream()
                .filter(e -> !seen.add(e))
                .collect(Collectors.toSet());
    }

    public static void ContentCreationAPI(String URL, Map<String, String> RequestBody, String cookie) throws Exception {
    String Nodetype = "";
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> entry : RequestBody.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            postData.append('=');
            postData.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
        if(entry.getValue().contains("node_"))
            Nodetype=entry.getValue();
        }
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_NONE);
        HttpClient httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NEVER)
                .cookieHandler(cookieManager)
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("", "".toCharArray());
                    }
                })
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Cookie",cookie )
                .POST(HttpRequest.BodyPublishers.ofString(postData.toString()))
                .build();
        CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = responseFuture.get();
        int statusCode = response.statusCode();
        String responseBody = response.body();
        System.out.println("Status Code: " + statusCode);
        //System.out.println("res body: " + responseBody);

        // Assert.assertTrue(CheckContentCreationResponseCode(statusCode));
       NodeWriter(responseBody,Nodetype);

    }
    public static void NodeWriter(String responseBody, String nodetype) throws GeneralSecurityException, IOException {
        GoogleSheetsHelper GSH=new GoogleSheetsHelper();
        List<Object> data =new ArrayList<>();
        data.add(Extract_Node_ID(responseBody));
        data.add(nodetype);
        GSH.writeDataInNextEmptyRow(data);
    }
    public static int  TermNodeExtarctor(String A) {
            String input = A;



            Pattern pattern = Pattern.compile("edit-terms-tid(\\d+)");
        int number = 0;

            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                String numberStr = matcher.group(1);
                number = Integer.parseInt(numberStr);
                System.out.println("Extracted number: " + number);
            } else {
                System.out.println("No number found in the input string.");
            }

            return number;
        }

    public static String Extract_Node_ID(String responseBody){
        Pattern pattern = Pattern.compile("/(\\d+)/");

        // Create a matcher for the input string
        Matcher matcher = pattern.matcher(responseBody);
        String number = "";
        // Find and print the first match
        if (matcher.find()) {
             number = matcher.group(1); // Number between the slashes
        } else {
            System.out.println("Number not found.");
        }
        return number;
    }
    public static Boolean CheckIfContentCreated(String responseBody){

        return responseBody.contains("Redirecting to https://unhcr-staging.unhcr.info/node/");

    }

    public static Boolean CheckContentCreationResponseCode(int statusCode){
        return  statusCode==303;

    }
public static void
GeneralResponseValidator(List<String> Response_List) throws Exception {
    Response_List.add("zhxeoo-unhcr-entity:node/:en");
        PrintDuplicateLog(Response_List);

        if(findDuplicates(Response_List).isEmpty()){
            PrintDuplicateLog(Response_List);

            System.out.println( "No duplicate Found");

        }
        else {
            System.out.println( "duplicate Found");
            PrintDuplicateLog(Response_List);
            Exception exception= new Exception();
            Sendthereport();
            throw new Exception(findDuplicates(Response_List).size()+" Duplicate found: "+findDuplicates(Response_List));
        }
    }

    public static void PrintDuplicateLog(List<String> Response_List) throws IOException {
            String DuplicationPath = System.getProperty("user.dir")+"/HDuplication-List"+System.currentTimeMillis()+".txt";
            Files.write((findDuplicates(Response_List).size()+" Duplicate found: "+findDuplicates(Response_List)).getBytes(), Paths.get(DuplicationPath).toFile());

        }
    public static List<String> getLines(String input) {
        List<String> lines = new ArrayList<>();
input=input.replace(":", "\"");

        String[] splitInput = input.split("\\n");
        for (String line : splitInput) {
            line=line.replace(" ", "="+'"');
            line = line.replace(",=\"", ",");

            lines.add('"'+line+'"');
        }

        return lines;
    }

    public static String PrepareFormData(String PageURL,String cssQuery) {
        RestAssured.baseURI = PageURL;
        RequestSpecification request = RestAssured.given();
        Response response;

        response = request.headers("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36").cookie(CR.Getcookie()).auth().basic("", "").given().when().get(PageURL);
        //System.out.println("getBody===="+response.getBody().asString());
        Document doc = Jsoup.parse(response.getBody().asString());
        //System.out.println("response===="+response.getStatusCode());
        Element inputElement = doc.select(cssQuery).first();
        if (inputElement != null) {
            System.out.println("@@@Found");
            return inputElement.attr("value");
        } else {
            System.out.println("@@@ Not Found");

            return null; // Handle the case where the input element is not found
        }
    }



    public static void Content_Deleter(String URL,String Path,String Cookie,Map<String, String> RequestBody) throws ExecutionException, InterruptedException, IOException, GeneralSecurityException {
            StringBuilder postData = new StringBuilder();

            for (Map.Entry<String, String> entry : RequestBody.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
                postData.append('=');
                postData.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            }
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            HttpClient httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NEVER)
                    .cookieHandler(cookieManager)
                    .authenticator(new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("", "".toCharArray());
                        }
                    })
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL+Path))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Cookie",Cookie )
                    .POST(HttpRequest.BodyPublishers.ofString(postData.toString()))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> response = responseFuture.get();
            int statusCode = response.statusCode();
            System.out.println("Status Code: " + statusCode);

            Assert.assertTrue(CheckContentCreationResponseCode(statusCode));
GoogleSheetsHelper GSH= new GoogleSheetsHelper();
            List<Object> data= new ArrayList<>();
            data.add("");
            data.add("");
GSH.deleteDataInLastRow(data);

    }



    }











