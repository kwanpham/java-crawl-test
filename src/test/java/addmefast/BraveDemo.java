package addmefast;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class BraveDemo {

    WebDriver driver;

    @BeforeClass
    void setupClass() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver", "path of chrome driver 81(i.e. compatable) version");
        ChromeOptions ops = new ChromeOptions()
                .setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe"); // this is just example

        ops.addArguments("--no-sandbox");
        ops.addArguments("--disable-notifications");
        ops.addArguments("start-maximized");
        ops.addArguments("disable-infobars");
        ops.addArguments("--user-data-dir=C:\\Users\\Administrator\\AppData\\Local\\BraveSoftware\\Brave-Browser\\temp");  //đường đẫn đến profile
        ops.addArguments("profile-directory=fb-profile");
           // ops.addArguments("headless");               // chạy ngầm
        // ops.addArguments("window-size=1200x600"); // set kích thước

        driver = new ChromeDriver(ops);
    }

//    @BeforeTest
//    void setupTest() {
//        driver = new ChromeDriver();
//    }

    @AfterTest
    void teardown() {
        //driver.quit();
    }

    @Test
    void test() throws Exception {
        // Exercise
        driver.get("https://www.facebook.com/Xomvuichoi1368/posts/103095191629493");
        String title = driver.getTitle();

        this.takeSnapShot(driver, "image/test.png");
        // Verify
        assert (title).contains("Facebook – log in or sign up");
    }

    public void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        //Call getScreenshotAs method to create image file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File DestFile = new File(fileWithPath);
        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
    }

}
