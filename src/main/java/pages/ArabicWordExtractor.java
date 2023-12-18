package pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArabicWordExtractor {

    public static void main(String[] args) {
        // Replace 'your_file_path_here' with the path to your HTML file
        String filePath = "C:/Users/Vardot QA/Desktop/ar.html";

        try {
            // Read the HTML file
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8", "");

            // Extract all text from the webpage
            String text = doc.text();

            // Extract Arabic words from the text
            List<String> arabicWords = extractArabicWords(text);

            // Print the Arabic words
            if (!arabicWords.isEmpty()) {
                System.out.println("Arabic words found in the HTML page:");
                for (String word : arabicWords) {
                    System.out.println(word);
                }
            } else {
                System.out.println("No Arabic words found in the HTML page.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> extractArabicWords(String text) {
        List<String> arabicWords = new ArrayList<>();
        BreakIterator breakIterator = BreakIterator.getWordInstance(new Locale("ar"));
        breakIterator.setText(text);
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = breakIterator.next();
            if (lastIndex != BreakIterator.DONE && hasArabicCharacters(text.substring(firstIndex, lastIndex))) {
                arabicWords.add(text.substring(firstIndex, lastIndex));
            }
        }
        return arabicWords;
    }

    private static boolean hasArabicCharacters(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (isArabicCharacter(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isArabicCharacter(char c) {
        return (c >= 0x0600 && c <= 0x06E0); // Range for Arabic characters
    }
}
