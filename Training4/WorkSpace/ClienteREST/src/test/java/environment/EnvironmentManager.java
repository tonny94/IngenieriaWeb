package environment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class EnvironmentManager {
	public static void initChromeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/home/jpau/university/wecc/springboot-test-selenium/chromedriver");
        WebDriver driver = new ChromeDriver();
        RunEnvironment.setWebDriver(driver);
    }

    public static void initFirefoxWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/home/jpau/university/wecc/springboot-test-selenium/geckodriver");
        WebDriver driver = new FirefoxDriver();
        RunEnvironment.setWebDriver(driver);
    }

    public static void shutDownDriver() {
        RunEnvironment.getWebDriver().quit();
    }
}
