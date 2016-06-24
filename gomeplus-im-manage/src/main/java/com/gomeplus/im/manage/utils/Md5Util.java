package com.gomeplus.im.manage.utils;

import java.security.MessageDigest;

/**
 * Created by wangshikai on 2016/3/2.
 */
public class Md5Util {

    /***
     * MD5加密 生成32位md5码
     *
     * @return 返回32位md5码
     */
    public static String md5Encode(String str) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = str.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 测试主函数
     *
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        String appId = "appIdappIdappId";
        String time = "1459240174631";
        String uid = "11111";
        String validateStr = md5Encode(appId+uid+time);
        validateStr = md5Encode("I'm Persian.");
        System.out.println(validateStr);
        String a = "e939c6f77b3a161f8421e9e113bc71e7";
        if(a.equals(validateStr)){
        	System.out.println("OK");
        }
    }
}
