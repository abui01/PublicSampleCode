package com.learning.pages;

import com.learning.library.BasePage;
import static org.junit.Assert.*;

import org.openqa.selenium.By;

public class HomePage extends BasePage{
	private String homepageURL = "http://www.thegreatcourses.com/";
	
	
	
	public HomePage goto_theGreatCourseWebsite()
	{
		driver.get(homepageURL);
		System.out.println("Page Title: '" + driver.getTitle() + "'");	
		assertEquals("Online Courses & Lectures for Home Study and Lifelong Learning", driver.getTitle());
		return this;
	}
	
	public HomePage click_CategoryScience()
	{
		myLib.clickElement(By.linkText("Science"));		
		return this;		
	}
	
	
	
}
