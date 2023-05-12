package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.exception.CustomException;

public class ConnectionUtility {

	private final  String DBURL = "jdbc:mysql://localhost:3306/tripmanagement";

	private final  String USER = "finley";

	private final  String PASSWORD = "password";

	private final  String loadDriver = "com.mysql.cj.jdbc.Driver";

	private Connection connect = null;

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

	public void closeConnection(Connection con) throws CustomException {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				throw new CustomException("Connection error");
			}
		}
	}
}
