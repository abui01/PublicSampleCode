package com.learning.pages;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.learning.library.BasePage;

public class CheckoutPage extends BasePage{
	
	public CheckoutPage completeCheckoutMethod()
	{
		waitForPageLoad();
		myLib.enterTextField(By.id("login-email"), "frankTest123@gmail.com");
		myLib.clickOnHiddenElement(By.id("no_have_pass"));
		myLib.clickElement(By.id("checkout-sigin"));
		return this;
	}
	
	public CheckoutPage completeBillingAddress()
	{
		WebElement firstName = myLib.fluentWait(By.id("billing:firstname"));
		firstName.sendKeys("FrankFirst");
		
		myLib.enterTextField(By.id("billing:lastname"), "FrankLast");
		myLib.enterTextField(By.id("billing:street1"), "100 Frank st");
		myLib.enterTextField(By.id("billing:city"), "FrankCity");
		myLib.selectDropDown(By.id("billing:country_id"), "United States");
		myLib.selectDropDown(By.id("billing:region_id"), "Virginia");
		myLib.enterTextField(By.id("billing:postcode"), "22033");
		myLib.enterTextField(By.id("billing:customer_password"), "password1");
		myLib.enterTextField(By.id("billing:confirm_password"), "password1");
		WebElement buttonContainer = driver.findElement(By.id("billing-buttons-container"));
		buttonContainer.findElement(By.tagName("button")).click();
		
		return this;
	}
	
	public CheckoutPage completePaymentInfo()
	{
		myLib.fluentWait(By.id("pbridge_iframe"));
		myLib.switchToIframe(By.id("pbridge_iframe"));
		
		myLib.enterTextField(By.id("cc_number_paymentech"), "5529420350615465");		
		myLib.selectDropDown(By.id("cc_expiration_month_paymentech"), "12 - December");
		myLib.selectDropDown(By.id("cc_expiration_year_paymentech"), "2024");
		myLib.clickElement(By.name("form_submit"));		
		
		myLib.switchToDefault();
		return this;
	}
	
	
	
	
	////////////////// Helper Methods///////////////
	private void waitForPageLoad()
	{
		WebElement headerCheckoutElem = myLib.fluentWait(By.tagName("h1"));
		System.out.println("text: " + headerCheckoutElem.getText());
		assertEquals("Checkout", headerCheckoutElem.getText());		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
