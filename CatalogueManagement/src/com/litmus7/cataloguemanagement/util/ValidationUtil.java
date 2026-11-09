package com.litmus7.cataloguemanagement.util;

public class ValidationUtil {

	public static boolean isInt(int number) {

		boolean isInt = false;
		
		if (number >= 0) {
			isInt = true;
		}
		return isInt;
	}
}
