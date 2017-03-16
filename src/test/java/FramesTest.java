import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;


public class FramesTest {
    FirefoxDriver driver;

    @BeforeTest
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://the-internet.herokuapp.com/iframe");
    }

    @Test
    public void testFrames() {
        driver.switchTo().frame("mce_0_ifr");

        WebElement mce = driver.findElement(By.tagName("p"));
        assertEquals("Your content goes here.", mce.getText());

        WebElement textField = driver.findElement(By.cssSelector("#tinymce"));
        textField.click();
        textField.clear();
        textField.sendKeys("Hello, ");
        driver.switchTo().defaultContent();


        WebElement boldText = driver.findElement(By.cssSelector(".mce-i-bold"));
        boldText.click();


        driver.switchTo().frame("mce_0_ifr");
        textField.click();
        textField.sendKeys("world!");

        WebElement mceAgain = driver.findElement(By.tagName("p"));
        assertEquals(mceAgain.getText(), "Hello, \uFEFFworld!");

    }

    @AfterTest
    public void tearDown() {

        driver.quit();
    }

}

