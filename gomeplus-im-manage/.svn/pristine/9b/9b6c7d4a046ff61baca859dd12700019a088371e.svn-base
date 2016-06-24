package com.gomeplus.im.manage.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 字符工具类
 * 
 * @author xiazeyong
 *
 */
public class StringUtil {
	static Logger log = LoggerFactory.getLogger(StringUtil.class);
	
	private static final Random rand = new Random();
	private static String PATTERN = "[^\\w\\u4E00-\\u9FA5\\uF900-\\uFA2D\\pP]";

	/**
	 * 用省略号"…"来替代超过长度的字符串
	 * 
	 * 例: StringUtil.abbreviateByEllipsis(null,1); = null <br/>
	 * StringUtil.abbreviateByEllipsis("",1); = "" <br/>
	 * StringUtil.abbreviateByEllipsis("测试",2); = "测试" <br/>
	 * StringUtil.abbreviateByEllipsis("测试",1); = "测…" <br/>
	 * 
	 * @param str
	 *            要截取的字符串
	 * @param maxLength
	 *            截取的开始长度
	 * @return 返回截取后的字符串
	 */
	public static String abbreviateByEllipsis(String str, int maxLength) {

		if (str == null) {
			return null;
		}

		// 调整最大宽度
		if (maxLength < 1) {
			maxLength = 1;
		}

		if (str.length() <= maxLength) {
			return str;
		}
		return str.substring(0, maxLength) + "…";
	}

	/**
	 * 手机号码校验
	 * 
	 * @param mobiles
	 *            待检测的手机号码
	 * @return 返回检测的结果
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 邮编校验
	 * 
	 * @param postCode
	 *            邮编
	 * @return 邮编是否合法
	 */
	public static boolean isPostCode(String postCode) {
		Pattern p = Pattern.compile("^[0-9]{6}$");
		Matcher m = p.matcher(postCode);
		return m.matches();
	}

	/**
	 * 固定电话校验
	 * 
	 * @param phone
	 *            固话
	 * @return 固话是否合法
	 */
	public static boolean isPhone(String phone) {
		Pattern p = Pattern.compile("^((\\d{3}|\\d{4})-)?(\\d{7}|\\d{8})$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	/**
	 * 获取字符串的长度，中文占二个字符,英文数字占一个字符
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static long length(String value) {
		long valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < value.length(); i++) {
			// 获取一个字符
			String temp = value.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为2
				valueLength += 2;
			} else {
				// 其他字符长度为1
				valueLength += 1;
			}
		}
		// 进位取整
		return (long) Math.ceil(valueLength);
	}

	/**
	 * 判断输入字符串是否只包含半角字母和数字
	 * 
	 * @param value
	 *            输入字符串
	 * @return 输入字符串只包含半角字母和数字，返回true，否则返回false
	 */
	public static boolean isUnicodeStr(String value) {
		Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher m = p.matcher(value);
		return m.matches();
	}

	/**
	 * 返回大于0的整数字符串
	 * 
	 * @param value
	 *            需要验证的内容
	 * @return true/false
	 */
	public static boolean isPositiveNum(String value) {
		Pattern p = Pattern.compile("^[1-9]*[1-9][0-9]*$");
		Matcher m = p.matcher(value);
		return m.matches();
	}

	/**
	 * 构造部分隐藏的字符串
	 * 
	 * @param origStr
	 *            源字符串
	 * @param begin
	 *            源字符串前边需要显示的个数
	 * @param end
	 *            源字符串后边边需要显示的个数
	 * @return 构造部分隐藏字符串
	 */
	public static String buildPartHideStr(String origStr, int begin, int end) {
		if (isBlank(origStr) || begin >= origStr.length() || end >= origStr.length()
				|| (begin + end) >= origStr.length()) {
			return "";
		}
		int len = origStr.length() - begin - end;
		String starStr = "";
		if (len > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i <= len; i++) {
				sb.append("*");
			}
			starStr = sb.toString();
		}
		return left(origStr, begin) + starStr + right(origStr, end);
	}

	/**
	 * 隐藏真实姓名 名字两位字符，名打* 名字三位字符，中间打* 名字超过三位字符，前后不打*，中间两位*
	 * 
	 * @param realName
	 *            真实姓名
	 * @return 打*结果
	 */
	public static String hiddenRealName(String realName) {
		if (realName.length() > 1 && realName.length() <= 2) {
			return realName.toCharArray()[0] + "*";
		} else if (realName.length() == 3) {
			return realName.toCharArray()[0] + "*" + realName.toCharArray()[2];
		} else if (realName.length() > 3) {
			return realName.toCharArray()[0] + "**" + realName.toCharArray()[realName.length() - 1];
		}
		return realName;
	}

	/**
	 * 缩短
	 * 
	 * @param s
	 * @return
	 */
	public static String getShortName(String s) {
		if (s == null) {
			return null;
		}
		int length = 0;
		for (int i = 0, j = s.length(); i < j; i++) {
			int codePoint = Character.codePointAt(s, i);
			if (codePoint >= 0 && codePoint <= 255) {
				length++;
			} else {
				length += 2;
			}
			if (length > 10) {
				// 补长度，有些表情要占用2个字符（两个char），如果截断了会显示成?
				int lastCodePoint = Character.codePointAt(s, i - 1);
				if (Character.charCount(lastCodePoint) == 2) {
					if (i + 1 < j) {
						return s.substring(0, i + 1) + "...";
					} else if (i + 1 == j) {
						return s;
					}
				}

				return s.substring(0, i) + "...";
			}
		}
		return s;
	}

	/**
	 * 过滤不支持的字符并缩短
	 * 
	 * @param s
	 * @return
	 */
	public static String getBasicAndShortName(String s) {
		if (s == null) {
			return null;
		}
		String temp = s.replaceAll(PATTERN, "..");
		return getShortName(temp);
	}

	/**
	 * URLencode
	 * 
	 * @param str
	 * @param charset
	 * @param defaultStr
	 * @return
	 */
	public static String urlEncode(String str, String charset, String defaultStr) {
		try {
			return URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException e) {
			return defaultStr;
		}
	}

	public static boolean isBlank(String str) {
		if (null == str) {
			return true;
		}
		if ("".equals(str) || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	private static String left(String str, int begin) {
		return str.substring(0, begin);
	}

	private static String right(String str, int end) {
		return str.substring(str.length() - end);
	}

	/**
	 * 系统目录
	 * 
	 * @return
	 */
	public static String getRealPath() {
		String realPath = StringUtil.class.getClassLoader().getResource("").getFile();
		File file = new File(realPath);
		if (realPath.indexOf("bin") > 0) {
			realPath = file.getParentFile().getAbsolutePath();
		} else {
			realPath = file.getAbsolutePath();
		}
		return realPath;
	}

	/**
	 * 时间格式转换
	 * 
	 * @param time
	 * @return MM月dd日 HH点mm分
	 */
	public static String long2DateString(Long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH点mm分");
		if (time != null) {
			if (time.longValue() != 0) {
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	
	/**
	 * 时间格式转换
	 * @param time
	 * @return yyyy-MM-dd
	 */
	public static String long2DateString2(Long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (time != null) {
			if (time.longValue() != 0) {
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	
	/**
	 * 时间格式转换
	 * 
	 * @param time
	 * @return HH:mm
	 */
	public static String long2DateString3(Long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		if (time != null) {
			if (time.longValue() != 0) {
				return format.format(new Date(time.longValue()));
			}
		}
		return null;
	}
	
	/**
	 * 时间格式转换
	 */
	public static long dateString2Long(String time) {
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (time != null) {
					return format.parse(time).getTime();
			}
		} catch(Exception e) {
			log.error("dateString2Long", e);
		}
		
		return 0;
	}

	/**
	 * 获取uuid
	 * 
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 随机数
	 * 
	 * @return
	 */
	public static int getRanNumber() {
		return rand.nextInt(Integer.MAX_VALUE);
	}
	
	/**
     * 改进的32位FNV算法1
     * @param data  字符串
     * @return int值
     */
    public static int FNVHash1(String data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < data.length(); i++)
            hash = (hash ^ data.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }
    
    /**
	 * SDBM算法
	 */
	public static int SDBMHash(String str) {
		int hash = 0;
		for (int i = 0; i < str.length(); i++) {
			hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}
		return (hash & 0x7FFFFFFF);
	}


	public static void main(String[] args) {
//		/*System.out.println(StringUtil.getRealPath());
//		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
//		String v = map.put("11", "22");
//		System.out.println(v);
//		v = map.put("11", "33");
//		System.out.println(v);*/
//		String oo0 = "1767_3964330598";
//		String oo1 = "165632804472750092";
//		int hashValue = FNVHash1(oo0);//2955_3964330598; 3170_3964330598
//		System.out.println("hashValue: "+hashValue);
//		int coll0 = SDBMHash(oo0);
//		System.out.println("hashValue: "+hashValue+" ; 0,dbName_No. : "+hashValue%64 + "; collName_NO.: "+coll0%2);
//
//		int hashValue1 = FNVHash1(oo1);
//		System.out.println("hashValue1: "+hashValue1);
//		int coll1 = SDBMHash(oo1);
//		System.out.println("hashValue: "+hashValue1+" ; 1,dbName_No. : "+hashValue1%64 + "; collName_NO.: "+coll1%2);

		System.out.println(getDBAndTableName("152619067147026472",""));
		
	}


	private static String[] getDBAndTableName(String groupId,String appId) {
		String[] arr = new String[2];
		int hashValue = StringUtil.FNVHash1(groupId);
		int hashValueForColl = StringUtil.SDBMHash(groupId);
		System.out.println(hashValue % 64);
		System.out.println(hashValueForColl % 2);
		return arr;
	}
}
