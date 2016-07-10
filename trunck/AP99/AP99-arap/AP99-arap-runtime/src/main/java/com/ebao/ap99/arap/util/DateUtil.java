package com.ebao.ap99.arap.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Date getTimeStamp() throws Exception{
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}
	
	public static String format(Date date, String format) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
}
