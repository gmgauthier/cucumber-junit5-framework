package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;


import static org.junit.jupiter.api.Assertions.fail;

public class Stepdefs {
    private RemoteWebDriver driver;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        if (driver!=null) {
            driver.quit();
        }
    }

    @Given("I have a driver for {string}")
    public void i_have_a_driver_for(String browser) {
        driver = generateDriver(browser);
    }

    @When("I navigate to test.io")
    public void i_navigate_to_test_io() {
        driver.get("https://test.io/");
        if (!driver.getCurrentUrl().equals("https://test.io/")){
            fail("Browser navigation failed");
        }
    }

    @Then("The page is displayed")
    public void the_success_message_is_displayed() {
        Assertions.assertEquals("QA Testing as a Service | test IO", driver.getTitle());
    }

    //helpers
    public RemoteWebDriver generateDriver(final String browser) {
        if (browser.equals("chrome")){
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            options.addArguments("--ignore-certificate-errors"); // only for limited test envs
            return new ChromeDriver(options);
        } else if (browser.equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(true);
            options.addArguments("--ignore-certificate-errors");
            return new FirefoxDriver(options);
        }
        return null;
    }
}
