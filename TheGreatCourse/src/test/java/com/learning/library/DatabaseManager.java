package com.learning.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {
	
	private String databaseServerName = "localhost";
	private String databasePort = "1521";
	private String databaseName = "xe";
	private String userName = "hr";
	private String userPassword = "hr";
	
	private String connectionURL = "jdbc:oracle:thin:hr@//" + databaseServerName 
			+ ":" + databasePort + "/" + databaseName;
	
	private ResultSet resultSet = null;
	private Statement statement = null;
	private Connection connection = null;
	
	private void connectToOracleDB() throws Exception
	{
		Class.forName("oracle.jdbc.OracleDriver");
		connection = DriverManager.getConnection(connectionURL, userName, userPassword);
		statement = connection.createStatement();
	}
	
	public ResultSet runSQLQuery(String sqlQuery) throws Exception
	{
		connectToOracleDB();
		resultSet = statement.executeQuery(sqlQuery);
		return resultSet;
	}
	
	
	
	
	
	
	
	
	
	
}
