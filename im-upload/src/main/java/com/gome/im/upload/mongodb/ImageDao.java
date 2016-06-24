package com.gome.im.upload.mongodb;

import org.bson.Document;

import com.gome.im.upload.model.ImageUrl;
import com.gome.im.upload.mongodb.BaseDao;
import com.gome.im.upload.utils.BeanTransUtils;

public class ImageDao extends BaseDao {
	
	private final static String collName = "t_image_url";
	public void addImageUrl(ImageUrl iu) {
        try{
            Document doc = BeanTransUtils.bean2Document(iu);
            insert(dbName, collName, doc);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
