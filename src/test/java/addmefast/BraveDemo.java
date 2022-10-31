//package addmefast;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//public class BraveDemo {
//
//    WebDriver driver;
//
//    @BeforeClass
//    void setupClass() {
//        WebDriverManager.chromedriver().setup();
//        //System.setProperty("webdriver.chrome.driver", "path of chrome driver 81(i.e. compatable) version");
//        ChromeOptions ops = new ChromeOptions()
//                .setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe"); // this is just example
//
//        ops.addArguments("--no-sandbox");
//        ops.addArguments("--disable-notifications");
//        ops.addArguments("start-maximized");
//        ops.addArguments("disable-infobars");
//        ops.addArguments("--user-data-dir=C:\\Users\\Administrator\\AppData\\Local\\BraveSoftware\\Brave-Browser\\temp");  //đường đẫn đến profile
//        ops.addArguments("profile-directory=fb-profile");
//           // ops.addArguments("headless");               // chạy ngầm
//        // ops.addArguments("window-size=1200x600"); // set kích thước
//
//        driver = new ChromeDriver(ops);
//    }
//
////    @BeforeTest
////    void setupTest() {
////        driver = new ChromeDriver();
////    }
//
//    @AfterTest
//    void teardown() {
//        //driver.quit();
//    }
//
//    @Test
//    void test() throws Exception {
//        // Exercise
//        driver.get("https://m.facebook.com/Xomvuichoi1368");
//        String title = driver.getTitle();
//
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//
//
//        // This  will scroll down the page by  1000 pixel vertical
//        js.executeScript("window.scrollBy(0,2000)");
//
////*[@id="u_a_18_ni"]/div/header/div[2]/div/div/div[1]/div/a/abbr
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
//
//        WebElement newestPost = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/div/div[4]/div/div[4]/div[3]/div[1]/div/article/div/header/div[2]/div/div/div[1]/div/a"));
//
//        System.out.println(newestPost.findElement(By.tagName("abbr")).getText());
//        System.out.println(newestPost.getAttribute("href"));
//        Uri uri = Uri.parse(url);
//        newestPost.click();
//        // Verify
//       // assert (title).contains("Facebook – log in or sign up");
//    }
//
//    public static Map<String, List<String>> getQueryParams(String url) {
//        try {
//            Map<String, List<String>> params = new HashMap<String, List<String>>();
//            String[] urlParts = url.split("\\?");
//            if (urlParts.length > 1) {
//                String query = urlParts[1];
//                for (String param : query.split("&")) {
//                    String[] pair = param.split("=");
//                    String key = URLDecoder.decode(pair[0], "UTF-8");
//                    String value = "";
//                    if (pair.length > 1) {
//                        value = URLDecoder.decode(pair[1], "UTF-8");
//                    }
//
//                    List<String> values = params.get(key);
//                    if (values == null) {
//                        values = new ArrayList<String>();
//                        params.put(key, values);
//                    }
//                    values.add(value);
//                }
//            }
//
//            return params;
//        } catch (Exception ex) {
//            ex.getMessage()
//        }
//    }
//
//    public void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
//        //Convert web driver object to TakeScreenshot
//        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
//        //Call getScreenshotAs method to create image file
//        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
//        //Move image file to new destination
//        File DestFile = new File(fileWithPath);
//        //Copy file at destination
//        FileUtils.copyFile(SrcFile, DestFile);
//    }
//
//}
