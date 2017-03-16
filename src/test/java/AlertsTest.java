import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class AlertsTest {
    FirefoxDriver driver;

    @BeforeTest
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

    }

    @Test
    public void testJSAlert(){
        WebElement jsAlertButton = driver.findElement(By.xpath("//*[@id='content']/div/ul/li[1]/button"));
        jsAlertButton.click();

        try {
            Alert alert = driver.switchTo().alert();
            String textOnAlert = alert.getText();
            alert.accept();
            assertEquals("I am a JS Alert", textOnAlert);
        } catch (NoAlertPresentException e){
            e.printStackTrace();
        }
    }

    @Test
    public void ConfirmAccess(){
        WebElement ConfirmButton = driver.findElement(By.xpath("//*[@class='example']/ul/li[2]/button"));
        ConfirmButton.click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            assertEquals("I am a JS Confirm", alert.getText());
            alert.accept();
        } catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testPrompt(){
        WebElement button = driver.findElement(By.xpath("//*[@id='content']/div/ul/li[3]/button"));
        button.click();

        try {
            Alert alert = driver.switchTo().alert();
            //alert.until(ExpectedConditions.alertIsPresent());
            alert.sendKeys("I am a super hero");
            alert.accept();

            WebElement message = driver.findElement(By.cssSelector("#result"));
            assertEquals("You entered: I am a super hero", message.getText());

        }catch(NoAlertPresentException e){
            e.printStackTrace();
        }
    }

}
