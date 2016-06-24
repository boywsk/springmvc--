package cn.com.gome.api.utils;

import java.util.Random;

/**
 * @Description: 生成login的TOKEN串 
 * @author: lijiahuan
 * @date: 2015年5月6日 下午8:39:46
 */
public class GenerateToken {
	
	/**
	 * @Description: 在登陆后调用，生成随机的Token信息 
	 * @author: lijiahuan
	 * @date: 2015年5月6日 下午8:43:29
	 * @return
	 */
	public static String generateToken(){
		//生成24位随机数字
		int length = 24;
		return getCharAndNumr(length);
	}

	/**
	 * @Description: 生成随机数字和字母组合 
	 * @author: lijiahuan
	 * @date: 2015年5月6日 下午8:39:56
	 * @param length
	 * @return
	 */
	private static String getCharAndNumr(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
//	 public static void main(String[] args) {
//		  System.out.println("生成Token为：" + generateToken());
//		  System.out.println("生成Token为：" + generateToken());
//	 }

}
