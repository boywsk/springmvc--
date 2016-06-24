package cn.com.gome.test;


import java.io.Serializable;
import java.util.List;

/**
 * 用户信息 Request model
 */
public class ReqUserInfo extends BaseRequest implements Serializable {
    private int opt; //1：添加用户操作   2：更新用户操作
    private List<UserInfo> userInfoList;

    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
