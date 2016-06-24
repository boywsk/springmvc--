package cn.com.gome.manage.mongodb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshikai on 2016/2/18.
 */
public class MsgStats implements Serializable {
    private String day;
    private List<Long> singleMsgCounts = new ArrayList<>();
    private List<Long> groupMsgCounts = new ArrayList<>();

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Long> getSingleMsgCounts() {
        return singleMsgCounts;
    }

    public void setSingleMsgCounts(List<Long> singleMsgCounts) {
        this.singleMsgCounts = singleMsgCounts;
    }

    public List<Long> getGroupMsgCounts() {
        return groupMsgCounts;
    }

    public void setGroupMsgCounts(List<Long> groupMsgCounts) {
        this.groupMsgCounts = groupMsgCounts;
    }
}
