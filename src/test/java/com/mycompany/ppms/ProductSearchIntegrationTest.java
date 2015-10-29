package com.mycompany.ppms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ProductSearchIntegrationTest {
	static WebDriver driver;
	
	static String appPath;
	
	@BeforeClass
	public static void setUpOnce() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		appPath = "http://localhost:8080/ppms";
	}
	
	@AfterClass
	public static void tearDownOnce() {
		driver.quit();
	}
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testProductSearchFormDisplayCorrectly() {
		driver.get(appPath + "/product/searchForm");
		WebElement searchForm = driver.findElement(By.id("searchForm"));
		
		assertNotNull(searchForm);
		assertEquals("form", searchForm.getTagName());
		
		WebElement searchInputText = searchForm.findElement(By.id("q"));
		
		assertNotNull(searchInputText);
		assertEquals("input", searchInputText.getTagName());
		assertEquals("text", searchInputText.getAttribute("type"));
		
		WebElement searchInputButton = searchForm.findElement(By.id("searchButton"));
		
		assertNotNull(searchInputButton);
		assertEquals("input", searchInputButton.getTagName());
		assertEquals("button", searchInputButton.getAttribute("type"));
		assertEquals("search", searchInputButton.getAttribute("value"));
		
		WebElement searchResults = driver.findElement(By.id("searchResults"));
		
		assertNotNull(searchResults);
		assertEquals("div", searchResults.getTagName());
		assertEquals("", searchResults.getText());
		
		
	}
	
	@Test
	public void testProductSearchFormSearchAndResultsDisplay() {
		driver.get(appPath + "/product/searchForm");
		
		WebElement searchForm = driver.findElement(By.id("searchForm"));
		WebElement searchInputText = searchForm.findElement(By.id("q"));
		WebElement searchInputButton = searchForm.findElement(By.id("searchButton"));
		
		searchInputText.sendKeys("Shoes");
		searchInputButton.click();
		
		driver.findElement(By.xpath("//div[@id='searchResults']/li[1]"));
		
		WebElement searchResults = driver.findElement(By.id("searchResults"));
		
		assertEquals("Very Nice Shoes", searchResults.findElement(By.xpath("li[1]")).getText());
		assertEquals("Cool Shoes", searchResults.findElement(By.xpath("li[2]")).getText());
	}
}
