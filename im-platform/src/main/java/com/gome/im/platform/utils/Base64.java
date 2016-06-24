package com.gome.im.platform.utils;

/**
 * @Description: base64算法进行加解密
 * @author: lijiahuan
 * @date: 2015年5月6日 下午9:50:09
 */
public class Base64 {
	
	public static void main(String[] args) throws Exception {
		//userid= 23714
//		String userPassword = "15511637777|gome7777";
//		String userPassword = "2549333C1956481779645F32E11093EB43B824D8AA91DC2E";
//		String str = Base64.encode(userPassword);
//		System.out.println("密文: " + str);//MjU0OTMzM0MxOTU2NDgxNzc5NjQ1RjMyRTExMDkzRUI0M0I4MjREOEFBOTFEQzJF
//		str = Base64.decode(str);
//		System.out.println("明文: " + str);
		
		String test = "gome7777|1438854143";
		System.out.println("明文: " + test);
		test = Base64.encode(AES.encrypt(test));
		System.out.println("密文: " + test);
		test = AES.decrypt(Base64.decode(test));
		System.out.println("明文: " + test);
		
		
	}

	/**
	 * @Description: 编码字符串 
	 * @author: lijiahuan
	 * @date: 2015年5月6日 下午9:55:00
	 * @param data 源字符串
	 * @return
	 */
	public static String encode(String data) {
		return new String(encode(hex2byte(data.getBytes())));
	}

	/**
	 * @Description: 解码字符串 
	 * @author: lijiahuan
	 * @date: 2015年5月6日 下午9:55:27
	 * @param data 源字符串
	 * @return
	 */
	public static String decode(String data) {
		return byte2hex(decode(data.toCharArray()));
	}

	/**
	 * @Description: 编码byte[] 
	 * @author: lijiahuan
	 * @date: 2015年5月6日 下午9:55:47
	 * @param data 源
	 * @return
	 */
	public static char[] encode(byte[] data) {
		char[] out = new char[((data.length + 2) / 3) * 4];
		for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;

			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i + 1) < data.length) {
				val |= (0xFF & (int) data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length) {
				val |= (0xFF & (int) data[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}
		return out;
	}

	/**
	 * @Description: 解码 
	 * @author: lijiahuan
	 * @date: 2015年5月6日 下午9:56:19
	 * @param data 编码后的字符数组
	 * @return
	 */
	public static byte[] decode(char[] data) {

		int tempLen = data.length;
		for (int ix = 0; ix < data.length; ix++) {
			if ((data[ix] > 255) || codes[data[ix]] < 0) {
				--tempLen; // ignore non-valid chars and padding
			}
		}
		// calculate required length:
		// -- 3 bytes for every 4 valid base64 chars
		// -- plus 2 bytes if there are 3 extra base64 chars,
		// or plus 1 byte if there are 2 extra.

		int len = (tempLen / 4) * 3;
		if ((tempLen % 4) == 3) {
			len += 2;
		}
		if ((tempLen % 4) == 2) {
			len += 1;

		}
		byte[] out = new byte[len];

		int shift = 0; // # of excess bits stored in accum
		int accum = 0; // excess bits
		int index = 0;

		// we now go through the entire array (NOT using the 'tempLen' value)
		for (int ix = 0; ix < data.length; ix++) {
			int value = (data[ix] > 255) ? -1 : codes[data[ix]];

			if (value >= 0) { // skip over non-code
				accum <<= 6; // bits shift up by 6 each time thru
				shift += 6; // loop, with new bits being put in
				accum |= value; // at the bottom.
				if (shift >= 8) { // whenever there are 8 or more shifted in,
					shift -= 8; // write them out (from the top, leaving any
					out[index++] = // excess at the bottom for next iteration.
					(byte) ((accum >> shift) & 0xff);
				}
			}
		}

		// if there is STILL something wrong we just have to throw up now!
		if (index != out.length) {
			throw new Error("Miscalculated data length (wrote " + index
					+ " instead of " + out.length + ")");
		}

		return out;
	}
	
	//
	// code characters for values 0..63
	//
	private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();

	//
	// lookup table for converting base64 characters to value in range 0..63
	//
	private static byte[] codes = new byte[256];
	static {
		for (int i = 0; i < 256; i++) {
			codes[i] = -1;
			// LoggerUtil.debug(i + "&" + codes[i] + " ");
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			codes[i] = (byte) (i - 'A');
			// LoggerUtil.debug(i + "&" + codes[i] + " ");
		}

		for (int i = 'a'; i <= 'z'; i++) {
			codes[i] = (byte) (26 + i - 'a');
			// LoggerUtil.debug(i + "&" + codes[i] + " ");
		}
		for (int i = '0'; i <= '9'; i++) {
			codes[i] = (byte) (52 + i - '0');
			// LoggerUtil.debug(i + "&" + codes[i] + " ");
		}
		codes['+'] = 62;
		codes['/'] = 63;
	}
	
	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
}
