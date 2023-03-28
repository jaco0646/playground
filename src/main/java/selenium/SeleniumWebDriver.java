package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.openqa.selenium.chrome.ChromeDriverLogLevel.OFF;

class SeleniumWebDriver {
    static final String BASE_PATH = "C:\\Program Files (x86)\\BraveSoftware\\Brave-Browser\\Application\\";
    static final String BRAVE_PATH = BASE_PATH + "brave.exe";
    static final String DRIVER_PATH = BASE_PATH + "chromedriver111.exe";

    static {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        System.setProperty("webdriver.http.factory", "jdk-http-client");  // This will become the default in later versions of Selenium.
    }

    static WebDriver newBraveDriver() {
        ChromeOptions chromeOptions = new ChromeOptions()
                .setBinary(BRAVE_PATH)
                .setLogLevel(OFF)
                .setHeadless(true);
        return new ChromeDriver(chromeOptions);
    }
}
