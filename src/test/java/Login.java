import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.fail;

public class Login {
    FirefoxDriver driver;
    ProfilesIni firProfiles;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeTest
    public void setup() {
        //Access firefox browser profile "certificateIssue" to use It In test.
        firProfiles = new ProfilesIni();
        FirefoxProfile wbdrverprofile = firProfiles.getProfile("default");
        wbdrverprofile.setAcceptUntrustedCertificates(true);
        wbdrverprofile.setAssumeUntrustedCertificateIssuer(false);

        driver = new FirefoxDriver(wbdrverprofile);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://192.168.100.26/");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @DataProvider
    public Object[][] loginData() {
        return new Object[][]{
                new Object[]{"EugenBorisik", "qwerty12345"},
                new Object[]{"EugenBorisik", "123123"},
                new Object[]{"LVtest", "qwerty12345"},
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String login, String password) throws InterruptedException {
        try {
            WebElement loginField = driver.findElement(By.id("Username"));
            loginField.clear();
            loginField.sendKeys(login);

            WebElement passField = driver.findElement(By.id("Password"));
            passField.clear();
            passField.sendKeys(password);

            Thread.sleep(1000); //This is not a good way to use it because that doesn't make our tests run as quickly as possible
            WebElement loginButton = driver.findElement(By.id("SubmitButton"));
            loginButton.click();
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }

        try {
            WebElement officeTab = driver.findElement(By.cssSelector("#officeMenu"));
            officeTab.click();

            WebElement searchOfficeField = (new WebDriverWait(driver, 15))
                    .until(new ExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver d) {
                            return d.findElement(By.cssSelector("#input-search"));
                        }
                    });
            Assert.assertTrue(searchOfficeField.isDisplayed());

        } catch (NoSuchElementException e) {
            fail("Element not found");
            e.printStackTrace();
        }

        WebElement logoutLink = (new WebDriverWait(driver, 10)) // Explicit waiter task 40 p.3
                .until(ExpectedConditions.presenceOfElementLocated(By.className("sign-out-span")));

        Assert.assertTrue(logoutLink.isDisplayed());

    }

}