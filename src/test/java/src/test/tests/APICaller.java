package src.test.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;

import java.io.File;
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
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.net.URI;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;

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
        response = request.headers("user-agent", "Application").auth().basic("unicc", "5NJjoVm-RV8u9Qun4hnt").given().when().get(Path);


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

        response = request.headers("user-agent", "Application").auth().basic("unicc", "5NJjoVm-RV8u9Qun4hnt").given().cookie(CR.Getcookie()).when().get(Path);
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
                        return new PasswordAuthentication("unicc", "5NJjoVm-RV8u9Qun4hnt".toCharArray());
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
        System.out.println("res body: " + responseBody);

        Assert.assertTrue(CheckContentCreationResponseCode(statusCode));
       //NodeWriter(responseBody,Nodetype);

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
        System.out.println(CR.Getcookie());
        response = request.headers("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .cookie(CR.Getcookie()).auth().basic("unicc", "5NJjoVm-RV8u9Qun4hnt").given().header("cookie",CR.Getcookie()).when().get(PageURL);
        Document doc = Jsoup.parse(response.getBody().asString());
        Element inputElement = doc.select(cssQuery).first();
            if (inputElement != null) {
                System.out.println("@@@Found");
                System.out.println(inputElement.attr("value"));
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
                            return new PasswordAuthentication("unicc", "5NJjoVm-RV8u9Qun4hnt".toCharArray());
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

    public static String uploadImage(String uploadURL, File fileToUpload, String cookie) throws Exception {
        String value = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadFile = new HttpPost(uploadURL);
            uploadFile.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
            uploadFile.setHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary4zCJHKOjZsq89GTw");
            uploadFile.setHeader("Cookie", cookie);
            uploadFile.setHeader("X-Requested-With", "XMLHttpRequest");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setBoundary("----WebKitFormBoundary4zCJHKOjZsq89GTw");
            builder.addPart("files[field_media_image_0]", new FileBody(fileToUpload, ContentType.IMAGE_JPEG, fileToUpload.getName()));
            builder.addTextBody("field_media_image[0][fids]", "");
            builder.addTextBody("field_media_image[0][focal_point]", "50,50");
            builder.addTextBody("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.properties.getProperty("Add_Media_Image"), "input[name=form_build_id]"));
            builder.addTextBody("form_token", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.properties.getProperty("Add_Media_Image"), "input[name=form_token]"));
            builder.addTextBody("form_id", "media_image_add_form");
            builder.addTextBody("name[0][value]", "");
            builder.addTextBody("field_tags[target_id][textfield]", "");
            builder.addTextBody("field_tags[target_id][value_field]", "\"\"");
            builder.addTextBody("field_media_in_library[value]", "1");
            builder.addTextBody("status[value]", "1");
            builder.addTextBody("field_copyright[0][value]", "");
            builder.addTextBody("advanced__active_tab", "edit-revision-information");
            builder.addTextBody("_triggering_element_name", "field_media_image_0_upload_button");
            builder.addTextBody("_triggering_element_value", "Upload");
            builder.addTextBody("_drupal_ajax", "1");
            builder.addTextBody("ajax_page_state[theme]", "vartheme_claro");
            builder.addTextBody("ajax_page_state[theme_token]", "KGGCRQgXU2FUF9Y6OLO7Yb4HsnVyLqdAEWT5goD4M_A");
            builder.addTextBody("ajax_page_state[libraries]", "eJx9UlF2g0AIvBDRM_QkPlxJ3GZdLLBJ7emLJmrse83XwgADO4DdEHNjzKlFqZ9vZUIE-G-o6vlGErNRtmPa8uqWHANnwBx6libFfK07KSOm6gUCLMaBhzGRUdNRKt9UoyqZViGhMLTCdyXxmFGw-ujCklMP1EU8nVkGcK6BJNA6hAM-6LcVTGv7HTnNI-j7lJ1HaPtAsHijpfqAB04JR6UD6CpFm9bpdtw1tBjmDtjqIzJrKtljn1-FZKr2muwWpvhD4GO9avl04RzTxjzbcGbnbkZ2zg3fIXCFLeaLNiY4rQkHENSXZKFs5asPf4-CNOBIH_MleMy8x6yfcKotQMl9kOZ5JnfCq9avDhTf5tphtperUbihtKh0vCUHraeBmsfaL4lb12-hO2mQOJq-z7HJxbr8AuhQKPA");

            uploadFile.setEntity(builder.build());

            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                System.out.println("Response Body: " + responseBody); // Print the response for debugging

                if (statusCode != 200) {
                    throw new RuntimeException("Failed to upload image. Status code: " + statusCode);
                }

                // Attempt to parse JSON response
                try {
                    // Initialize ObjectMapper from Jackson library
                    ObjectMapper objectMapper = new ObjectMapper();

                    // Parse JSON string to JsonNode
                    JsonNode rootNode = objectMapper.readTree(responseBody);

                    // Iterate through the JSON array
                    for (JsonNode node : rootNode) {
                        // Extract 'data' field which is an array
                        JsonNode dataNode = node.path("data");

                        // Iterate through the 'data' array
                        for (JsonNode innerNode : dataNode) {
                            // Find the node with the specific attribute 'name="field_media_image[0][fids]"'
                            String nameAttribute = innerNode.path("name").asText();
                            // Check if the 'name' attribute matches the desired value
                            if (nameAttribute.equals("field_media_image[0][fids]")) {
                                // Extract the 'value' associated with the 'name' attribute
                                value = innerNode.path("value").asText();
                                System.out.println("Value for name=\"field_media_image[0][fids]\": " + value);
                                break;  // Assuming we found the value, exit the loop
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }



    }











