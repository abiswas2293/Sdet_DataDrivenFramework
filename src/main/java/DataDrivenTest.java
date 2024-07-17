import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class DataDrivenTest {
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        WebDriverManager.firefoxdriver().setup();
        driver.get("https://admin-demo.nopcommerce.com/login");

//anindita
    }

    @Test(dataProvider = "LoginData")
    public void loginTest(String user, String pwd, String expectedResult) throws InterruptedException {

        driver.findElement(By.xpath("//input[@id='Email']")).clear();
        driver.findElement(By.xpath("//input[@id='Email']")).sendKeys(user);


        driver.findElement(By.xpath("//input[@id='Password']")).clear();
        driver.findElement(By.xpath("//input[@id='Password']")).sendKeys(pwd);


        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        String expUrl = "https://admin-demo.nopcommerce.com/admin/";
        String actualUrl = driver.getCurrentUrl();

        if (expectedResult.equals("Valid")) {
            if (expUrl.equals(actualUrl)) {
                Thread.sleep(5000);
                driver.findElement(By.xpath("//a[text()='Logout']")).click();
                Assert.assertTrue(true);
            } else {
                Assert.assertTrue(false);
            }
        } else if (expectedResult.equals("invalid")) {
            if (expUrl.equals(actualUrl)) {
                driver.findElement(By.xpath("//a[text()='Logout']")).click();
                Assert.assertTrue(false);

            } else {
                Assert.assertTrue(true);
            }

        }


    }

    @DataProvider(name = "LoginData")
    public Object[][] getData() {
        String loginData[][] = {
                {"admin@yourstore.com", "admin", "Valid"},
                {"admin@yourstore.com", "adm", "Invalid"}
        };
        return loginData;
    }

    @AfterTest
    public void tearDown() {
        //driver.close();
    }
}
