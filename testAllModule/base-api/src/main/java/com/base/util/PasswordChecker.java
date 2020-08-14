package com.base.util;

import com.base.network.ApplicationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * <pre>
 * 校验密码是否合法
 * </pre>
 * 
 * @author xiawb3 wenbin.xia@midea.com.cn
 * @version 1.00.00
 * 2016-11-29
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class PasswordChecker {
	// 最短密码长度
	private static final int minPasswordLen = 6;
	// 最长密码长度
	private static final int maxPasswordLen = 16;
	// 密码不能包含的字符
	private static final String illegalCharacter = "!<>&%+";
	
	private PasswordChecker(){
		// TODO:读取properties文件中的配置信息
	}
	
	/**
	 * 校验密码正确性
	 * @param passwd
	 * @param uid
	 * @return
	 * @throws ApplicationException
	 */
	public static boolean check(String passwd, String uid) throws ApplicationException {
		if(passwd == null || "".equals(passwd)) {
			throw new ApplicationException("密码不能为空");
		}
		if(uid == null || "".equals(uid)) {
			throw new ApplicationException("UID不能为空");
		}
		if(!checkLength(passwd)) {
			throw new ApplicationException("密码长度必须大于等于"+minPasswordLen+"位，小于等于"+maxPasswordLen+"位");
		}
		if(!checkContainUID(passwd, uid)) {
			throw new ApplicationException("不能在密码中包含UID");
		}
		if(!checkIllegalCharacter(passwd)) {
			throw new ApplicationException("不能在密码中包含"+illegalCharacter+"");
		}
		if(!checkLegal(passwd)) {
			throw new ApplicationException("密码必须是四种密码元素（数字、小写字母、大写字母、特殊字符【"+illegalCharacter+"除外】）中的三种组合（例如：Qwe123@#）");
		}
		return true;
	}
	
	/**
	 * 校验密码长度
	 * @param passwd
	 * @return boolean
	 */
	public static boolean checkLength(String passwd) {
		if(passwd.length() < minPasswordLen || passwd.length() > maxPasswordLen) {
			return false;
		}
		return true;
	}
	
	/**
	 * 校验密码是否包含UID
	 * @param passwd
	 * @param uid
	 * @return
	 */
	public static boolean checkContainUID(String passwd, String uid) {
		if(passwd.indexOf(uid) > -1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 校验密码是否包含特殊字符
	 * @param passwd
	 * @return
	 */
	public static boolean checkIllegalCharacter(String passwd) {
		Pattern p = Pattern.compile("["+illegalCharacter+"]+");
		Matcher mc = p.matcher(passwd);
		if(mc.find()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 校验密码是否合法
	 * @param passwd
	 * @return
	 */
	public static boolean checkLegal(String passwd) {
		Pattern numberPattern = Pattern.compile("[0-9]+");
		Matcher numberMatcher = numberPattern.matcher(passwd);
		Pattern lowcasePattern = Pattern.compile("[a-z]+");
		Matcher lowcaseMatcher = lowcasePattern.matcher(passwd);
		Pattern upcasePattern = Pattern.compile("[A-Z]+");
		Matcher upcaseMatcher = upcasePattern.matcher(passwd);
		Pattern specialPattern = Pattern.compile("((?=[\\x21-\\x7e]+)[^A-Za-z0-9])+");
		Matcher specialMatcher = specialPattern.matcher(passwd);
		int matchNum = 0;
		if(numberMatcher.find()) {
			matchNum ++;
		}
		if(lowcaseMatcher.find()) {
			matchNum ++;
		}
		if(upcaseMatcher.find()) {
			matchNum ++;
		}
		if(specialMatcher.find()) {
			matchNum ++;
		}
		if(matchNum < 3) {
			return false;
		}
		return true;
	}
}
