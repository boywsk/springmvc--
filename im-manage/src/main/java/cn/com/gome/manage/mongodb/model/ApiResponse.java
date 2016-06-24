package cn.com.gome.manage.mongodb.model;

import java.io.Serializable;

/**
 * Created by wangshikai on 2015/12/22.
 */
public class ApiResponse  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String resultData;

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }
}
