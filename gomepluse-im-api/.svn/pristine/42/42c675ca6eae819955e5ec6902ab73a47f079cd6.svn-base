package com.gomeplus.im.api.utils;

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
        String appId = "test_appId";
        String appKey = "test_appKey";
        String msg = appId+"|"+appKey;
        System.out.println("原始：" + msg);
        String md5str = md5Encode(msg);
        System.out.println("MD5后：" + md5str);

        String validateStr = md5Encode("test_appId"+"|"+"test_appKey");
        if(validateStr.equals(md5str)){
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }
    }
}
