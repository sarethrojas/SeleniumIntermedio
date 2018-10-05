package org.titanium.intermedio;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JavaScriptExample {
    String baseUrl = "http://newtours.demoaut.com/index.php";
    WebDriver driver;
    String expectedResult = "";
    String actualResult = "";
    String chromePath = System.getProperty("user.dir") + "/Drivers/chromedriver.exe";
    JavascriptExecutor js;
    String pageLoadStatus = "";

    private boolean highLight(WebElement element){
        js = (JavascriptExecutor) driver;
        for(int iCnt = 0; iCnt < 3; iCnt ++){
            try{
                js.executeScript("arguments[0].setAttribute('style', 'background:red')", element);
                Thread.sleep(1000);
                js.executeScript("arguments[0].setAttribute('style', 'background:')", element);
            }catch (JavascriptException jse){
                System.err.println("class: JavaScriptExample | Method: highLight Exception | Desc: " + jse.getMessage());
                return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean scrollWindow(){
        try {
            js = (JavascriptExecutor) driver;
            //scroll up(0,-250)/down(0,250)
            js.executeScript("window.scrollBy(0,250)");
        }catch (JavascriptException jse){
            System.err.println("class: JavaScriptExample | Method: scrollWindow | Desc: " + jse.getMessage());
            return false;
        }
        return true;
    }

    private boolean waitForPageToLoad(){
        try {
            do {
                js = (JavascriptExecutor)driver;
                pageLoadStatus = (String) js.executeScript("return document.readyState");

            }while (!pageLoadStatus.equals("complete"));
        }catch (JavascriptException jse){
            System.err.println("class: JavaScriptExample | Method: scrollWindow | Desc: " + jse.getMessage());
            return false;
        }
        return true;
    }

    @BeforeTest
    public void launchBrowser(){
        System.setProperty("webdriver.chrome.driver", chromePath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        waitForPageToLoad();
    }

    @AfterTest
    public void closeBrowser(){

        driver.quit();
    }

    @Test(priority = 0)
    public void goToRegisterPage(){
        WebElement lnkRegister = driver.findElement(By.linkText("REGISTER"));
        Assert.assertTrue(highLight(lnkRegister));
        js.executeScript("arguments[0].click();",lnkRegister);
        expectedResult = "Register: Mercury Tours";
        actualResult = driver.getTitle();
        Assert.assertEquals(actualResult, expectedResult);
        Assert.assertTrue(scrollWindow());
    }

    @Test(priority = 1)
    public void registerAnUser(){
        WebElement txtFirstName = driver.findElement(By.name("firstName"));
        highLight(txtFirstName);
        txtFirstName.sendKeys("Sareth R");

        WebElement ddlCountry = driver.findElement(By.name("country"));
        highLight(ddlCountry);
        new Select(ddlCountry).selectByVisibleText("COMOROS");

        highLight(driver.findElement(By.id("email")));
        driver.findElement(By.id("email")).sendKeys("sarethitha@gmail.com");

        highLight(driver.findElement(By.name("password")));
        driver.findElement(By.name("password")).sendKeys("123");

        WebElement txtConfirmPass = driver.findElement(By.name("confirmPassword"));
        highLight(txtConfirmPass);
        txtConfirmPass.sendKeys("123");
        txtConfirmPass.submit();

        Assert.assertTrue(waitForPageToLoad());

        highLight(driver.findElement(By.xpath("//*[contains(text(),'Note:')]")));

    }
}
