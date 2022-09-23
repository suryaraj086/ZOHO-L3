package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.CustomException;

public enum ConnectionUtility {

	CONNECTION;

	private final static String DBURL = "jdbc:mysql://localhost:3306/tripmanagment";

	private final static String USER = "";

	private final static String PASSWORD = "";

	private final static String loadDriver = "com.mysql.cj.jdbc.Driver";

	private static Connection connect = null;

	public Connection getConnection() throws Exception {

		try {

			if (connect == null) {

				Class.forName(loadDriver);

				connect = DriverManager.getConnection(DBURL, USER, PASSWORD);

				return connect;
			} else {

				return connect;
			}
		} catch (SQLException e) {

			throw new CustomException("Connection Error");
		}

	}

	public void closeConnection() throws CustomException,Exception {
		if (connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
				throw new CustomException("Connection error");
			}
		}
	}
}
