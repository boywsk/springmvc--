package com.gomeplus.im.manage.model;

import java.io.Serializable;

/**
 *
 * 群组
 * Created by wangshikai on 2016/2/19.
 */
public class Group implements Serializable {
    private long id;
    private String groupId;
    private long uid; // 群组创建者id
    private long groupIdHash;
    private long userId; //群主id
    private int type;// 群组类型;1:单聊,2:群聊,3:系统消息,4:小秘书
    private String groupName;
    private String groupDesc;
    private String avatar;    //头像
    private String qRcode;     //二维码
    private int capacity;
    private int isAudit;     //是否需要审核
    private int isTop;       //是否置顶
    private int isDele;
    private long createTime;
    private long updateTime;
    
	private String formateCreateTime;
	private String formateUpdateTime;
    private String nickName; //20160222-Phil-增加-群主昵称
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public long getGroupIdHash() {
        return groupIdHash;
    }

    public void setGroupIdHash(long groupIdHash) {
        this.groupIdHash = groupIdHash;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getqRcode() {
        return qRcode;
    }

    public void setqRcode(String qRcode) {
        this.qRcode = qRcode;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(int isAudit) {
        this.isAudit = isAudit;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getIsDele() {
        return isDele;
    }

    public void setIsDele(int isDele) {
        this.isDele = isDele;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getFormateCreateTime() {
		return formateCreateTime;
	}

	public void setFormateCreateTime(String formateCreateTime) {
		this.formateCreateTime = formateCreateTime;
	}

	public String getFormateUpdateTime() {
		return formateUpdateTime;
	}

	public void setFormateUpdateTime(String formateUpdateTime) {
		this.formateUpdateTime = formateUpdateTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


}
