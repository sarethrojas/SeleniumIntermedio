package org.titanium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage {
    WebDriver driver;

    By txtFirstNAme = By.name("firstname");
    By ddlCountry = By.name("country");
    By txtUserName = By.id("email");
    By txtPassword = By.name("password");
    By txtConfrimPass = By.name("confirmPassword");
    By btnSubmit = By.name("register");

    public RegisterPage(WebDriver driver){
        this.driver = driver;
    }

    public void setTxtFirstNAme(String firstNAme){
        driver.findElement(txtFirstNAme).sendKeys(firstNAme);
    }

    public void selectcountry(String country){
        new Select(driver.findElement(ddlCountry)).selectByVisibleText(country);
    }

    public void setUserName(String userName){
        driver.findElement(txtUserName).sendKeys(userName);
    }

    public void setPassword(String password){
        driver.findElement(txtPassword).sendKeys(password);
    }

    public void setConfirmPassword(String confirmPass){
        driver.findElement(txtConfrimPass).sendKeys(confirmPass);
    }

    public void clickOnSubmit(){
        driver.findElement(btnSubmit).click();
    }


}
