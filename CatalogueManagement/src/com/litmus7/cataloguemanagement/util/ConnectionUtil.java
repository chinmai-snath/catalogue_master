package com.litmus7.cataloguemanagement.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.cataloguemanagement.constants.MessageConstants;
import com.litmus7.cataloguemanagement.constants.SqlConstants;
import com.litmus7.cataloguemanagement.exceptions.FileReadException;

public class ConnectionUtil {
	private static Properties properties = new Properties();
	private static String url;
	private static String user;
	private static String pass;
	private static Logger logger = LogManager.getLogger(ConnectionUtil.class);

	public static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(url, user, pass);
		logger.debug(MessageConstants.CONNECTION_MESSAGE);
		return connection;
	}

	static {
		try {
			InputStream stream = ConnectionUtil.class.getClassLoader().getResourceAsStream(SqlConstants.DB_CONFIG_FILE);
			properties.load(stream);
			url = properties.getProperty(SqlConstants.DB_URL);
			user = properties.getProperty(SqlConstants.DB_USER);
			pass = properties.getProperty(SqlConstants.DB_PASSWORD);
			logger.debug("Property file read done . . .");
		} catch (IOException e) {

			throw new FileReadException("File read error", e);
		}
	}
}