package com.learning.tests;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import com.learning.library.BasePage;
import com.learning.pages.CategoryScienceCoursePage;
import com.learning.pages.CheckoutPage;
import com.learning.pages.ChooseCourseFormatPage;
import com.learning.pages.HomePage;
import com.learning.pages.YourShoppingCartPage;

public class RegressionTests extends BasePage{

	final static Logger logger = Logger.getLogger(RegressionTests.class);
	
	HomePage hpPage = new HomePage();
	CategoryScienceCoursePage cscPage = new CategoryScienceCoursePage();
	ChooseCourseFormatPage ccfPage = new ChooseCourseFormatPage();
	YourShoppingCartPage yscPage = new YourShoppingCartPage();
	CheckoutPage coPage = new CheckoutPage();
	
	
	@Ignore
	@Test
	public void loginTest()
	{
		hpPage.goto_theGreatCourseWebsite();
		logger.info("browser opens 'The Great Course' website!");
	}
	
	//@Ignore
	@Test
	public void buyAcourseTest()
	{
		hpPage.goto_theGreatCourseWebsite();
		ccfPage.verifyConfirmationPopup2();
		hpPage.click_CategoryScience();
		cscPage.select_OurNightSky();
		ccfPage.clickVideoDownload();
		ccfPage.clickAddToCartButton();
		ccfPage.verifyConfirmationPopup();
		ccfPage.clickCheckOutNowButton();
		yscPage.clickCheckOutNowButton();
		coPage.completeCheckoutMethod();
		coPage.completeBillingAddress();
		coPage.completePaymentInfo();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
