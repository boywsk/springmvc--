package cn.com.gome.manage.mongodb.model.file;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VoiceModel {

	private long uid;
	private long uploadTime;
	private String voiceName;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(long uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getVoiceName() {
		return voiceName;
	}

	public void setVoiceName(String voiceName) {
		this.voiceName = voiceName;
	}

}
