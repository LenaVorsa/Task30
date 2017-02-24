import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;
import static org.testng.Assert.assertEquals;


public class Login {
    FirefoxDriver driver;
    ProfilesIni firProfiles;

    @BeforeTest
    public void setup() {
        //Access firefox browser profile "certificateIssue" to use It In test.
        firProfiles = new ProfilesIni();
        FirefoxProfile wbdrverprofile = firProfiles.getProfile("certificateIssue");
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

    @Test
    public void loginTest() {
        WebElement loginField = driver.findElement(By.id("Username"));
        loginField.sendKeys("EugenBorisik");

        WebElement passField = driver.findElement(By.id("Password"));
        passField.sendKeys("qwerty12345");

        WebElement loginButton = driver.findElement(By.id("SubmitButton"));
        loginButton.click();

        WebElement logoutLink = driver.findElement(By.className("sign-out-span"));

        Assert.assertEquals(true, logoutLink.isDisplayed());
    }
/*
    //Task 2. Create By variables, which covers all possible types of location in Selenium WebDriver.
    WebElement loginField = driver.findElement(By.id("Username"));
    WebElement submitButton = driver.findElement(By.className("submit-button"));
    WebElement submitButton2 = driver.findElement(By.linkText("submit"));
    WebElement checkBox = driver.findElement(By.xpath("//label[@for='Remember']/span"));
    WebElement passwordInput = driver.findElement(By.cssSelector("#Password"));

*/
}