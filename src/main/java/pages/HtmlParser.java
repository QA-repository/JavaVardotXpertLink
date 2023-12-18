package pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HtmlParser {

    public static void main(String[] args) {
        try {
            // Read the HTML file from the local system
            File htmlFile = new File("C:\\Users\\Vardot QA\\Desktop\\createlandingpage.html"); // Replace with your local file path
            Document doc = Jsoup.parse(htmlFile, "UTF-8", "");

            // Extract all label elements with 'form-item__label' class
            Elements labelElements = doc.select("label.form-item__label");

            // Save the result in an Excel sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Field Details");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Field Title");
            headerRow.createCell(1).setCellValue("Required");

            int rowNum = 1;
            for (Element labelElement : labelElements) {
                String fieldTitle = labelElement.ownText();
                String required = labelElement.hasClass("js-form-required") ? "Yes" : "No";

                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(fieldTitle);
                dataRow.createCell(1).setCellValue(required);
            }

            // Specify the output file path
            String outputFilePath = "FieldDetails.xlsx";
            FileOutputStream fileOut = new FileOutputStream(outputFilePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("Extraction and save successful. Saved to: " + outputFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
