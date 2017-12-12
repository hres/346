package hc.fcdr.rws.db;

import java.io.*;
import java.sql.DriverManager;
import java.sql.Connection;




import java.sql.*;

public class Connect {
static final String URL = "jdbc:postgresql://localhost:5432/basedebonnee";
static final String USER = "postgres";
static final String PASSWORD= "romario";
	
	
	public static Connection getConnections() throws SQLException, Exception, IOException {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		//Properties props   = new Properties();

		if(connection != null){
			System.out.println("Connection has been established");
			return connection;
		}else{
			return null;
		}

		

	}
}