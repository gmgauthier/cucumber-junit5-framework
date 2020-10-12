package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import tools.BrowserDriver;


import static org.junit.jupiter.api.Assertions.fail;

public class Stepdefs {
    private String url;
    protected RemoteWebDriver driver;

    @Before
    public void setUp() {
        url = "https://test.io/";
    }

    @After
    public void tearDown() {
        if (driver!=null) {
            driver.quit();
        }
    }

    @Given("I have a driver for {string}")
    public void i_have_a_driver_for(String browser) {
        Boolean headless = true;
        if (browser.contains("safari")){
            System.out.println(System.getProperty("os.name"));
            if (!System.getProperty("os.name").contains("Mac OS X")){
                throw new UnreachableBrowserException("Safari browser not available on this platform");
            }
            headless = false;
        }
        driver = new BrowserDriver(browser, headless).getDriver();
    }

    @When("I navigate to test.io")
    public void i_navigate_to_test_io() {
        driver.get(url);
        if (!driver.getCurrentUrl().equals(url)){
            fail("Browser navigation failed");
        }
    }

    @Then("The page is displayed")
    public void the_success_message_is_displayed() {
        Assertions.assertEquals("QA Testing as a Service | test IO", driver.getTitle());
    }

    public String getOperatingSystem() {
        String os = System.getProperty("os.name");
        return os;
    }

}
