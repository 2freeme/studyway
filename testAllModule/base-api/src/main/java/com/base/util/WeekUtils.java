package com.base.util;

import org.apache.commons.lang.time.FastDateFormat;

import java.util.Calendar;


public class WeekUtils {
    
    private FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd");
    
    // testcase
    public static void main(String[]  xx){
        WeekUtils wu = new WeekUtils();
        
        // 该月份最大周数
        System.out.println(wu.getNumWeekOfMonth(2017, 2));
        
        // 该周的周一指周七
        System.out.println(wu.getStartDayOfWeek(2016, 2, 5));
        System.out.println(wu.getEndDayOfWeek(2016, 2, 5));
        
        // 该周的周一指周七
        System.out.println(wu.getStartDayOfWeek(2016, 3, 1));
        System.out.println(wu.getEndDayOfWeek(2016, 3, 1));
        
        // 该月的第一天至最后一天
        System.out.println(wu.getFirstDayOfMonth(2017, 2));
        System.out.println(wu.getLastDayOfMonth(2017, 2));
    }
    
    /**
     * 获取该月份最大周数
     */
    public int getNumWeekOfMonth(int year,int month) {
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  //周一为开始第一天      
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH , month - 1); 
        return cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }
    
    /**
     * 获取该月的第一天
     */ 
    public String getFirstDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  //周一为开始第一天      
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH , month - 1); 
        cal.set(Calendar.DATE , 1);        
        return df.format(cal);
    }
      
    /**
     * 获取该月的最后一天
     */ 
    public String getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR , year);
        cal.set(Calendar.MONTH , month - 1);
        cal.set(Calendar.DATE , 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_YEAR , -1);
        return df.format(cal);
    }  
      
      
    /**
     * 获取该周的周一日期
     */
    public String getStartDayOfWeek(int year,int month,int weekNo){
        int maxWeekNo = getNumWeekOfMonth(year,month);
        if (weekNo > maxWeekNo || weekNo < 1) {
            throw new IllegalArgumentException("输入周数不合法(1-" + maxWeekNo + ")");
        }
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  //周一为开始第一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH , month - 1);
        cal.set(Calendar.WEEK_OF_MONTH, weekNo);
        return df.format(cal);
    }  
      
    /**
     * 获取该周的周七日期
     */ 
    public String getEndDayOfWeek(int year,int month,int weekNo){
        int maxWeekNo = getNumWeekOfMonth(year,month);
        if (weekNo > maxWeekNo || weekNo < 1) {
            throw new IllegalArgumentException("输入周数不合法(1-" + maxWeekNo + ")");
        }
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  //周一为开始第一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH , month - 1);
        cal.set(Calendar.WEEK_OF_MONTH, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return df.format(cal);
    }
}
