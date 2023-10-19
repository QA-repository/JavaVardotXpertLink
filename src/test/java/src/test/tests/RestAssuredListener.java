package src.test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static src.test.tests.TestBases.extent;


public class RestAssuredListener implements ITestListener {
int Passed_Test_Cases;
int Failed_Test_Cases;
int Skipped_Test_Cases;
    ExtentTest test;

    List<String> Passed_Test_CasesList = new ArrayList<>();
List<String> Failed_Test_CasesList= new ArrayList<>();;
List<String> Skipped_Test_CasesList= new ArrayList<>();;

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting test: " + result.getMethod().getMethodName());
        test = extent.createTest(result.getMethod().getMethodName());
        test.log(Status.INFO, "Test Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getMethod().getMethodName());
        Passed_Test_Cases=Passed_Test_Cases+1;
        Passed_Test_CasesList.add(result.getMethod().getMethodName());
        test.log(Status.PASS, "Test Passed");
        test.assignCategory(Arrays.toString(result.getMethod().getGroups()));
    }
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test passed: " + result.getMethod().getMethodName());
        Skipped_Test_Cases=Skipped_Test_Cases+1;
        Skipped_Test_CasesList.add(result.getMethod().getMethodName());
        test.log(Status.SKIP, "Test Skipped");
        test.assignCategory(Arrays.toString(result.getMethod().getGroups()));

    }
    @Override
    public void onTestFailure(ITestResult result) {
        System.err.println("Test failed: " + result.getMethod().getMethodName());
        Failed_Test_Cases=Failed_Test_Cases+1;
        Failed_Test_CasesList.add(result.getMethod().getMethodName());
        test.log(Status.PASS, "Test Failed");
        test.assignCategory(Arrays.toString(result.getMethod().getGroups()));

    }
    @Override
    public void onFinish(ITestContext context) {

        String[][] data = {
                {"Test Cases Passed: ", "Test Cases Failed: ", "Test Cases Skipped: ","Passed Test Cases List: ","Failed Test Cases List: ","Skipped Test Cases List: "},

                {String.valueOf(Passed_Test_Cases), String.valueOf(Failed_Test_Cases), String.valueOf(Skipped_Test_Cases),Passed_Test_CasesList.toString(),Failed_Test_CasesList.toString(),Skipped_Test_CasesList.toString()}

        };

        int[] columnWidths = new int[data[0].length];
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                if (row[i].length() > columnWidths[i]) {
                    columnWidths[i] = row[i].length();
                }
            }
        }

        for (int i = 0; i < data[0].length; i++) {
            System.out.print(padRight(data[0][i], columnWidths[i] + 2));
        }
        System.out.println();

        // Print the table data
        for (int row = 1; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                System.out.print(padRight(data[row][col], columnWidths[col] + 2));
            }
            System.out.println();
        }
    }

    private static String padRight(String s, int width) {
        return String.format("%-" + width + "s", s);
    }
    public static List<String> splitStrings(List<String> originalList) {
        List<String> splitList = new ArrayList<>();

        for (String originalString : originalList) {
            // Split the original string at commas
            String[] parts = originalString.split(",");

            // Add each part to the split list
            for (String part : parts) {
                splitList.add(part.trim()); // Remove leading/trailing spaces if needed
            }
        }

        return splitList;
    }
}