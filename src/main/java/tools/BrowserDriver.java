package tools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class BrowserDriver {
    private static final String osName = System.getProperty("os.name");

    public static RemoteWebDriver getDriver(final String browser, final Boolean headless) {
        switch (browser) {
            case "chrome":
                return getChrome(headless);
            case "firefox":
                return getFirefox(headless);
            case "edge":
                return getEdge(headless); // headless unsupported until selenium 4
            case "safari":
                return getSafari(false); // headless unsupported indefinitely
            default: throw new IllegalArgumentException("Invalid browser specified");
        }
    }

    private static RemoteWebDriver getChrome(final Boolean headless){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(headless);
        options.setAcceptInsecureCerts(true);
        RemoteWebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    private static RemoteWebDriver getFirefox(final Boolean headless){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(headless);
        options.setAcceptInsecureCerts(true);
        RemoteWebDriver driver =  new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        return driver;
    }

    private static RemoteWebDriver getEdge(final Boolean headless){
        List<String> osNames = Arrays.asList("Windows 10", "Mac OS X");
        if (!osNames.contains(osName)){
            throw new UnreachableBrowserException("Edge browser not available on this platform");
        }
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.setAcceptInsecureCerts(true);
        //if (headless.equals(true)){ // necessary for Selenium 3
        //    throw new UnsupportedOperationException("Edge does not support headless execution yet");
        //}
        //options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        //** the following will only work once Selenium 4 is ready.
        if (headless){
           options.addArguments("headless");
        }
        RemoteWebDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    private static RemoteWebDriver getSafari(final Boolean headless){
        String osName = System.getProperty("os.name");
        if (!osName.contentEquals("Mac OS X")){
            throw new UnreachableBrowserException("Safari browser not available on this platform");
        }
        if (headless.equals(true)){
            throw new UnsupportedOperationException("Safari does not support headless execution yet");
        }
        SafariOptions options = new SafariOptions();
        //options.setAcceptInsecureCerts(true);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        RemoteWebDriver driver = new SafariDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

}
