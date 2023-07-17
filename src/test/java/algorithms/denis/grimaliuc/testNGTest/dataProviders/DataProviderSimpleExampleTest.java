package algorithms.denis.grimaliuc.testNGTest.dataProviders;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Path;

public class DataProviderSimpleExampleTest {

    WebDriver driver;

    @DataProvider(name = "test-data")
    public Object[][] dataProvFunc() {
        return new Object[][]{
                {"Lambda Test"}, {"Automation"}
        };
    }

    @DataProvider(name = "test-data2")
    public Object[][] dataProvFunc2() {
        return new Object[][]{
                {"Selenium", "Delhi"}, {"QTP", "Bangalore"}, {"LoadRunner", "Chennai"}
        };
    }

    @BeforeMethod
    public void setUp() {

        System.out.println("Start test");
        var pathToChrome = Path.of(System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe").normalize().toString();
        System.setProperty("webdriver.chrome.driver", pathToChrome);
        driver = new ChromeDriver();
        String url = "https://www.google.com";
        driver.get(url);
        driver.manage().window().maximize();

    }

    //Passing the dataProvider to the test method through @Test annotation
    @Test(dataProvider = "test-data")
    public void search(String keyWord) {
        // txtBox.sendKeys(keyWord1," ",keyWord2);
        //Reporter.log("Keyword entered is : " +keyWord1+ " " +keyWord2);
        WebElement txtBox = driver.findElement(By.cssSelector("textarea[name='q']"));
        txtBox.sendKeys(keyWord);
        Reporter.log("Keyword entered is : " + keyWord);
        txtBox.sendKeys(Keys.ENTER);
        Reporter.log("Search results are displayed.");
    }


    @AfterMethod
    public void burnDown() {
        driver.quit();
    }
}
