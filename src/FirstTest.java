import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;


public class FirstTest {
    private AppiumDriver driver;
    String onboardingSkipButtonId = "org.wikipedia:id/fragment_onboarding_skip_button";
    String errorMessageCannotFind = "Cannot find by ";
    String errorMessageCannotSendText = "Cannot send text ";
    String searchWikipediaInputXpath = "//*[contains(@text, \"Search Wikipedia\")]";
    String searchWikipediaInputId = "org.wikipedia:id/search_src_text";
    long defaultTimeOut = 5;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\projects\\mob_auto\\JavaAppiumAutomation_lesson4\\apks\\Wikipedia_2_7.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void testCheckArticleTitle() {
        String searchLine = "Java";
        String articleTitle = "Java (programming language)";
        String articleTitleDescription = "Object-oriented programming language";

        waitForElementAndClick(
                By.id(onboardingSkipButtonId),
                errorMessageCannotFind + onboardingSkipButtonId,
                defaultTimeOut);

        waitForElementAndClick(
                By.xpath(searchWikipediaInputXpath),
                errorMessageCannotFind + searchWikipediaInputXpath,
                defaultTimeOut);

        waitForElementAndSendKeys(
                By.id(searchWikipediaInputId),
                searchLine,
                errorMessageCannotSendText +
                        searchWikipediaInputId + "\nsearchLine: " + searchLine,
                defaultTimeOut);

        waitForElementAndClick(
                By.xpath("//*[contains(@text,\"" + articleTitleDescription + "\")]"),
                errorMessageCannotFind + "//*[contains(@text,\"" + articleTitleDescription + "\")]",
                defaultTimeOut);

        assertElementPresent(
                By.xpath("//*[@class = \"android.view.View\" and contains(@content-desc,\"" + articleTitle + "\")]"),
                errorMessageCannotFind + "//*[@class = \"android.view.View\" and contains(@content-desc,\"" + articleTitle + "\")]");
    }


    private WebElement waitForElementPresent(By by, String error_message, long timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        WebDriverWait wait = new WebDriverWait(driver, 0);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    private void assertElementPresent(By by, String errorMessage) {
        Assert.assertTrue(errorMessage, isElementPresent(by));
    }

}
