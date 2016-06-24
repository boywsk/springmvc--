package cn.com.gome.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshikai on 2016/1/12.
 */
public class UserInfo {
    private long uid;  //用户id
    private String nickName; //用户名称
    private int deviceType; //app设备类型
    private String deviceToken; //app设备token

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public static void main(String[] args) {
//        List<String> li = new ArrayList<>();
//        li.add("1dfsdf2");
//        li.add("1dfsdf3");
//        li.add("1dfsdf4");
//        li.add("1dfsdf5");
//        li.add("1dfsdf6");
//        li.add("1dfsdf7");
//        System.out.println(li.toString());
//        long time = System.currentTimeMillis();
//        long result = time + 4000000000000L;//3999395200000
//        long sevenDayTime = 7*24*60*60*1000;//5463998784920
//        System.out.println(sevenDayTime);
//        System.out.println(4000000000000L/sevenDayTime);
//        System.out.println(result-time);
        int A = 'A';
        System.out.println(A);
        int Z = 'Z';
        System.out.println(Z);
        int a = 'a';
        System.out.println(a);
        int z = 'z';
        System.out.println(z);
        for (int i = 91; i < 97; i++){
            char x = (char)i;
            System.out.println(x);
        }
    }

}
