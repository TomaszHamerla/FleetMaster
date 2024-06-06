//package com.example.selenium;
//
//import org.junit.After;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class AuthTests {
//
//    private WebDriver driver;
//
//    @BeforeEach
//    public void setUp() {
//        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\chromedriver-win64\\chromedriver.exe");
//        driver = new ChromeDriver();
//        driver.get("http://localhost:5173/");
//    }
//
//    @Test
//    public void testRegister() {
//        WebElement signUpButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/form/div[3]/button[2]"));
//        signUpButton.click();
//
//        WebElement usernameInput = driver.findElement(By.id("username"));
//        WebElement emailInput = driver.findElement(By.id("email"));
//        WebElement passwordInput = driver.findElement(By.id("password"));
//        WebElement registerButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/form/div[4]/button[2]"));
//
//        usernameInput.sendKeys("newuser");
//        emailInput.sendKeys("newuser@example.com");
//        passwordInput.sendKeys("newpassword");
//        registerButton.click();
//
//        assertEquals("newuser", usernameInput.getAttribute("value"));
//        assertEquals("newuser@example.com", emailInput.getAttribute("value"));
//        assertEquals("newpassword", passwordInput.getAttribute("value"));
//    }
//
//    @Test
//    public void testLogin() {
//        WebElement usernameInput = driver.findElement(By.id("username"));
//        WebElement passwordInput = driver.findElement(By.id("password"));
//        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/form/div[3]/button[1]"));
//
//        usernameInput.sendKeys("Doe");
//        passwordInput.sendKeys("456");
//        loginButton.click();
//
//        assertEquals("Doe", usernameInput.getAttribute("value"));
//        assertEquals("456", passwordInput.getAttribute("value"));
//    }
//
//    @After
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}
