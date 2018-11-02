package com.mtq.ols.dal;

import com.mtq.ols.sap.bean.CldSapKAppParm.MtqAppInfo;
import com.mtq.ols.sap.bean.CldSapKAppParm.MtqAppInfoNew;

public class CldDalKAppCenter {
	
	/** app��Ϣ  */
	private MtqAppInfoNew appinfo;
	/** ���°汾  */
	private boolean hasNewVersion;
	
	private static CldDalKAppCenter cldDataKApp;

	public static CldDalKAppCenter getInstance() {
		if (cldDataKApp == null) {
			cldDataKApp = new CldDalKAppCenter();
		}
		return cldDataKApp;
	}
	
	private CldDalKAppCenter() {
		appinfo = new MtqAppInfoNew();
		hasNewVersion = false;
	}
	
	public void uninit() {
		appinfo = new MtqAppInfoNew();
		hasNewVersion = false;
	}
	
	public MtqAppInfoNew getMtqAppInfo() {
		return appinfo;
	}

	public void setMtqAppInfo(MtqAppInfoNew appinfo) {
		this.appinfo = appinfo;
	}
	
	public boolean getNewVersion() {
		return hasNewVersion;
	}

	public void setNewVersion(boolean hasNewVersion) {
		this.hasNewVersion = hasNewVersion;
	}
}
