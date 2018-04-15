package com.learning.tests;

import org.junit.Test;

import com.learning.library.BasePage;

public class FileUploadTests extends BasePage {
	
	@Test
	public void testingLocalFileUpload()
	{
		driver.get("www.samplewebsite.com");
	}

}
