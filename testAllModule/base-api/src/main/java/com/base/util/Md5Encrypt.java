package com.base.util;

import com.midea.ccs.core.exception.ApplicationException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class Md5Encrypt {

	public static String BASE_CHARSET = "utf-8";

	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param text
	 *            明文
	 * @return 密文
	 */
	public static String md5(String text) {
		return md5(text, null);
	}

	public static String md5(String text, String encoding) {
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(
					"System doesn't support MD5 algorithm.");
		}
		try {
			if (StringUtils.isNotBlank(encoding)) {
				msgDigest.update(text.getBytes(encoding));
			} else {
                msgDigest.update(text.getBytes());
			}
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(
					"System doesn't support Encoding.", e);
		}
		byte[] bytes = msgDigest.digest();
		byte tb;
		char low;
		char high;
		char tmpChar;
		String md5Str = "";
		for (int i = 0; i < bytes.length; i++) {
			tb = bytes[i];
			tmpChar = (char) ((tb >>> 4) & 0x000f);
			if (tmpChar >= 10) {
				high = (char) (('a' + tmpChar) - 10);
			} else {
				high = (char) ('0' + tmpChar);
			}
			md5Str += high;
			tmpChar = (char) (tb & 0x000f);
			if (tmpChar >= 10) {
				low = (char) (('a' + tmpChar) - 10);
			} else {
				low = (char) ('0' + tmpChar);
			}
			md5Str += low;
		}
		return md5Str;
	}

	public static final String MD5To32bit(String readyEncryptStr) {
		try {
			if (readyEncryptStr != null) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(readyEncryptStr.getBytes());
				byte[] b = md.digest();
				StringBuilder su = new StringBuilder();
				for (int offset = 0, bLen = b.length; offset < bLen; offset++) {
					String haxHex = Integer.toHexString(b[offset] & 0xFF);
					if (haxHex.length() < 2) {
						su.append("0");
					}
					su.append(haxHex);
				}
				return su.toString();
			} else {
				return null;
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5加密错误！");
		}
	}
	
	/**
	 * <pre>
	 * ccs_msale_region_rule 的 OBJ_MD5 字段生成
	 * 所选账套排序后","隔开的字符串MD5加密后的值
	 * </pre>
	 * @param list 账套号 List
	 * @return 加密字符串
	 */
	public static final <T> String getObjMd5(List<T> list) {
		final int type;
		if (list.get(0) instanceof Long) {// Long型
			type = 1;
		} else if (list.get(0) instanceof String) {// 字符型
			type = 2;
		} else {
			throw new ApplicationException("此方法现现只支持Long和String，若要增加类型，请修改此方法");
		}
		Map<T,T> map = new HashMap<T,T>();
		for (int i= list.size() - 1; i >=0; i--) {
			if (map.get(list.get(i)) == null) {
				map.put(list.get(i), list.get(i));
			} else {
				list.remove(i);
			}
		}
		Collections.sort(list, new Comparator<T> () {

			@Override
			public int compare(Object arg0, Object arg1) {
				if (type == 1) {
					Long a0 = (Long) arg0;
					Long a1 = (Long) arg1;
					return a0.compareTo(a1);
				} else if (arg0.toString().length() == arg1.toString().length()) {
					return arg0.toString().compareTo(arg1.toString());
				}
				return arg0.toString().length() - arg1.toString().length();
			}
			
		});
		
		StringBuilder sb = new StringBuilder();
		String result = "";
		if(list!=null && list.size() > 0 ){
			for(Object id : list){
				sb.append(id+",");
			}
			if(sb.length() > 0){
				result = sb.substring(0, sb.length()-1);
				//setsOfBooksIds 不足5位取原字符串+MD5加密字符串，否则截取setsOfBooksIds前5位+MD5加密字符串
				result = (result.length() < 5 ? result : result.substring(0, 5)) + md5(result);
			}
			
		}
		return result;
	}
	
}
