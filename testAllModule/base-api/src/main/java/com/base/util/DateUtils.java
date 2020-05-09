package com.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
	
	public static final String DEFUALT_FORMAT = "yyyy-MM-dd HH:mm:ss"; 
	
	public static final SimpleDateFormat DEFUALT_DATE_FORMAT = new SimpleDateFormat(DEFUALT_FORMAT);

	/**  
     * @return 返回指定月份的最后一天的59分59秒  
     */  
    public static Date getLastMonthOfDate(Date date) {  
        Calendar para = Calendar.getInstance(java.util.Locale.CHINA);  
        para.setTime(date);  
        para.set(Calendar.DATE, para.getActualMaximum(Calendar.DAY_OF_MONTH));  
        para.set(Calendar.HOUR_OF_DAY, 23);  
        para.set(Calendar.MINUTE, 59);  
        para.set(Calendar.SECOND, 59);  
        return para.getTime();  
    }  
    
	/**  
     * @return 返回指定日期的23点59分59秒  
     */  
    public static Date getCurDate(Date date) {  
        Calendar para = Calendar.getInstance(java.util.Locale.CHINA);  
        para.setTime(date);  
      /*  para.set(Calendar.DATE, para.getActualMaximum(Calendar.DAY_OF_MONTH));  */
        para.set(Calendar.HOUR_OF_DAY, 23);  
        para.set(Calendar.MINUTE, 59);  
        para.set(Calendar.SECOND, 59);  
        return para.getTime();  
    }  
    
    /**  
     * @return 返回指定日期的0点0分0秒  
     */  
    public static Date getCurBeginDate(Date date) {  
    	Calendar para = Calendar.getInstance(java.util.Locale.CHINA);  
    	para.setTime(date);  
    	/*  para.set(Calendar.DATE, para.getActualMaximum(Calendar.DAY_OF_MONTH));  */
    	para.set(Calendar.HOUR_OF_DAY, 0);  
    	para.set(Calendar.MINUTE, 0);  
    	para.set(Calendar.SECOND, 0);  
    	return para.getTime();  
    }  
    
    /**
	 * @return 返回指定月份的第一天的0分0秒
	 */
	public static Date getFirstMonthOfDate(Date date) {
		Calendar para = Calendar.getInstance(java.util.Locale.CHINA);
		para.setTime(date);
		para.set(Calendar.DATE, para.getActualMinimum(Calendar.DAY_OF_MONTH));
		para.set(Calendar.HOUR_OF_DAY, 0);
		para.set(Calendar.MINUTE, 0);
		para.set(Calendar.SECOND, 0);
		return para.getTime();
	}
	
	/**
	 * 将月份填充，1月填充为01
	 * @param month
	 * @return
	 */
	public static String fillMonthStr(int month) {
		if(month >= 10) {
			return String.valueOf(month);
		}
		return "0"+month;
	}

	/**
	 * 计算开始年月到结束年月之间的月份
	 * @param startYearMonth
	 * 开始年月，格式：yyyy-MM
	 * @param endYearMonth
	 * 结束年月，格式：yyyy-MM
	 * @return
	 * 返回月份列表，
	 * 如果出现异常挥着开始年月大于结束年月，返回null
	 */
	public static List<String> getYearMonthList(String startYearMonth, String endYearMonth) {
		int startYear;
		int startMonth;
		int endYear;
		int endMonth;
		try {
			startYear = Integer.parseInt(startYearMonth.substring(0, 4));
			startMonth = Integer.parseInt(startYearMonth.substring(4, 6));
			endYear = Integer.parseInt(endYearMonth.substring(0, 4));
			endMonth = Integer.parseInt(endYearMonth.substring(4, 6));
		} catch (Exception e) {
			return null;
		}
		if((startYear*100+startMonth) > (endYear*100+endMonth)) {
			return null;
		}
		List<String> yearMonthList = new ArrayList<String>();
		while((startYear * 100+startMonth) <= (endYear * 100+endMonth)) {
			yearMonthList.add(String.valueOf(startYear) + fillMonthStr(startMonth));
			if(startMonth == 12) {
				startMonth = 1;
				startYear += 1;
			} else {
				startMonth++;
			}
		}
		return yearMonthList;
	}
	
	/**
	 * 将字符串转换为日期
	 * 转换格式：yyyy-MM-dd hh:mm:ss
	 * @param dateStr 
	 * 日期字符串
	 * @return
	 * 日期
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr) throws ParseException{
		return DEFUALT_DATE_FORMAT.parse(dateStr);
	}
	
	/**
	 * 将字符串转换为日期
	 * @param dateStr
	 * 日期字符串
	 * @param format
	 * 转换格式
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr, String format) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(dateStr);
	}
	
	/**
	 * 将日期转换为字符串
	 * 转换格式：yyyy-MM-dd hh:mm:ss
	 * @param date
	 * 日期
	 * @return
	 * 日期字符串
	 * @throws ParseException
	 */
	public static String parseDateStr(Date date) throws ParseException{
		return DEFUALT_DATE_FORMAT.format(date);
	}
	
	/**
	 * 将日期转换为字符串
	 * @param date
	 * 日期
	 * @param format
	 * 转换格式
	 * @return
	 * 日期字符串
	 * @throws ParseException
	 */
	public static String parseDateStr(Date date, String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	/**
	 * 获取上周周一 日期
	 * @param date
	 * @return
	 */
	public static String getLastWeekMonday(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = new GregorianCalendar();
		 c.setTime(date);		     
	     c.setFirstDayOfWeek(Calendar.MONDAY);
	     c.set(Calendar.WEEK_OF_MONTH, c.get(Calendar.WEEK_OF_MONTH) - 1);
	     c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	     
	     String imptimeBegin = sdf.format(c.getTime()); 
	     return imptimeBegin;	     
	}
	
	/**
	 * 获取上周周日 日期
	 * @param date
	 * @return
	 */
	public static String getLastWeekSunday(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = new GregorianCalendar();
		 c.setTime(date);		     
	     c.setFirstDayOfWeek(Calendar.MONDAY);
	     c.set(Calendar.WEEK_OF_MONTH, c.get(Calendar.WEEK_OF_MONTH) - 1);
	     c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	     
	     String imptimeBegin = sdf.format(c.getTime()); 
	     return imptimeBegin;
	}
	
	/**
     * 把long 转换成 日期 再转换成String类型
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
    
    /**
     * 获取当前系统时间，按格式输出
     * @param dateFormat  输出格式(如：yyyy-MM-dd HH:mm:ss)
     * @return 格式化后的系统日期字符串
     */
    public static String getSystemFormatDate(String dateFormat) {
    	return transferLongToDate(dateFormat, System.currentTimeMillis());
    }
    
    
    /** 
           * 时间戳转换成日期格式字符串 
           * @param seconds 精确到秒的字符串 
           * @param formatStr 
           * @return 
         */  
         public static String timeStamp2Date(String seconds,String format) {  
             if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
                return "";  
             }  
             if(format == null || format.isEmpty()){
                 format = "yyyy-MM-dd HH:mm:ss";
             }   
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
             return sdf.format(new Date(Long.valueOf(seconds)));  
         }  
   
}
