package cn.com.gome.api.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import cn.com.gome.api.properties.Properties;

/**
 * @Description: AES加密工具类 
 * @author: lijiahuan
 * @date: 2015年8月5日 上午11:16:55
 */
public class AES {

	/** 加密、解密key. */
	//private static final String PASSWORD_CRYPT_KEY = Properties.getInstance().getValue("app.aes.key", "app_testapp_test");
	//private static final String PASSWORD_CRYPT_KEY = PropertiesUtil.getProProperties("app.aes.key", "app_testapp_test");
	private static final String PASSWORD_CRYPT_KEY = "app_testapp_test";
	/** 加密算法,可用 DES,DESede,Blowfish. */
	private final static String ALGORITHM = "AES";

	public static void main(String[] args) {
		String content = "gome7777";
		// 加密
		System.out.println("加密前：" + content);
		System.out.println(PASSWORD_CRYPT_KEY.length());
		String encryptResult = encrypt(content);
		System.out.println(encryptResult);

		// 解密
	//	String decryptResult = decrypt("e5c406083da7ff99e7752df4449cc996");
	//	System.out.println("解密后：" + decryptResult);
	}

	/** 
	 * @Description: 使用默认密钥加密 
	 * @author: lijiahuan
	 * @date: 2015年8月5日 上午11:15:48
	 * @param content
	 * @return
	 */
	public static String encrypt(String content) {
		return parseByte2HexStr(encrypt(content, PASSWORD_CRYPT_KEY));
	}

	
	/**
	 * @Description: 使用默认密钥解密 
	 * @author: lijiahuan
	 * @date: 2015年8月5日 上午11:16:20
	 * @param content
	 * @return
	 */
	public static String decrypt(String content) {
		return new String(decrypt(parseHexStr2Byte(content), PASSWORD_CRYPT_KEY));
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
//			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
//			kgen.init(128, new SecureRandom(password.getBytes()));
//			SecretKey secretKey = kgen.generateKey();
//			byte[] enCodeFormat = secretKey.getEncoded();
			byte[] enCodeFormat=null;
			try {
				enCodeFormat = password.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
//			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
//			kgen.init(128, new SecureRandom(password.getBytes()));
//			SecretKey secretKey = kgen.generateKey();
//			byte[] enCodeFormat = secretKey.getEncoded();
			byte[] enCodeFormat=null;
			try {
				enCodeFormat = password.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
			Cipher cipher= Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}
