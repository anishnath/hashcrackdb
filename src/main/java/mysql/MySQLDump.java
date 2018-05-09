package mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MySQLDump {

	static final String DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/bruteforce";
	static final String DB_USER = "root";
	static final String DB_PASSWORD = "root";
	Connection connection;

	public MySQLDump() {
		connect();
	}

	public void connect() {
		try {
			Class.forName(DRIVER);
			connection = (Connection) DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			connection.setAutoCommit(true);
			
		} catch (Exception e) {
			System.out.println("ERROR connecting to database!");
			System.out.println(e.toString());
		}
	}

	public ResultSet select(String query) {
		try {
			Statement statement = (Statement) connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			return result;
		} catch (SQLException e) {
			System.out.println("ERROR while executing select query!");
			System.out.println(e.toString());
			return null;
		}
	}

	public int update(String query) {
		try {
			Statement statement = (Statement) connection.createStatement();
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("ERROR while executing update query");
			System.out.println(e.toString());
			return -1;
		}
	}

	public int deleteorInsert(String query) {
		try {
			Statement statement = (Statement) connection.createStatement();
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("ERROR while deleting line!");
			System.out.println(e.toString());
			return -1;
		}
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("ERROR while closing connections!");
			System.out.println(e.toString());
		}
	}

}
