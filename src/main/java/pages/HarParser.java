package pages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HarParser {

    public static void main(String[] args) {
        // Replace 'your_har_file.har' with the path to your HAR file
        String harFilePath ="C:\\Users\\Vardot QA\\Desktop\\DRUPICC-2.txt";

        try {
            // Read the HAR data from the file
            File harFile = new File(harFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode harNode = objectMapper.readTree(harFile);

            // Extract response times and timestamps
            JsonNode logNode = harNode.path("log");
            JsonNode entriesNode = logNode.path("entries");

            for (JsonNode entryNode : entriesNode) {
                JsonNode timingsNode = entryNode.path("timings");

                long sendTime = timingsNode.path("send").asLong();
                long waitTime = timingsNode.path("wait").asLong();
                long receiveTime = timingsNode.path("receive").asLong();

                // Calculate total response time in seconds
                long totalResponseTime = sendTime + waitTime + receiveTime;

                // Convert totalResponseTime to minutes as a double
                double responseTimeInMinutes = totalResponseTime / 1000.0 / 60.0;

                // Get timestamp of the request
                long timestamp = entryNode.path("startedDateTime").asLong();
                Date date = new Date(timestamp);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // Print response time and timestamp
                System.out.println("Request Response Time: " + responseTimeInMinutes + " minutes");
                System.out.println("Timestamp: " + dateFormat.format(date));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
