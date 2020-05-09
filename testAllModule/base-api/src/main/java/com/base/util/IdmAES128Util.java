package com.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class IdmAES128Util {
	
	private static Logger logger = LoggerFactory.getLogger(IdmAES128Util.class);
	 
    static final String KEY_ALGORITHM = "AES";
    
    static final String CIPHER_ECB_PKCS5 = "AES/ECB/PKCS5Padding";
 
    public static String encrypt(String src, String seed) {
        try {
            byte[] result = encode(src.getBytes("utf-8"), seed.getBytes());
            return bytesToHexString(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
 
    public static String decrypt(String decryptSrc, String seed) {
        try {
            byte[] enc = hexStringToBytes(decryptSrc);
            byte[] result = decode(enc, seed.getBytes());
            if (result != null) {
                return new String(result);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
 
    private static byte[] encode(byte[] clear, byte[] raw) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ECB_PKCS5);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(clear);
            return encrypted;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
 
    private static byte[] decode(byte[] encrypted, byte[] raw) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ECB_PKCS5);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return decrypted;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
 
    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]) & 0xff);
        }
        return d;
    }
 
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
 
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
 
    public static void main(String[] args) {
    	String appKey = "cc837606f1f14ca191bd963f7c7fbfc6";
    	String secretId = "5elbk9r51we1ma0f";
        String content = IdmMd5Util.MD5Encode(IdmMd5Util.MD5Encode(secretId));
        appKey = appKey.substring(0, 16);
        //加密
        String encryptResultStr = encrypt(content, appKey);
        System.out.println("加密后：" + encryptResultStr);
        //解密
        String decryptResult = decrypt("4f810cd27419fa842080e5287e566ccf29b969a335c714db3489118ab645eb07e7929a4008489d89e9231c05eaf18841", appKey);
        System.out.println("解密后：" + decryptResult);
    }
}
