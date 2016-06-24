package cn.com.gome.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangshikai on 2016/3/17.
 */
public class GroupExtra implements Serializable{
    private String nickName; //群操作人的名称   如群创建者名称，群邀请者名称。。。
    private String groupName;
    private String msgBody;  //文案内容
    private String ext;    //自定义扩展内容
    private long imUserId; //邀请人的imUserId

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public long getImUserId() {
        return imUserId;
    }

    public void setImUserId(long imUserId) {
        this.imUserId = imUserId;
    }
}
