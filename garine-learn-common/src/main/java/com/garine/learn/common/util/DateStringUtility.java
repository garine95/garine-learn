package com.garine.learn.common.util;

import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 时间格式化及计算
 * 
 * @author wangyp<wangyp@4px.com>
 * @version 1.0 2014-06-24 上午10:58:02
 */
public class DateStringUtility {

	public static final String YYYY_MM_DD_HH_MM_SS_MS = "yyyy-MM-dd HH:mm:ss:ms";
	public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDDHHMMSSMS = "yyyyMMddHHmmssms";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYY = "yyyy";
	public static final String YYYY_MM_DD2 = "yyyy年MM月dd日";
	public static final String YYYY_MM = "yyyy-MM";
	/**
	 * UTC时间的ISO日期格式;
	 */
	public static final String YYYY_MM_DDTHH_mm_ss_sssZ = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";
	
	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToString(Date dateDate, String formatString) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatString);
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	
	/**
	 * 时间前推或后推分钟,其中mm表示分钟.
	 * 
	 * @param strDate 日期串
	 * @param mm 分钟
	 * @return
	 */
	public static String getPreTime(String strDate, int mm) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestr = "";
		try {
			Date date= format.parse(strDate);
			long Time = (date.getTime() / 1000) + mm * 60;
			date.setTime(Time * 1000);
			datestr = format.format(date);
		} catch (Exception e) {
		}
		return datestr;
	}
	
	/**
	 * 将字符串时间，格式化成Calendar类型
	 * 
	 * @param timestamp  日期
	 * @param formatString 格式化串
	 * @return
	 * @throws ParseException
	 */
	public static Calendar parseTimestamp(String timestamp, String formatString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formatString, Locale.CHINA);
		Date date = sdf.parse(timestamp);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	/**
	 * 将字符串时间，格式化成字符串类型
	 * 
	 * @param timestamp  时间的毫秒数
	 * @param formatString 格式化串
	 * @return
	 * @throws ParseException
	 */
	public static String parseTimestampFormat(String timestamp, String formatString) {
	    
	    if (StringUtils.isEmpty(timestamp.trim())) {
	        return null;
	    }
	    
	    Date date = new Date();
        date.setTime(Long.parseLong(timestamp));
        return DateStringUtility.dateToString(date, DateStringUtility.YYYY_MM_DD_HH_MM_SS);
	}
	
	/**
	 * 按格式化串比较两个日期,要求格式化串必须按年月日时分秒顺序
	 * 
	 * 格式化后,日期相同返还0,第一个日期小于第二个日期返回-1,第一个日期大于第二个日期返回1
	 * @param cal1
	 * @param cal2
	 * @param formatStr
	 * @return
	 */
	public static int compare(Calendar cal1, Calendar cal2, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String date1 = sdf.format(cal1.getTime());
		String date2 = sdf.format(cal2.getTime());
		return date1.compareTo(date2);
	}

	/**
	 * 获取Calendar前后beforeOrAfterDay天时间
	 * 
	 * @param date
	 * @param beforeOrAfterDay 前后天数用正负表示
	 * @return Calendar
	 */
	public static Calendar getCalendar(Date date, int beforeOrAfterDay) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DATE, beforeOrAfterDay);
		return calendar;
	}
	
	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat 格式yyyy-MM-dd
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {
		// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(dat)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}

	
	/**
	 * 判断是否润年
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isLeapYear(String date) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = stringToDate(date, "yyyy-MM-dd");
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0){
			return true;
		} else if ((year % 4) == 0) {
			if ((year % 100) == 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date stringToDate(String strDate, String formatString) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatString);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	/**
	 * 所需时间的下年,月,日afterOrAgo或上年,月,日beforeOrAfter的时间
	 * 
	 * @param dateStr 时间字符串
	 * @param type 1,年 2,月 3,日
	 * @param beforeOrAfter 下beforeOrAfter年,月,日份数
	 * @param formatString 格式化参数 yyyyMMddHHmmss
	 * @return String
	 * @throws Exception
	 */
	public static String getDateBeforeOrAfter(String dateStr, int type, int beforeOrAfter, String formatString) throws Exception {

		SimpleDateFormat outFormat = new SimpleDateFormat(formatString);
		GregorianCalendar calendar = new GregorianCalendar();
		try {
			Date date = outFormat.parse(dateStr);
			calendar.setTime(date);
			switch (type) {
			case 1:
				calendar.add(GregorianCalendar.YEAR, beforeOrAfter);
				break;
			case 2:
				calendar.add(GregorianCalendar.MONTH, beforeOrAfter);
				break;
			case 3:
				calendar.add(GregorianCalendar.DATE, beforeOrAfter);
				break;
			}
		} catch (ParseException ex) {
			throw new Exception("日期格式化异常！", ex);
		}
		return outFormat.format(calendar.getTime());
	}	
	/**
	 * 获取两个时间差天数
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @throws Exception 
	 */
	public static int getDistanceDays(String startTime,String endTime){
		int iDay = -1;
		if(null == startTime || null == endTime || "".equals(startTime)  || "".equals(endTime) ){
			return iDay;
		}
		try{
			SimpleDateFormat format = null;
			if(startTime.indexOf(':') != -1){
				format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			}else{
				format = new SimpleDateFormat(YYYY_MM_DD);
			}
			Date startDate = format.parse(startTime);
			Date endDate = format.parse(endTime);
			long diff = endDate.getTime() - startDate.getTime(); 
		    long days = diff / (1000 * 60 * 60 * 24); 
		    iDay = Integer.parseInt(String.valueOf(days)); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return iDay;
	}
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentTime(){
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		return format.format(new Date());
	}
	
	/**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
        return format.format(new Date());
    }
    
    /**
     * 控制开始时间和结束时间 控制在多少内
     * （时间格式yyyy-MM-dd HH:mm:ss）
     * 两个时间格式都不满足（或都为空）不会控制
     * 针对字符串的时间
     * @param  startDate 开始时间
     * @param  endDate 结束时间
     * @param  object 实体类
     * @param  day 时间相差多少天
     */
    public static <T> T rangeDate(String startDate, String endDate, T object, int day) {
    	
    	// 获取到开始时间和结束时间对象
    	PropertyDescriptor pd_begin = getPropertyDescriptor(object,startDate);
    	PropertyDescriptor pd_end = getPropertyDescriptor(object,endDate);
    	
    	String date_begin = "";
    	String date_end = "";
    	
    	// 获取到开始时间和结束时间的值
    	try {
    		date_begin = (String) pd_begin.getReadMethod().invoke(object, null);
    		date_end = (String) pd_end.getReadMethod().invoke(object, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
		boolean begin = date_begin != null && !"".equals(date_begin.trim()) && checkTimeFormat(date_begin);
		boolean end = date_end != null && !"".equals(date_end.trim()) && checkTimeFormat(date_end);
		
		if (begin || end) {
			// 开始时间 有， 结束时间没有， 结束时间=当前时间
			if (begin && !end) {
				Date begin_tmp = DateStringUtility.stringToDate(date_begin, DateStringUtility.YYYY_MM_DD);
				date_end =DateStringUtility.dateToString(DateStringUtility.getCalendar(begin_tmp, day).getTime(), DateStringUtility.YYYY_MM_DD_HH_MM_SS);
				try {
					pd_end.getWriteMethod().invoke(object, date_end);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 开始时间没有， 结束时间有， 开始时间=结束时间-30天
			if (!begin && end) {
				Date end_tmp = DateStringUtility.stringToDate(date_end, DateStringUtility.YYYY_MM_DD);
				date_begin =DateStringUtility.dateToString(DateStringUtility.getCalendar(end_tmp, -day).getTime(), DateStringUtility.YYYY_MM_DD_HH_MM_SS);
				try {
					pd_begin.getWriteMethod().invoke(object, date_begin);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 开始时间 和 结束时间都有，相差30天以上， 结束时间=开始时间+30天
			if (begin && end && DateStringUtility.getDistanceDays(date_begin, date_end) > day) {
				//  时间差 > 30 天
				Date begin_tmp = DateStringUtility.stringToDate(date_begin, DateStringUtility.YYYY_MM_DD);
				date_end =DateStringUtility.dateToString(DateStringUtility.getCalendar(begin_tmp, day).getTime(), DateStringUtility.YYYY_MM_DD_HH_MM_SS);
				try {
					pd_end.getWriteMethod().invoke(object, date_end);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	
    	return null;
    }
	
    public static PropertyDescriptor getPropertyDescriptor(Object obj, String filed) {  
    	PropertyDescriptor pd = null;
    	try {  
    		Class<? extends Object> clazz = obj.getClass();  
    		pd = new PropertyDescriptor(filed, clazz);  
    	} catch (SecurityException e) {  
    		e.printStackTrace();  
    	} catch (IllegalArgumentException e) {  
    		e.printStackTrace();  
    	} catch (IntrospectionException e) {  
    		e.printStackTrace();  
    	}
    	return pd;
    }  
    
    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param datestr
     * @return
     */
    public static int getWeek(String datestr) {
        // 再转换为时间
        Date date = stringToDate(datestr, "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
         int week = c.get(Calendar.DAY_OF_WEEK);
        // week中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推 "EEEE"
//        return new SimpleDateFormat("E",Locale.US).format(c.getTime());
        return week;
    }
    
    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param datestr
	 * @param local
     * @return
     */
    public static String getWeek(String datestr,Locale local) {
        // 再转换为时间
        Date date = stringToDate(datestr, "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
//         int week = c.get(Calendar.DAY_OF_WEEK);
        // week中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推 "EEEE"
        return new SimpleDateFormat("E",local).format(c.getTime());
    }
    
    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param local
     * @return
     */
    public static String getWeek(Locale local) {
        Calendar c = Calendar.getInstance();
//         int week = c.get(Calendar.DAY_OF_WEEK);
        // week中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推 "EEEE"
        return new SimpleDateFormat("E",local).format(c.getTime());
    }
    
    /**
     * 根据当前时间获取UTC时间的ISO标准格式字符串;
     * @param cal 当前时间;
     * @return
     */
    public static String getUTCDateString(Calendar cal){
    	if(null == cal){
    		return "";
    	}
    	//取得时间偏移量
    	int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
    	//取得夏令时差：
    	int dstOffset = cal.get(Calendar.DST_OFFSET);
    	//从本地时间里扣除这些差量，即可以取得UTC时间：
    	cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
    	
    	//格式化日期为ISO格式;
    	SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DDTHH_mm_ss_sssZ);
        return format.format(cal.getTime());
    }
    
    /**
     * 获取当前日期的UTC时间对象Calendar;
	 *
     * @return
     */
    public static Calendar getCurrentUTCCalendar(){
    	//获取UTC时间时间戳;
    	Calendar cal = Calendar.getInstance();
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		int dstOffset = cal.get(Calendar.DST_OFFSET);
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
    	return cal;
    }

	/**
	 * 校验时间正确性格式:年-月-日 时:分:秒
	 * @param value
	 * @return
	 */
	public static boolean checkTimeFormat(String value){
		if(null == value || "" == value){
			return true;
		}
		String dateFormat="^(\\d{4}|\\d{2})-((1[0-2])|(0?[1-9]))-(([12][0-9])|(3[01])|(0?[1-9]))(\\s((1|0?)[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))?$";
		Pattern patForamt = Pattern.compile(dateFormat);
		Matcher matcher = patForamt.matcher(value);
		return  matcher.matches();
	}
	
	/**
	 * 获取当前时间的时区(+0800)
	 * @return
	 */
	public static String getCurrentTimeZone(){
	    Date objDate = new Date();
	    return dateToString(objDate, "Z");
	}

	/**
	 *
	 * @param timeZone 以GMT+00:00 这种标准格式 北京时间是 GMT+08:00
	 * @param time 当前时间 HH:mm:ss
	 * @return
	 */
	public static long getTimeStampByTimeZoneAndTime(String timeZone , String time){
		SimpleDateFormat dff = new SimpleDateFormat(DateStringUtility.YYYY_MM_DD_HH_MM_SS);
		dff.setTimeZone(TimeZone.getTimeZone(timeZone));
		String dateStr = getCurrentDayByZone(timeZone) + " " + time;
		Date date = null;
		try {
			date = dff.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	public static String getCurrentDayByZone(String timeZone){
		SimpleDateFormat dff = new SimpleDateFormat(DateStringUtility.YYYY_MM_DD);
		dff.setTimeZone(TimeZone.getTimeZone(timeZone));
		return dff.format(new Date());
	}
}
