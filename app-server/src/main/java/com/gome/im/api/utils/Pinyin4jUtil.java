package com.gome.im.api.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pinyin4jUtil {
	static Logger log = LoggerFactory.getLogger(Pinyin4jUtil.class);
	
	/**
	 * 将汉字转换为全拼
	 * @param src
	 * @return String
	 */
	public static String getPinYin(String src) {
		String ret = "";
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		char[] srcCharArr = src.toCharArray();
		try {
			for (char srcChar : srcCharArr) {
				// 判断能否为汉字字符
				if (Character.toString(srcChar).matches("[\\u4E00-\\u9FA5]+")) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(srcChar, format);
					ret += temp[0];
				} else {
					ret += Character.toString(srcChar);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			log.error("getPinYin", e);
		}
		
		return ret;
	}

	/**
	 * 提取每个汉字的首字母
	 * @param str
	 * @return String
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert.toUpperCase();
	}
	
	/**
	 * 提取全拼和首字母
	 * @param str
	 * @return
	 */
	public static String[] getPinYinAndHeadChar(String src) {
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		char[] srcCharArr = src.toCharArray();
		String pinyin = "";
		String headChar = "";
		try {
			for (char srcChar : srcCharArr) {
				// 判断能否为汉字字符
				if (Character.toString(srcChar).matches("[\\u4E00-\\u9FA5]+")) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(srcChar, format);
					pinyin += temp[0];
					headChar += temp[0].charAt(0);
				} else {
					String str = Character.toString(srcChar).toUpperCase();
					pinyin += str;
					headChar += str;
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			log.error("getPinYinAndHeadChar", e);
		}
		String[] retArr = new String[]{pinyin, headChar};
		
		return retArr;
	}

	/**
	 * 将字符串转换成ASCII码
	 * @param cnStr
	 * @return String
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		// 将字符串转换成字节序列
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// 将每个字符转换成ASCII码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	public static void main(String[] args) {
		String cnStr = "重s庆";
		System.out.println(getPinYin(cnStr));
		System.out.println(getPinYinHeadChar(cnStr));
		String[] array = getPinYinAndHeadChar(cnStr);
		System.out.println(array[0]);
		System.out.println(array[1]);
//		System.out.println(getCnASCII(cnStr));
	}
}
