package com.gomeplus.im.api.request;

import java.io.Serializable;

public class ReqFindUser implements Serializable{
	private String keyWord; //关键字
	
	private String oldPwd;//旧密码
	
	private String newPwd;//新密码

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	

}
