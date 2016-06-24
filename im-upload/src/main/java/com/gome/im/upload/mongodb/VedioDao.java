package com.gome.im.upload.mongodb;

import org.bson.Document;

import com.gome.im.upload.model.VedioModel;
import com.gome.im.upload.utils.BeanTransUtils;

public class VedioDao extends BaseDao {
	
	private final static String collName = "t_vedio_url";

	public void addVedioUrl(VedioModel iu) {
		try {
			Document doc = BeanTransUtils.bean2Document(iu);
			insert(dbName, collName, doc);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
