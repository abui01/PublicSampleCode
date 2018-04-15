package com.learning.tests;

import java.sql.ResultSet;
import org.junit.Test;
import com.learning.library.DatabaseManager;

public class DatabaseTesting {
	
	@Test
	public void testingDatabaseConnection() throws Exception
	{
		DatabaseManager dbManager = new DatabaseManager();
		ResultSet data = dbManager.runSQLQuery("select * from COUNTRIES");
		
		while(data.next())
		{
			String countryID = data.getString("COUNTRY_ID");
			String countryName = data.getString("COUNTRY_NAME");
			int regionID = data.getInt("REGION_ID");
			
			System.out.println(countryID+ "\t" + countryName +"\t \t \t \t" + regionID);
		}
	}
}
