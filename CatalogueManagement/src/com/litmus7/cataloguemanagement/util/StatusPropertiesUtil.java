package com.litmus7.cataloguemanagement.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.litmus7.cataloguemanagement.exceptions.FileReadException;

public class StatusPropertiesUtil {
	private static Properties properties = new Properties();
	
	public String getProperty(String constant) {
		return properties.getProperty(constant);
	}

	static {
		InputStream stream = ConnectionUtil.class.getClassLoader()
				.getResourceAsStream("errorcodes.properties");

		try {
			properties.load(stream);
		} catch (IOException e) {

			throw new FileReadException("File read error", e);
		}
	}
}
