/**
 * 
 */
package com.javaweb.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HungDinh
 *
 */
public class DateFormatterUtils {

	public static Date getFormatDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			try {
				Date d = sdf.parse(date);
				System.out.println(date+ " parse : " + d);
				return d;
			} catch (ParseException e) {
				System.out.println("ParseException: " + date);
			}
		}
		return null;
	}

}
