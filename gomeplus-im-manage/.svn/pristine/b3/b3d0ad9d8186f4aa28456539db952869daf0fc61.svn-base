package com.gomeplus.im.manage.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * SHA-256 用户密码 加密方式
 * Created by wangshikai on 2016/6/2.
 */
public class PasswordEncode {

    /**
     *
     * @param password  密码
     * @param salt      盐值
     * @return
     */
    public static String encode(String password, String salt) {
        String pwd = null;
        try {
            pwd = SHA256(password + salt);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return pwd;
    }

    public static String SHA256(String str) throws UnsupportedEncodingException {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("SHA-256");
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

    public static void main(String[] args) {
        String pwd = "123456";
        pwd = encode(pwd, "gomeplus-pwd-salt-^#$@%&");
        System.out.println(pwd);
        System.out.println(pwd.length());
    }
}
