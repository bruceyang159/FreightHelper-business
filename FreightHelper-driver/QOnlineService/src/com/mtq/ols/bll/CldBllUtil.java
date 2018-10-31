/*
 * @Title CldBllUtil.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:59
 * @version 1.0
 */
package com.mtq.ols.bll;

import com.cld.base.CldBase;
import com.cld.device.CldPhoneManager;
import com.cld.device.CldPhoneNet;
import com.cld.setting.CldSetting;
import com.cld.utils.CldPackage;
import com.mtq.ols.api.CldOlsBase.CldOlsParam;
import com.mtq.ols.sap.CldSapKAccount;
import com.mtq.ols.sap.CldSapKAppCenter;
import com.mtq.ols.sap.CldSapKCombo;
import com.mtq.ols.sap.CldSapKPub;

import android.content.Context;

/**
 * �߼��㹫��������
 * 
 * @author Zhouls
 * @date 2014-9-11 ����3:38:52
 */
public class CldBllUtil {

	/** �豸Ӧ������(1:CM;5:����). */
	private int apptype;
	/** ҵ����(1:CM��5������) */
	private int bussinessid;
	/** Ӧ��id. */
	private int appid;
	/** �豸[ϵͳ]����(1Android 2IOS 3WP 4WINCE��5δ֪) */
	private int osType;
	/** ��Ϣ����ģ��(1:K��;2:WEB��ͼ;3:һ��ͨ) */
	private int module;
	/** ��ȡ����汾�ţ�6.2�� */
	private String prover;
	/** �����汾��(M1831-D6073-3223J0K) */
	private String appver;
	/** ��ͼ�汾 */
	private String mapver;
	/** ����·�� */
	private String appPath;
	/** �Ƿ��ǲ��԰� */
	private boolean isTestVersion;
	/** ���������� */
	private int cid;

	private static CldBllUtil cldBllUtil;

	/**
	 * Instantiates a new cld bll util.
	 */
	private CldBllUtil() {
		apptype = 1;
		bussinessid = 1;
		appid = 9;
		osType = 1;
		module = 1;
		prover = "";
		appver = "";
		mapver = "";
		appPath = "";
		cid = 1;
		isTestVersion = true;
	}

	/**
	 * Gets the single instance of CldBllUtil.
	 * 
	 * @return single instance of CldBllUtil
	 */
	public static CldBllUtil getInstance() {
		if (cldBllUtil == null) {
			cldBllUtil = new CldBllUtil();
		}
		return cldBllUtil;
	}

	/**
	 * ���ݳ�ʼ��
	 * 
	 * @param parm
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-8 ����4:36:07
	 */
	public void init(CldOlsParam parm) {
		/**
		 * ��ʼ���豸apptype
		 */
		apptype = parm.apptype;
		CldSetting.put("apptype", apptype + "");
		/**
		 * ��ʼ��ҵ����
		 */
		bussinessid = parm.bussinessid;
		CldSetting.put("bussinessid", bussinessid + "");
		/**
		 * Ӧ��id
		 */
		appid = parm.appid;
		CldSapKAccount.APPID = appid;
		CldSapKPub.APPID = appid;
		CldSetting.put("appid", appid + "");
		/**
		 * �豸[ϵͳ]����
		 */
		osType = parm.osType;
		this.prover = CldPackage.getAppVersion();
		this.appver = parm.appver;
		this.mapver = parm.mapver;
		this.isTestVersion = parm.isTestVersion;
		/**
		 * ����·��ֻ�е�һ��ָ����������
		 */
		if (parm.isDefInit) {
			this.appPath = parm.appPath;
		}
		/**
		 * ��ʼ��������
		 */
		this.cid = parm.cid;
		CldSapKAccount.PROVER = this.prover;
		CldSapKAccount.CID = parm.cid;
		
		/**
		 * add by zhaoqy 2017-2-15
		 */
		CldSapKAppCenter.PROVER = this.prover;
		CldSapKAppCenter.CID = this.cid;
		
		CldSapKCombo.PROVER = this.prover;
		CldSapKCombo.CID = this.cid;
	}

	/**
	 * Ϊ�����ʼ��һЩ����
	 * 
	 * @param isTestVersion
	 *            true ���԰� false ��ʽ��
	 * @param appPath
	 *            ����·��
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-8 ����10:46:42
	 */
	public void init(boolean isTestVersion, String appPath) {
		this.appPath = appPath;
		this.isTestVersion = isTestVersion; 
	}

	/**
	 * ����Ϊ����ģʽ
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2014-9-11 ����7:29:49
	 */
	public void setCarMode() {
		setAppid(24);
		setApptype(5);
		setBussinessid(5);
		setOsType(1);
	}

	/**
	 * ����Ϊiphone
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2014-9-17 ����2:40:30
	 */
	public void setIponeMode() {
		setApptype(2);
		setAppid(12);
		setBussinessid(4);
		setOsType(2);
	}

	/**
	 * Gets the apptype.
	 * 
	 * @return the apptype
	 */
	public int getApptype() {
		return apptype;
	}

	/**
	 * Sets the apptype.
	 * 
	 * @param apptype
	 *            the new apptype
	 */
	public void setApptype(int apptype) {
		this.apptype = apptype;
		CldSetting.put("apptype", apptype + "");
	}

	/**
	 * Gets the bussinessid.
	 * 
	 * @return the bussinessid
	 */
	public int getBussinessid() {
		return bussinessid;
	}

	/**
	 * Sets the bussinessid.
	 * 
	 * @param bussinessid
	 *            the new bussinessid
	 */
	public void setBussinessid(int bussinessid) {
		this.bussinessid = bussinessid;
	}

	/**
	 * Gets the appid.
	 * 
	 * @return the appid
	 */
	public int getAppid() {
		return appid;
	}

	/**
	 * Sets the appid.
	 * 
	 * @param appid
	 *            the new appid
	 */
	public void setAppid(int appid) {
		this.appid = appid;
		CldSapKAccount.APPID = appid;
	}

	/**
	 * Gets the os type.
	 * 
	 * @return the os type
	 */
	public int getOsType() {
		return osType;
	}

	/**
	 * Sets the os type.
	 * 
	 * @param osType
	 *            the new os type
	 */
	public void setOsType(int osType) {
		this.osType = osType;
	}

	/**
	 * Gets the module.
	 * 
	 * @return the module
	 */
	public int getModule() {
		return module;
	}

	/**
	 * Sets the module.
	 * 
	 * @param module
	 *            the new module
	 */
	public void setModule(int module) {
		this.module = module;
	}

	/**
	 * ��ȡ����汾�ţ�6.2��
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-4-8 ����4:36:45
	 */
	public String getProver() {
		return prover;
	}

	/**
	 * �����汾��(M1831-D6073-3223J0K)
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-4-8 ����4:36:56
	 */
	public String getAppver() {
		return appver;
	}

	/**
	 * ��ͼ�汾
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-4-8 ����4:37:05
	 */
	public String getMapver() {
		return mapver;
	}

	/**
	 * ���ó���汾��
	 * 
	 * @param prover
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-8 ����4:37:13
	 */
	public void setProver(String prover) {
		this.prover = prover;
	}

	/**
	 * ���õ����汾��
	 * 
	 * @param appver
	 * @return void
	 * @author Zhouls
	 * @date 2015-4-8 ����4:37:27
	 */
	public void setAppver(String appver) {
		this.appver = appver;
	}

	/**
	 * ���õ�ͼ�汾��
	 * 
	 * @param mapver
	 *            the new mapver
	 * @return void
	 * @author Zhouls
	 * @date 2014-10-30 ����5:20:58
	 */
	public void setMapver(String mapver) {
		this.mapver = mapver;
	}

	/**
	 * ��Ļ��
	 * 
	 * @return int
	 * @author Zhouls
	 * @date 2014-10-8 ����9:43:37
	 */
	public int getScreenW() {
		return CldPhoneManager.getScreenWidth();
	}

	/**
	 * ��Ļ��
	 * 
	 * @return int
	 * @author Zhouls
	 * @date 2014-10-8 ����9:43:51
	 */
	public int getScreenH() {
		return CldPhoneManager.getScreenHeight();
	}

	/**
	 * ��Ӫ������
	 * 
	 * @return int
	 * @author Zhouls
	 * @date 2014-10-8 ����9:44:06
	 */
	public int getUimtype() {
		int uimtype = -1;
		String operator = CldPhoneManager.getSimOperator();
		if (null != operator && operator.length() > 0) {
			if (operator.equals("46000") || operator.equals("46002")
					|| operator.equals("46007")) {
				// �й��ƶ�
				uimtype = 4;
			} else if (operator.equals("46003")) {
				// �й�����
				uimtype = 5;
			} else if (operator.equals("46001")) {
				// �й���ͨ
				uimtype = 6;
			}
		} else {
		}
		return uimtype;
	}

	/**
	 * Gets the wifi mac.
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-10-8 ����9:44:17
	 */
	public String getWifiMac() {
		return CldPhoneNet.getMacAddress();
	}

	/**
	 * Gets the blue mac.
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-10-8 ����9:44:32
	 */
	public String getBlueMac() {
		return CldPhoneNet.getBluetoothAddress();
	}

	/**
	 * Gets the imei.
	 * 
	 * @return String
	 * 
	 * @author Zhouls
	 * @date 2014-10-8 ����9:44:50
	 */
	public String getImei() {
		try {
			return CldPhoneManager.getImei();
		} catch (Exception e) {
			return "empty";
		}
	}

	/**
	 * Gets the imsi.
	 * 
	 * @return String
	 * 
	 * @author Zhouls
	 * @date 2014-10-8 ����9:44:50
	 */
	public String getImsi() {
		try {
			return CldPhoneManager.getImsi();
		} catch (Exception e) {
			return "empty";
		}	
	}

	/**
	 * Gets the sN.
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-10-8 ����9:45:00
	 */
	public String getSN() {

		try {
			return CldPhoneManager.getSimSerialNumber();
		} catch (Exception e) {
			return "empty";
		}
	}

	/**
	 * Gets the model.
	 * 
	 * @return String
	 * 
	 * @author Zhouls
	 * @date 2014-10-8 ����9:45:08
	 */
	public String getModel() {
		return CldPhoneManager.getPhoneModel();
	}

	/**
	 * Gets the sDK version.
	 * 
	 * @return String
	 * 
	 * @author Zhouls
	 * @date 2014-10-8 ����9:45:23
	 */
	public String getSDKVersion() {
		return CldPhoneManager.getSDKVersion();
	}

	/**
	 * Gets the oS version.
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-10-8 ����9:45:37
	 */
	public String getOSVersion() {
		return CldPhoneManager.getOSVersion();
	}

	/**
	 * Gets the device code.
	 * 
	 * @return String
	 * @author Zhouls
	 * @date 2014-10-8 ����10:18:57
	 */
	public String getDeviceCode() {
		// String wifiMac = getWifiMac();
		String wifiMac = getImei();
		if (null != wifiMac) {
			return wifiMac;
		} else {
			String imei = getImei();
			if (null != imei) {
				return imei;
			} else {
				String sn = getSN();
				if (null != sn) {
					return sn;
				} else {
					String blueMac = getBlueMac();
					if (null != blueMac) {
						return blueMac;
					}
					return null;
				}
			}
		}
	}

	/**
	 * Gets the device name.
	 * 
	 * @return String
	 * 
	 * @author Zhouls
	 * @date 2014-10-8 ����10:20:50
	 */
	public String getDeviceName() {
		String deviceName = getDeviceCode();
		return deviceName;
	}

	/**
	 * Gets the context.
	 * 
	 * @return Context
	 * 
	 * @author Zhouls
	 * @date 2014-11-5 ����10:22:27
	 */
	public Context getContext() {
		return CldBase.getAppContext();
	}

	/**
	 * Gets the app path.
	 * 
	 * @return the app path
	 */
	public String getAppPath() {
		return appPath;
	}

	/**
	 * Sets the app path.
	 * 
	 * @param appPath
	 *            the new app path
	 */
	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	/**
	 * Checks if is test version.
	 * 
	 * @return true, if is test version
	 */
	public boolean isTestVersion() {
		return isTestVersion;
	}

	/**
	 * Sets the test version.
	 * 
	 * @param isTestVersion
	 *            the new test version
	 */
	public void setTestVersion(boolean isTestVersion) {
		this.isTestVersion = isTestVersion;
	}

	/**
	 * ��ȡ�汾����
	 * 
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-20 ����9:38:14
	 */
	public int getVersionType() {
		if (isTestVersion) {
			return 1;
		} else {
			return 2;
		}
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}
}
