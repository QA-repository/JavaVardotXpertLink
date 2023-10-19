package src.test.tests;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WriteDataToExcel {

    public static void WriteDataToExcelSheet(String URL,int Responsecode,int WhereToWrite)throws Exception
    {
String Rspcode=String.valueOf(Responsecode);
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet spreadsheet
                = workbook.createSheet(" Failed Links");


        Map<String, Object[]> LinkData
                = new TreeMap<String, Object[]>();

        LinkData.put(
                "0",
                new Object[] { "Link", "Response Code" });
        LinkData.put(
                String.valueOf(WhereToWrite),
                new Object[] { URL,Rspcode});


        Set<String> keyid = LinkData.keySet();

        int rowid = 0;


        for (String key : keyid) {

            XSSFRow row = spreadsheet.createRow(rowid++);
            Object[] objectArr = LinkData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }


        FileOutputStream out = new FileOutputStream(
                new File( System.getProperty("user.dir")+"/Duplication-List"+System.currentTimeMillis()+".xlsx"));

        workbook.write(out);
        out.close();
    }
}