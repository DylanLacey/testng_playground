import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class homepageTest {

  private WebDriver driver;

  public String generateTestName(Method testMethod) {
      String testName = testMethod.getName();

      String className = testMethod.getDeclaringClass().getName();
      String splitTestName = StringUtils.join(
          StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(testName)),
          ' '
      );

      String readableTestName = StringUtils.join(new String[]{className, splitTestName}, ' ');

      return readableTestName;
  }

  @BeforeMethod
  public void setupTestMethod(Method testMethod) throws MalformedURLException {
    String sauceUserName = System.getenv("SAUCE_USERNAME");
    String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
    String sauceURL = "https://ondemand.saucelabs.com/wd/hub";

    String testName = generateTestName(testMethod);

    /**
     * * Here we set the MutableCapabilities for "sauce:options", which is required for newer versions of Selenium
     * and the w3c protocol, for more info read the documentation:
     * https://wiki.saucelabs.com/display/DOCS/Selenium+W3C+Capabilities+Support+-+Beta */
    MutableCapabilities sauceOpts = new MutableCapabilities();
    sauceOpts.setCapability("username", sauceUserName);
    sauceOpts.setCapability("accessKey", sauceAccessKey);
    /** In order to use w3c you must set the seleniumVersion **/
    sauceOpts.setCapability("seleniumVersion", "3.141.59");
    sauceOpts.setCapability("name", testName);
    sauceOpts.setCapability("screenResolution", "1680x1050");

    /**
     * in this exercise we set additional capabilities below that align with
     * testing best practices such as tags, timeouts, and build name/numbers.
     *
     * Tags are an excellent way to control and filter your test automation
     * in Sauce Analytics. Get a better view into your test automation.
     */

    /** Tags are an excellent way to control and filter your test automation
     * in Sauce Analytics. Get a better view into your test automation.
     */
    List<String> tags = Arrays.asList("sauceDemo", "demoTest", "module4");
    sauceOpts.setCapability("tags", tags);

    /** Another of the most important things that you can do to get started
     * is to set timeout capabilities for Sauce based on your organizations needs. For example:
     * How long is the whole test allowed to run?*/
    sauceOpts.setCapability("maxDuration", 3600);
    /** A Selenium crash might cause a session to hang indefinitely.
     * Below is the max time allowed to wait for a Selenium command*/
    sauceOpts.setCapability("commandTimeout", 600);
    /** How long can the browser wait for a new command */
    sauceOpts.setCapability("idleTimeout", 1000);

    /** Setting a build name is one of the most fundamental pieces of running
     * successful test automation. Builds will gather all of your tests into a single
     * 'test suite' that you can analyze for results.
     * It's a best practice to always group your tests into builds. */
    sauceOpts.setCapability("build", "Onboarding Sample App - Java-TestNG");


    /** Required to set w3c protoccol **/
    ChromeOptions chromeOpts = new ChromeOptions();
    chromeOpts.setExperimentalOption("w3c", true);

    /** Set a second MutableCapabilities object to pass Sauce Options and Chrome Options **/
    MutableCapabilities capabilities = new MutableCapabilities();
    capabilities.setCapability("sauce:options", sauceOpts);
    capabilities.setCapability("goog:chromeOptions", chromeOpts);
    capabilities.setCapability("browserName", "chrome");
    capabilities.setCapability("platformVersion", "Windows 10");
    capabilities.setCapability("browserVersion", "latest");

    /** If you're accessing the EU data center, use the following endpoint:.
     * https://ondemand.eu-central-1.saucelabs.com/wd/hub
     * */
    driver = new RemoteWebDriver(new URL(sauceURL), capabilities);
  }

  @Test
  public void shouldVisitCashdeck() {
    /** Don't forget to enter in your application's URL in place of 'https://www.saucedemo.com'. */
    driver.navigate().to("https://www.cashdeck.com.au");
    Assert.assertEquals(driver.getTitle(), "CashDeck");

  }

    @Test
    public void shouldHaveAPersonalPageHero() {
        /** Don't forget to enter in your application's URL in place of 'https://www.saucedemo.com'. */
        driver.navigate().to("https://www.cashdeck.com.au");

        WebElement personalButton = driver.findElement(By.className("personal"));
        personalButton.click();

        Assert.assertEquals(driver.getTitle(), "CashDeck");
        Assert.assertEquals(driver.findElements(By.cssSelector(".hero.partners.index")).size(), 1);
    }

    @Test
    public void shouldHaveABrokerHeroPage() {
        /** Don't forget to enter in your application's URL in place of 'https://www.saucedemo.com'. */
        driver.navigate().to("https://www.cashdeck.com.au");

        WebElement personalButton = driver.findElement(By.className("brokers"));
        personalButton.click();

        Assert.assertEquals(driver.getTitle(), "CashDeck");
        Assert.assertEquals(driver.findElements(By.cssSelector(".hero.brokers.index")).size(), 1);
    }

    @Test
    public void shouldHaveAnAdviserHeroPage() {
        /** Don't forget to enter in your application's URL in place of 'https://www.saucedemo.com'. */
        driver.navigate().to("https://www.cashdeck.com.au");

        WebElement personalButton = driver.findElement(By.className("professional"));
        personalButton.click();

        Assert.assertEquals(driver.getTitle(), "CashDeck");
        Assert.assertEquals(driver.findElements(By.cssSelector(".hero.partners.index")).size(), 1);
    }

    @Test
    public void shouldHaveACustomHeroPage() {
        /** Don't forget to enter in your application's URL in place of 'https://www.saucedemo.com'. */
        driver.navigate().to("https://www.cashdeck.com.au");

        WebElement personalButton = driver.findElement(By.className("custom"));
        personalButton.click();

        Assert.assertEquals(driver.getTitle(), "CashDeck");
        Assert.assertEquals(driver.findElements(By.cssSelector(".hero.partners.index")).size(), 1);
    }

    @Test
    public void customPageShouldHaveSecurityLinks() {
        /** Don't forget to enter in your application's URL in place of 'https://www.saucedemo.com'. */
        driver.navigate().to("https://www.cashdeck.com.au");

        WebElement personalButton = driver.findElement(By.className("custom"));
        personalButton.click();

        Assert.assertEquals(driver.getTitle(), "CashDeck");
        Assert.assertEquals(driver.findElements(By.cssSelector(".hero.partners.index")).size(), 1);

        WebElement sec_button = driver.findElement(By.linkText("Security"));
        sec_button.click();

        Assert.assertEquals(driver.getTitle(), "CashDeck");
        Assert.assertEquals(driver.findElements(By.cssSelector(".hero.partner.security")).size(), 1);
    }



  @AfterMethod
  public void cleanUpAfterTestMethod(ITestResult result) {
    ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
    driver.quit();
  }
}