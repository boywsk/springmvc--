package com.gomeplus.im.api.request.response;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * 好友业务
 * @author lijinpeng
 *
 */
public class RspFriend implements Serializable{
    private long friendUserId;//好友ID
    private String friendNickName;//好友昵称
    private String firstNickNameSpell;//好友昵称首字母缩写
    private String mark;//好友备注
    private String firstMarkSpell;//备注首字母缩写
    private long friendGroupId;//好友分组
    public RspFriend() {
	}
	public long getFriendUserId() {
		return friendUserId;
	}
	public void setFriendUserId(long friendUserId) {
		this.friendUserId = friendUserId;
	}
	public String getFriendNickName() {
		if (StringUtils.isBlank(friendNickName)) {
			return StringUtils.EMPTY;
		}
		return friendNickName;
	}
	public void setFriendNickName(String friendNickName) {
		this.friendNickName = friendNickName;
	}
	public String getMark() {
		if (StringUtils.isBlank(mark)) {
			return StringUtils.EMPTY;
		}
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public long getFriendGroupId() {
		return friendGroupId;
	}
	public void setFriendGroupId(long friendGroupId) {
		this.friendGroupId = friendGroupId;
	}
	public String getFirstNickNameSpell() {
		if (StringUtils.isBlank(firstNickNameSpell)) {
			return StringUtils.EMPTY;
		}
		return firstNickNameSpell;
	}
	public void setFirstNickNameSpell(String firstNickNameSpell) {
		this.firstNickNameSpell = firstNickNameSpell;
	}
	public String getFirstMarkSpell() {
		if (StringUtils.isEmpty(firstMarkSpell)) {
			return StringUtils.EMPTY;
		}
		return firstMarkSpell;
	}
	public void setFirstMarkSpell(String firstMarkSpell) {
		this.firstMarkSpell = firstMarkSpell;
	}
	
   
}
