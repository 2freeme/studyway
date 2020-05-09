package com.base.util;

import com.midea.ccs.core.exception.ApplicationException;

public class ExceptionUtils {
	
	public static Long parseLong(String longStr,String errorMsg){		
		try {
			return Long.parseLong(longStr);
		} catch (Exception e) {
			throw new ApplicationException(errorMsg, e);
		}		
	}
	
	public static Integer parseInteger(String integerStr,String errorMsg){		
		try {
			return Integer.parseInt(integerStr);
		} catch (Exception e) {
			throw new ApplicationException(errorMsg, e);
		}		
	}
}
