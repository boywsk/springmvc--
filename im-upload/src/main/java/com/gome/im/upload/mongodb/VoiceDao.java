package com.gome.im.upload.mongodb;

import org.bson.Document;

import com.gome.im.upload.model.VoiceModel;
import com.gome.im.upload.utils.BeanTransUtils;

public class VoiceDao extends BaseDao {

	private final static String collName = "t_voice_url";

	public void addVoiceUrl(VoiceModel iu) {
		try {
			Document doc = BeanTransUtils.bean2Document(iu);
			insert(dbName, collName, doc);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
