package tools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;



public class BrowserDriver extends RemoteWebDriver {
    private final RemoteWebDriver driver;
    private final String browser;
    private final Boolean headless;

    public BrowserDriver(final String browser, final Boolean headless){
        this.browser = browser;
        this.headless = headless;

        switch (browser) {
            case "chrome":  this.driver = getChrome(headless);
                break;
            case "firefox":  this.driver = getFirefox(headless);
                break;
            case "edge":  this.driver = getEdge(headless); // headless unsupported until selenium 4
                break;
            case "safari":  this.driver = getSafari(headless); // headless unsupported indefinitely
                break;
            default: throw new IllegalArgumentException("Invalid browser specified");
        }
    }

    public RemoteWebDriver getDriver(){
        return this.driver;
    }

    private RemoteWebDriver getChrome(final Boolean headless){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--ignore-certificate-errors"); // only for limited test envs
        return new ChromeDriver(options);
    }

    private RemoteWebDriver getFirefox(final Boolean headless){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        options.addArguments("--ignore-certificate-errors");
        return new FirefoxDriver(options);
    }

    private RemoteWebDriver getEdge(final Boolean headless){
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        //if (headless.equals(true)){ // necessary for Selenium 3
        //    throw new UnsupportedOperationException("Edge does not support headless execution yet");
        //}
        //options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        //** the following will only work once Selenium 4 is ready.
        options.setAcceptInsecureCerts(true);
        if (headless){
           options.addArguments("headless");
        }
        return new EdgeDriver(options);
    }

    private SafariDriver getSafari(final Boolean headless){
        if (headless.equals(true)){
            throw new UnsupportedOperationException("Safari does not support headless execution yet");
        }
        SafariOptions options = new SafariOptions();
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return new SafariDriver(options);
    }
}
