package dataProviders;

import org.testng.annotations.DataProvider;
import utils.ExcelUtils;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws Exception {
        // ✅ Correct dynamic path for your Excel file
        String excelPath = System.getProperty("user.dir") + "/src/test/java/testData/LoginData.xlsx";
        String sheetName = "Sheet1";

        // ✅ Calling Excel utility method
        return ExcelUtils.getTestData(excelPath, sheetName);
    }
}
