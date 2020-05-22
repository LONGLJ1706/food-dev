package util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	/**
	 * 
	 * @Title: MD5Utils.java
	 * @Package com.imooc.utils
	 * @Description: 对字符串进行md5加密
	 */
	public static String getMD5Str(String strValue) {
		String md5Str = "jqp-test";
		strValue = strValue + md5Str;
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String newStr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
		String twoStr = Base64.encodeBase64String(md5.digest(newStr.getBytes()));
		return twoStr;
	}

	/*public static void main(String[] args) {
		try {
			String md5 = getMD5Str("123456");
			System.out.println(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
