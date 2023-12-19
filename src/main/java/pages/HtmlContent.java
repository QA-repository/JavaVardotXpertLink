package pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HtmlContent {
@Test
    public static void main(String[] args) {
        try {
            File file1 = new File("C:/Users/Vardot QA/Desktop/devsite.html");

            File file2= new File("C:/Users/Vardot QA/Desktop/435site.html");
            System.out.println("Label '" + "l" + "' is present in file1 but not in file2.");

            Set<String> labels1 = extractLabels(file1);
            Set<String> labels2 = extractLabels(file2);

            compareLabels(labels1, labels2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> extractLabels(File file) throws IOException {
        Set<String> labels = new HashSet<>();
        Document doc = Jsoup.parse(file, "UTF-8");
        Elements elements = doc.select("label");
        for (Element element : elements) {
            labels.add(element.text());
        }
        return labels;
    }

    private static void compareLabels(Set<String> labels1, Set<String> labels2) {
        for (String label : labels1) {
            if (!labels2.contains(label)) {
                System.out.println("Label '" + label + "' is present in file1 but not in file2.");
            }
        }
    }
}
