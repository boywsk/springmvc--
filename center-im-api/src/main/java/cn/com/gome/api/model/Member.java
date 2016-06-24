package cn.com.gome.api.model;

/**
 * Created by wangshikai on 2016/1/8.
 */
public class Member {
    private long imUserId;
    private String memberName;

    public long getImUserId() {
        return imUserId;
    }

    public void setImUserId(long imUserId) {
        this.imUserId = imUserId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
