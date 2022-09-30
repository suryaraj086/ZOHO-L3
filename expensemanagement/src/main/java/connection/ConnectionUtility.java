package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.CustomException;

public enum ConnectionUtility {

	CONNECTION;

	private final static String DBURL = "jdbc:mysql://localhost:3306/tripmanagement";

	private final static String USER = "finley";

	private final static String PASSWORD = "password";

	private final static String loadDriver = "com.mysql.cj.jdbc.Driver";

	private static Connection connect = null;

	public Connection getConnection() throws CustomException {

		try {

			if (connect == null) {

				try {
					Class.forName(loadDriver);
				}

				catch (ClassNotFoundException e) {

					throw new CustomException("sql class not found");
				}

				connect = DriverManager.getConnection(DBURL, USER, PASSWORD);

				return connect;
			}

			return connect;

		} catch (SQLException e) {

			throw new CustomException("Connection Error");
		}

	}

	public void closeConnection() throws CustomException, Exception {
		if (connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
				throw new CustomException("Connection error");
			}
		}
	}
}
