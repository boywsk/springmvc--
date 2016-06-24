package cn.com.gome.api.model.request;

import cn.com.gome.api.model.ReqUser;
import cn.com.gome.api.model.UserInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangshikai on 2016/1/13.
 */
public class ReqUserInfo extends BaseRequest implements Serializable {
    private int useUid; //1：使用第三方自己的uid作为imUserId  0：不使用第三方自己的uid作为imUserId
    private int opt; //1：添加用户操作   2：更新用户操作
    private ReqUser reqUser;

    public int getUseUid() {
        return useUid;
    }

    public void setUseUid(int useUid) {
        this.useUid = useUid;
    }


    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public ReqUser getReqUser() {
        return reqUser;
    }

    public void setReqUser(ReqUser reqUser) {
        this.reqUser = reqUser;
    }
}
