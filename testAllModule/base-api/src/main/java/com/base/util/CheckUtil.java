package com.base.util;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
	
	   //验证输入金额是否合规  
	   public static boolean isNumber(String str)   
	   {   
		  // 判断小数点后2位的数字的正则表达式
	       java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");   
	       java.util.regex.Matcher match=pattern.matcher(str);   
	       if(match.matches()==false)   
	       {   
	          return false;   
	       }   
	       else   
	       {   
	          return true;   
	       }   
	   }  
	   
	    // 判断电话
	    public static boolean isTelephone(String phonenumber) {
	        String phone = "0\\d{2,3}-\\d{7,8}";
	        Pattern p = Pattern.compile(phone);
	        Matcher m = p.matcher(phonenumber);
	        return m.matches();
	    }

	    // 判断手机号
	    public static boolean isMobileNO(String mobiles) {
	        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	        Matcher m = p.matcher(mobiles);
	        return m.matches();
	    }

	    // 判断邮箱
	    public static boolean isEmail(String email) {
	        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
	        Pattern p = Pattern.compile(str);
	        Matcher m = p.matcher(email);
	        return m.matches();
	    }
	    
	    // 判断是否是正整数
	    public static boolean isPositiveInt(String str){
	    	String reg = "^[1-9]{1}\\d*$";
	        Pattern p = Pattern.compile(reg);
	        Matcher m = p.matcher(str);
	        return m.matches();
	    }
	    
	    // 判断是否是年份
	    public static boolean isYear(String yearStr){
	    	String reg = "^[1-9]{1}\\d*$";
	        Pattern p = Pattern.compile(reg);
	        return p.matcher(yearStr).matches();
	    }
	    
	    // 判断是否是月份
	    public static boolean isMonth(String monthStr){
	    	String reg = "^([1-9]{1})|(10)|(11)|(12)|(0[1-9]{1})$";
	        Pattern p = Pattern.compile(reg);
	        return p.matcher(monthStr).matches();
	    }
	    
	    public static boolean checkDateStrFormat(String dateStr, String format){
	    	SimpleDateFormat sdf = new SimpleDateFormat(format);
	    	try {
				sdf.parse(dateStr);
			} catch (Exception e) {
				return false;
			}
	    	return true;
	    }
	    
}
