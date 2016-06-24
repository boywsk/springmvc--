package cn.com.gome.api.model;

/**
 * Created by wangshikai on 2016/2/29.
 */
public class MsgProtocol {
    protected String appId;//应用id
    protected short cmd;// 命令字
    private boolean isPersist;// 是否持久化
    private boolean containSelf;// 消息是否包含自己 //针对于单聊，群聊不考虑该字段
    private GroupMsg groupMsg;//聊天消息

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public boolean isPersist() {
        return isPersist;
    }

    public void setIsPersist(boolean isPersist) {
        this.isPersist = isPersist;
    }

    public GroupMsg getGroupMsg() {
        return groupMsg;
    }

    public void setGroupMsg(GroupMsg groupMsg) {
        this.groupMsg = groupMsg;
    }

    public boolean isContainSelf() {
        return containSelf;
    }

    public void setContainSelf(boolean containSelf) {
        this.containSelf = containSelf;
    }
}
