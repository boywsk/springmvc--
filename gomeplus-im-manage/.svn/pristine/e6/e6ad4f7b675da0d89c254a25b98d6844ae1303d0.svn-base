package com.gomeplus.im.manage.mongodb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gomeplus.im.manage.mongodb.dao.AppDao;
import com.gomeplus.im.manage.mongodb.dao.AppSysAccountDao;
import com.gomeplus.im.manage.mongodb.model.AppAccount;
import com.gomeplus.im.manage.mongodb.model.AppInfo;
import com.gomeplus.im.manage.mongodb.model.AppSearchModel;
import com.gomeplus.im.manage.mongodb.model.AppSysAccount;
import com.gomeplus.im.manage.pageSupport.PageInfo;
import com.gomeplus.im.manage.utils.StringUtil;
@Service
public class AppService  {
	
	private static Logger log = LoggerFactory.getLogger(AppService.class);
	private final static AppDao appDao = new AppDao();
	private final static AppSysAccountDao  asaDao = new AppSysAccountDao();

	public void saveAppInfo(AppInfo app) {
		long createTime = System.currentTimeMillis();
		app.setCreateTime(Long.toString(createTime));
		app.setAppKey(StringUtil.getUuid());
		log.info("AppInfo："+JSON.toJSONString(app));
		appDao.saveApp(app);
	}

	public void updateAppInfo(AppInfo app) {
		app.setUpdateTime(Long.toString(System.currentTimeMillis()));
		appDao.updateAppInfo(app);
	}

	public List<AppInfo> getAppInfo(PageInfo pageInfo,AppSearchModel appSearchModel) {
		List<AppInfo> app = appDao.getAppInfo(pageInfo, appSearchModel);
		return app;
	}
	
	public List<AppInfo> getAllAppInfo(AppSearchModel appSearchModel) {
		List<AppInfo> app = appDao.getAllAppInfo(appSearchModel);
		return app;
	}
	
	public int getAPPIDCount(String appId){
		int count = appDao.getAPPIDCount(appId);
		return count;
	}

	public void delAppInfo(String uId) {
		// TODO Auto-generated method stub

	}

	public void saveAppSysAccount(AppSysAccount asa) {		
		asaDao.saveAppSysAccount(asa);
		
	}
	public List<AppSysAccount> getAppSysAccountByAppId(String appId, String uId){
		List<AppSysAccount> asaList = asaDao.getAppSysAccountByAppId(appId,uId);
		return asaList;
	}

	public List<AppSysAccount> displayAppSysAccountByAppId(PageInfo pageInfo, String appId, String uId, String uName) {
		AppAccount appAccount = new AppAccount();
		appAccount.setAppId(appId);
		appAccount.setuId(uId);
		appAccount.setuName(uName);
		List<AppSysAccount> asaList = asaDao.displayAppSysAccountByAppId(pageInfo, appAccount);
		return asaList;
	}
	
	public List<AppSysAccount> displayAllAppSysAccountByAppId(String appId, String uId, String uName) {
		AppAccount appAccount = new AppAccount();
		appAccount.setAppId(appId);
		appAccount.setuId(uId);
		appAccount.setuName(uName);
		List<AppSysAccount> asaList = asaDao.displayAllAppSysAccountByAppId(appAccount);
		return asaList;
	}

	public List<String> getAllUidFromAppId(String appId) {
		List<String> asaList = asaDao.getAllUidFromAppId(appId);
		return asaList;
	}
	
}
