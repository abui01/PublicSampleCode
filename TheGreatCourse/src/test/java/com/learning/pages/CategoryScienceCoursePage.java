package com.learning.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.learning.library.BasePage;

public class CategoryScienceCoursePage extends BasePage {

	public CategoryScienceCoursePage select_OurNightSky()
	{
		myLib.fluentWait(By.className("category-products"));
		selectCourse("Our Night Sky");
		return this;
	}
	
	
	//////////////// Helper Methods //////////////////////////////
	private void selectCourse(String courseTitle)
	{
		WebElement allProductElem = driver.findElement(By.className("category-products"));
		List<WebElement> courseElems = allProductElem.findElements(By.cssSelector(".product-name"));
		for(WebElement temp: courseElems)
		{
			if (temp.getText().contains(courseTitle))
			{
				temp.click();
				break;				
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
