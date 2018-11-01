package com.xai.tt.business.biz.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by linkun on 2017/10/21.
 */
public class DateUtils {

	public static final String YYYY_DD_MM_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";

	public static final DateTimeFormatter formater = DateTimeFormatter.ofPattern(YYYY_DD_MM_hh_mm_ss);

	public static String format(Date date, String format) {
		if (date == null || StringUtils.isEmpty(format)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String str = sdf.format(date);
		return str;
	}

	public static Date format(String date, String format) {
		if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format)) {
			return null;
		}
		LocalDateTime formatter = LocalDateTime.parse(date, formater);
		return Date.from(getInstant(formatter));
	}

	public static Date addDays(Date date, int amount) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.DAY_OF_YEAR, amount);
		Date dt1 = rightNow.getTime();
		return dt1;
	}

	private static Instant getInstant(LocalDateTime formatter) {
		ZoneId zone = ZoneId.systemDefault();
		return formatter.atZone(zone).toInstant();
	}

	public static Date stringToDate(String str, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// date = java.sql.Date.valueOf(str);

		return date;
	}

	public static Date getMonFristDay(Date date) {
		// 获得第一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR));
		return calendar.getTime();
	}

	public static Date getMonEndDay(Date date) {
		// 获得最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
		return calendar.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 */
	public static int daysBetween(Date smdate, Date bdate) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期之间相差的天数，使用Math.ceil方法保留小数点后1位
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static double daysBetween2(Date date1, Date date2) {
		return Math.ceil(1.0 * (date2.getTime() - date1.getTime()) / (1000 * 3600 * 24) * 10) * 0.1;
	}
}
