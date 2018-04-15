package com.learning.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.learning.library.BasePage;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

public class ChooseCourseFormatPage extends BasePage{

	public ChooseCourseFormatPage clickVideoDownload()
	{
		WebElement radioButtonsElem = myLib.fluentWait(By.id("media-format-radio"));
		List<WebElement> optionsElem = radioButtonsElem.findElements(By.tagName("input"));
		myLib.clickOnHiddenElement(optionsElem.get(0));		
		return this;		
	}
	
	public ChooseCourseFormatPage clickAddToCartButton()
	{
		myLib.clickElement(By.id("add-to-cart-btn"));
		return this;
	}
	
	public ChooseCourseFormatPage verifyConfirmationPopup()
	{
		WebElement msgViewElem = myLib.fluentWait(By.id("messages_product_view"));
		String popupText = msgViewElem.findElement(By.tagName("span")).getText();	
		System.out.println("Confirmation popup text: '" +popupText+"'");
		assertThat("The confirmation popup failed",popupText,containsString("was added to your Shopping Cart."));		
		return this;
	}
	
	public ChooseCourseFormatPage verifyConfirmationPopup2()
	{
		WebElement msgViewElem = myLib.fluentWait(By.id("raleigh-optin"));
		String popupText = msgViewElem.findElement(By.id("raleigh-element-title-content")).getText();
		System.out.println("Confirmation popup text: '" +popupText+"'");
		//assertThat("The confirmation popup failed",popupText,containsString("was added to your Shopping Cart."));		
		return this;
	}
	
	public ChooseCourseFormatPage clickCheckOutNowButton()
	{
		myLib.clickElement(By.id("checkout-now"));
		return this;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
