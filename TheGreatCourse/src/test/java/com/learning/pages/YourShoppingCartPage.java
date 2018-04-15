package com.learning.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.learning.library.BasePage;
import static org.junit.Assert.*;

public class YourShoppingCartPage extends BasePage{

	public YourShoppingCartPage clickCheckOutNowButton()
	{
		waitForPageLoad();
		WebElement buttonHeaderElem = driver.findElement(By.className("cart_checkout_header"));
		buttonHeaderElem.findElement(By.tagName("button")).click();
		return this;
	}
	
	
	
	
	////////////////////// Helper Methods //////////////////////////////
	
	private void waitForPageLoad()
	{
		WebElement pageHeaderElem = myLib.fluentWait(By.tagName("h1"));
		System.out.println("text: " + pageHeaderElem.getText());
		System.out.println("Fixed the Test");
		System.out.println("Fixed the Test again!");
		System.out.println("New Page Features Dev1");
		assertEquals("Your Shopping Cart", pageHeaderElem.getText());		
	}
	
	
}
