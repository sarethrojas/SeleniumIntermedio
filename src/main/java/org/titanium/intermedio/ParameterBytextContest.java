package org.titanium.intermedio;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ParameterBytextContest {

    WebDriver driver;
    String baseUrl = "https://www.google.com/";
    String chromePath = System.getProperty("user.dir") + "/Drivers/chromedriver.exe";
    @BeforeTest(groups = {"A", "B"})
    public void launchBrowser(){
        System.setProperty("webdriver.chrome.driver", chromePath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @AfterTest(groups = {"A"})
    public void closeBrowser(){

        driver.quit();
    }

    @DataProvider(name = "SearchProvider")
    public Object [][] getDataFromDataProvider(ITestContext c) {
        Object [][] groupArray = null;
        for(String group: c.getIncludedGroups()){
            if(group.equals("A")){
                groupArray= new Object[][]{
                        {"Isabel", "Google"},
                        {"Ernesto", "Yahoo"},
                        {"Gabriela", "Amazon"},
                        {"Pedro", "Gmail"}
                };
                break;
            }else if(group.equals("B")){
                groupArray= new Object[][]{
                        {"Mexico"},
                        {"Canada"},
                        {"Rusia"},
                        {"Japon"}
                };
                break;
            }
        }
        return groupArray;

    }

    @Test(dataProvider = "SearchProvider", groups = "A")
    public void testMethodA(String tester, String searchKey) throws InterruptedException {
        WebElement txtSearch = driver.findElement(By.name("q"));
        txtSearch.sendKeys(searchKey);
        System.out.println("Bienvenido -> " + tester + " Tu palabra a buscar es " + searchKey);
        Thread.sleep(2000);

        String testValue = txtSearch.getAttribute("value");
        System.out.println("El valor de prueba es " + testValue + " y es igual a " + searchKey);
        txtSearch.clear();

        Assert.assertTrue(testValue.equals(searchKey));
    }
    @Test(dataProvider = "SearchProvider", groups = "B")
    public void testMethodB(String searchKey) throws InterruptedException {
        WebElement txtSearch = driver.findElement(By.name("q"));
        txtSearch.sendKeys(searchKey);
        Thread.sleep(2000);

        String testValue = txtSearch.getAttribute("value");
        System.out.println("El valor de prueba es " + testValue + " y es igual a " + searchKey);
        txtSearch.clear();

        Assert.assertTrue(testValue.equals(searchKey));
    }
}
