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
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class BrowserDriver extends RemoteWebDriver {
    private final RemoteWebDriver driver;
    private final String osName = System.getProperty("os.name");

    public BrowserDriver(final String browser, final Boolean headless) {

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
        options.setHeadless(headless);
        options.addArguments("--ignore-certificate-errors"); // only for limited test envs
        return new ChromeDriver(options);
    }

    private RemoteWebDriver getFirefox(final Boolean headless){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(headless);
        options.addArguments("--ignore-certificate-errors");
        return new FirefoxDriver(options);
    }

    private RemoteWebDriver getEdge(final Boolean headless){
        List<String> osNames = Arrays.asList("Windows 10", "Mac OS X");
        if (!osNames.contains(osName)){
            throw new UnreachableBrowserException("Edge browser not available on this platform");
        }
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
        String osName = System.getProperty("os.name");
        if (!osName.contentEquals("Mac OS X")){
            throw new UnreachableBrowserException("Safari browser not available on this platform");
        }
        if (headless.equals(true)){
            throw new UnsupportedOperationException("Safari does not support headless execution yet");
        }
        SafariOptions options = new SafariOptions();
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return new SafariDriver(options);
    }

    private Path findFile(Path targetDir, String fileName) throws IOException {
        return Files.list(targetDir).filter( (p) -> {
            if (Files.isRegularFile(p)) {
                return p.getFileName().toString().equals(fileName);
            } else {
                return false;
            }
        }).findFirst().orElse(null);
    }

}
