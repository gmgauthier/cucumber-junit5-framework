package junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tools.BrowserDriver;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.fail;


public class BrowserTests {
    private RemoteWebDriver driver;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @BeforeEach
    void setupThis(){
        System.out.println(dtf.format(LocalDateTime.now()) + " starting");
    }

    @DisplayName("Browser Validation")
    @ParameterizedTest(name = "Browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge", "safari" })
    void testBrowsers(String browser) {
        driver = BrowserDriver.getDriver(browser, true);
        System.out.println(dtf.format(LocalDateTime.now()) + " Navigating to url...");
        String url = "https://duckduckgo.com/";
        driver.get(url);
        if (!driver.getCurrentUrl().equals(url)){
            fail("Browser navigation failed");
        }
        System.out.println(dtf.format(LocalDateTime.now()) + " Entering search now...");
        RemoteWebElement searchBox =
                (RemoteWebElement)driver.findElementByXPath("//input[@id='search_form_input_homepage']");
        searchBox.sendKeys("frankenberries");
        searchBox.sendKeys(Keys.ENTER);
        System.out.println(dtf.format(LocalDateTime.now())+ " Checking search results...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10),Duration.ofMillis(100));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='links']")));
        RemoteWebElement results =
                (RemoteWebElement)driver.findElementByPartialLinkText("Monster cereal");
        System.out.println(results.getText());
        Assertions.assertNotNull(results);
    }


    @AfterEach
    void tearThis(){
        driver.quit();
        System.out.println(dtf.format(LocalDateTime.now()) + " finished");
    }
}
