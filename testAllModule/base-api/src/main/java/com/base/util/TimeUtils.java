package com.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtils {
	
   private static final Logger logger=LoggerFactory.getLogger(TimeUtils.class);
	
	private static DateFormat format= new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    
    /**
     * 

--当月
SELECT * FROM dual WHERE trunc(sysdate,'mm') <= last_update_date < trunc(sysdate) + 1;
SELECT to_char(sysdate, 'mm') FROM dual;

--当季
SELECT * FROM dual WHERE trunc(sysdate,'q') <= last_update_date < trunc(sysdate) + 1;
SELECT to_char(sysdate, 'q') FROM dual;

--当年
SELECT * FROM dual WHERE trunc(sysdate,'yyyy') <= last_update_date < trunc(sysdate) + 1;
SELECT to_char(sysdate, 'yyyy') FROM dual;

     * 
     * 
     */
	
	/**
     * 获取当天
     */
    public static Date getCurrentDay(){
    	Calendar cal = Calendar.getInstance(); 
    	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 
    			cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
    	return cal.getTime();
    }
    
    /**
     * 获取当月第一天
     */
    public static Date getCurrentMonthFirstDay(){
    	Calendar cal = Calendar.getInstance(); 
    	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
    	return cal.getTime();
    }
    
    /**
     * 获取当季第一天
     */
    public static Date getCurrentQuarterFirstDay(){
    	Calendar cal = Calendar.getInstance(); 
    	int currentMonth = cal.get(Calendar.MONTH) + 1;
    	
    	if (currentMonth >= 1 && currentMonth <= 3)
    		currentMonth = 0;
        else if (currentMonth >= 4 && currentMonth <= 6)
        	currentMonth = 3;
        else if (currentMonth >= 7 && currentMonth <= 9)
        	currentMonth = 6;
        else if (currentMonth >= 10 && currentMonth <= 12)
        	currentMonth = 9;
    	
    	cal.set(cal.get(Calendar.YEAR), currentMonth, 1, 0, 0, 0);
    	return cal.getTime();
    }
    
    /**
     * 获取当年第一天
     */
    public static Date getCurrentYearFirstDay(){
    	Calendar cal = Calendar.getInstance(); 
    	cal.set(cal.get(Calendar.YEAR), 0, 1, 0, 0, 0);
    	return cal.getTime();
    }
    
    public static String  date2String(Date date) {
    	try {
    		String time=format.format(date);
    		return time.substring(time.indexOf("年")+1);
		} catch (Exception e) {
			logger.error("日期转换失败"+e.getMessage());
		}
    	return "xx月xx日";
    }
	   
}
