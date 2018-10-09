package org.titanium.logs;

import  org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LogTest {
    WebDriver driver;
    String baseURL="https://healthunify.com/bmicalculator/";
    Logger log = Logger.getLogger(LogTest.class);

    @BeforeClass
    public void initializeComponent(){
        PropertyConfigurator.configure(System.getProperty("user.dir")+"/resourses/log4j.properties");

    }

    @Test
    public void launchBrowser(){
        try {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get(baseURL);
            log.info("Opening website: " + baseURL);
        }catch(WebDriverException we){
            log.error("Webdriver falló: " + we.getMessage());
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
    }

    @Test(dependsOnMethods = {"BmiCalculator"})
    public void tearDown(){
        driver.quit();
        log.info("Navegador Cerrado!");
    }

    @Test(dependsOnMethods = {"launchBrowser"})
    public void BmiCalculator(){
        try {
            log.info("ingresando peso");
            driver.findElement(By.name("wg")).sendKeys("89");
            log.info("seleccionando kg");
            new Select(driver.findElement(By.name("opt1"))).selectByVisibleText("kilograms");
            log.info("seleccionando altura");
            new Select(driver.findElement(By.name("opt2"))).selectByValue("5");
            log.info("seleccionando pies");
            new Select(driver.findElement(By.name("opt3"))).selectByValue("10");
            log.info("Click en el boton calcular");
            driver.findElement(By.name("cc")).click();

            String siunits = driver.findElement(By.name("si")).getAttribute("value");
            String usunits = driver.findElement(By.name("us")).getAttribute("value");
            String ukunits = driver.findElement(By.name("uk")).getAttribute("value");
            String note = driver.findElement(By.name("desc")).getAttribute("value");
            log.info("SI unit = " + siunits);
            log.info("US unit = " + usunits);
            log.info("UK unit = " + ukunits);
            log.info("Descripción = " + note);

        }catch (NoSuchElementException ne){
            log.error("No se encontró el elemento web: " + ne.getMessage());
        }catch(WebDriverException we){
            log.error("Webdriver falló: " + we.getMessage());
        }catch (Exception ex){
            log.error(ex.getMessage());
        }

    }
}
