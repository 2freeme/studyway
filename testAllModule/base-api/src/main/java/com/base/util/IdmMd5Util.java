package com.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * <pre>
 * 4A接口Md5工具
 * </pre>
 * @author xiawb3 wenbin.xia@midea.com.cn
 * @version 1.00.00
 */
public class IdmMd5Util {
	
	private final static Logger logger = LoggerFactory.getLogger(IdmMd5Util.class);
	 
    /**
     * MD5加密
     *
     * @param s 字符串
     * @return 加密后的字符串(32位大写)
     */
    public static String MD5Encode(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }
 
    public static String SHA256Encode(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }
 
    /**
     * @param password AD密码
     * @return 加密后的密码
     * @throws UnsupportedEncodingException 不支持的编码
     */
    public static byte[] encodeAdPassword(String password) throws UnsupportedEncodingException {
        return ("\"" + password + "\"").getBytes("UTF-16LE");
    }
    //strInput= plain + "&key=" + md5key;
    //charSet 编码
    public static String encryptMD5(String strInput, String charSet) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(strInput.getBytes(charSet));
            byte b[] = md.digest();
            buf = new StringBuffer(b.length * 2);
            for (int i = 0; i < b.length; i++) {
                if (((int) b[i] & 0xff) < 0x10) { // & 0xff
                    buf.append("0");
                }
                buf.append(Long.toHexString((int) b[i] & 0xff));
            }
        } catch (Exception e) {
            return null;
        }
        String result = buf.toString();
        return result;
    }
}
