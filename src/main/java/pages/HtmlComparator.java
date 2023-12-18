package pages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HtmlComparator {

    public static void main(String[] args) {

        String uiFilePath = "C:/Users/Vardot QA/Desktop/devsite.html";
        String expectedFilePath = "C:/Users/Vardot QA/Desktop/435site.html";

        try {
            String uiContent = readFileContent(uiFilePath);
            String expectedContent = readFileContent(expectedFilePath);

            if (areContentsEqual(uiContent, expectedContent)) {
                System.out.println("The content displayed on the UI is the same as expected.");
            } else {
                System.out.println("The content displayed on the UI is different. Differences:");

                // Print the differences
                printDifferences(uiContent, expectedContent);
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }

    private static String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static boolean areContentsEqual(String content1, String content2) {
        return content1.equals(content2);
    }

    private static void printDifferences(String content1, String content2) {
        int minLength = Math.min(content1.length(), content2.length());

        for (int i = 0; i < minLength; i++) {
            if (content1.charAt(i) != content2.charAt(i)) {
                // Print the differing character in red
                System.out.print("\u001B[31m" + content1.charAt(i) + "\u001B[0m");
            } else {
                System.out.print(content1.charAt(i));
            }
        }

        // Print the remaining characters in the longer string
        if (content1.length() > content2.length()) {
            System.out.print("\u001B[31m" + content1.substring(minLength) + "\u001B[0m");
        } else if (content1.length() < content2.length()) {
            System.out.print("\u001B[31m" + content2.substring(minLength) + "\u001B[0m");
        }

        System.out.println();
    }
}
