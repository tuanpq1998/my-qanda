/**
 * @author admin
 * @date 28-10-2019
 */

package com.tuanpq.myaskfm.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHandler {
	final static SimpleDateFormat formatFromSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final static SimpleDateFormat formatToDisplayDate = new SimpleDateFormat("dd-MM-yyyy");
	final static SimpleDateFormat formatToDisplayDateTime = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
	
	public static String convertToDisplayDateTime(String dateFromSQL) {
		Date date = null;
		try {
			date = formatFromSQL.parse(dateFromSQL);
			return formatToDisplayDateTime.format(date);
		} catch (Exception e) {
		}
		return dateFromSQL;
	}
	
	public static String convertToDisplayDate(String dateFromSQL) {
		Date date = null;
		try {
			date = formatFromSQL.parse(dateFromSQL);
			return formatToDisplayDate.format(date);
		} catch (Exception e) {
		}
		return dateFromSQL;
	}
	
	public static String convertToSaveToSQL(Date date) {
		return formatFromSQL.format(date);
	}
}
