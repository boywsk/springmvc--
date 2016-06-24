package cn.com.gome.manage.service;

import java.util.List;

import cn.com.gome.manage.mongodb.model.AppSearchModel;
import cn.com.gome.manage.mongodb.model.AppSysAccount;
import cn.com.gome.manage.pageSupport.PageInfo;
import cn.com.gome.manage.pojo.AppInfo;

public interface AppService {

	public void saveAppInfo(AppInfo ai);

	public void updateAppInfo(AppInfo ai);

	public List<AppInfo> getAppInfo(PageInfo pageInfo, AppSearchModel appSearchModel);
	
	public List<AppInfo> getAllAppInfo(AppSearchModel appSearchModel);

	public void delAppInfo(String uId);
	
	public void saveAppSysAccount(AppSysAccount asa);
	
	public List<AppSysAccount> getAppSysAccountByAppId(String appId, String uId);
	
	public List<AppSysAccount> displayAppSysAccountByAppId(PageInfo pageInfo, String appId, String uId, String uName);
	
	public List<AppSysAccount> displayAllAppSysAccountByAppId(String appId, String uId, String uName);
	
	public int getAPPIDCount(String appId);
	
	public List<String> getAllUidFromAppId(String appId);

}
