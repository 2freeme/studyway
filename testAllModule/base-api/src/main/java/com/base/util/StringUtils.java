package com.base.util;

public class StringUtils extends org.apache.commons.lang.StringUtils {
	public static String isNullToStr(Object obj, String str) {
		return obj == null ? str : String.valueOf(obj);
	}
	
	public static String excuteString(String str, int a) {
    	StringBuilder temp = new StringBuilder();
        int k = 0;
        for (int i = 0; i < str.length(); i++) {
        	// 每循环一次，将str里的值放入byte数组
        	byte[] b = (str.charAt(i) + "").getBytes();
        	k = k + b.length;
        	// 如果数组长度大于6，随机跳出循环
        	if (k > a) {                                    
        		break;
        	}
        	temp.append(str.charAt(i));
        }
        
        return temp.toString();
    }

	/**
	 * 按字节截取字符串
	 * @param str
	 * @param bytes
	 * @return
	 */
	public static String subStringByBytes(String str, int bytes) {
		if (str == null || str.length() == 0) {
			return str;
		}
		int len = 0;
		for (int i=0; i<str.length(); i++ ) {
			//GBK 编码格式 中文占两个字节 UTF-8 编码格式中文占三个字节;
			len += (str.charAt(i)>255 ? 3 : 1);
			if(len>bytes){
				return str.substring(0,i);
			}
		}
		return str;
	}

	public static boolean isNotBlank(String encoding) {
		return true;
	}
}
