package cn.com.gome.manage.pojo;

import java.util.List;

public class ServerSeries {
	 public String name;
	    public String type;
	    public List<Number> data;
	    public ServerSeries(String name, String type, List<Number> data) {
	        this.name = name;
	        this.type = type;
	        this.data = data;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public List<Number> getData() {
	        return data;
	    }

	    public void setData(List<Number> data) {
	        this.data = data;
	    }
}
