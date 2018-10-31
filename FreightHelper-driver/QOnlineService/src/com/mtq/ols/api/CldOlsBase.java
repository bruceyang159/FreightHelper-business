/*
 * @Title CldOlsBase.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.ols.api;

import android.os.Environment;

import com.cld.base.CldBase;
import com.cld.log.CldLog;
import com.cld.net.CldHttpClient;
import com.cld.setting.CldSetting;
import com.cld.utils.CldPackage;
import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.tools.model.CldOlsInitManager;
import com.mtq.ols.tools.model.CldOlsInitMod.ICldOlsModelInterface;

/**
 * ����ϵͳ����
 * 
 * @author Zhouls
 * @date 2015-3-5 ����3:44:55
 */
public class CldOlsBase {

	private static CldOlsBase cldOlsBase;
	private CldOlsInit cldOlsInit;

	private boolean isTestVersion;

	public boolean isTestVersion() {
		return isTestVersion; 
	}

	public void setTestVersion(boolean isTestVersion) {
		this.isTestVersion = isTestVersion; 
	}
	

	public static CldOlsBase getInstance() {
		if (null == cldOlsBase) {
			cldOlsBase = new CldOlsBase();
		}
		return cldOlsBase;
	}

	private CldOlsBase() {
		cldOlsInit = new CldOlsInit();
	}

	/**
	 * 
	 * ��ʼ���ص�
	 * 
	 * @author Zhouls
	 * @date 2015-3-20 ����8:53:59
	 */
	public interface IInitListener {
		/**
		 * ���ø��»ص�
		 * 
		 * @return void
		 * @author Zhouls
		 * @date 2015-7-30 ����10:59:13
		 */
		public void onUpdateConfig();

		/**
		 * ��ʼ��duid�ص�
		 * 
		 * @return void
		 * @author Zhouls
		 * @date 2015-7-30 ����10:59:39
		 */
		public void onInitDuid();
	}

	/**
	 * Olsģ���ʼ������
	 * 
	 * @param param
	 *            ��ʼ��������
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:45:04
	 */
	public void init(CldOlsParam param, IInitListener listener) {
		/**
		 * Ĭ������ʽ��,CMһ�ײ���
		 */
		CldKUtilAPI.getInstance().init(param);
		setTestVersion(false); 
		versionChanged(param.isDefInit);
		CldKConfigAPI.getInstance().initDefConfig();
		initEx(param);
		if (!param.isDefInit) {
			initBaseCondition(listener);
		}
		// ������ʼ������
		CldOlsInitManager.getInstance().init();
	}
	
	private void initEx(final CldOlsParam param) {
		CldHttpClient.init(CldBase.getAppContext());
	}

	/**
	 * ����ʼ��
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:45:23
	 */
	public void uninit() {

	}

	/**
	 * ��ʼ�����߷����������
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:45:32
	 */
	public void initBaseCondition(IInitListener listener) {
		cldOlsInit.init(listener);
	}

	/**
	 * ���õ�������·��
	 * 
	 * @param appPath
	 *            ����������·��
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:45:41
	 */
	public void setDebugMode(String appPath) {
		CldBllUtil.getInstance().setAppPath(appPath);
	}

	/**
	 * ��ʼ��������
	 * 
	 * @author Zhouls
	 * @date 2015-3-5 ����3:45:52
	 */
	public static class CldOlsParam {

		/** �����汾��(M1831-D6073-3223J0K) */
		public String appver = "";
		/** ��ͼ�汾 */
		public String mapver = "";
		/** true ���԰汾 false ��ʽ�汾 */
		public boolean isTestVersion = false;
		/** �Ƿ���Ĭ��ֵ��ʼ�� */
		public boolean isDefInit = true;
		/** Ӧ�ó������� */
		public int apptype = 1;
		/** ҵ��Id */
		public int bussinessid = 1;
		/** Ӧ������ */
		public int appid = 9;
		/** ����ϵͳ���� */
		public int osType = 1;
		/** ���������� */
		public int cid = 1;
		/** ����·����Ĭ��Ϊ��Ŀ¼ */
		public String appPath = Environment.getExternalStorageDirectory() + "";
	}
	
	/**
	 * �汾�仯�������
	 * 
	 * @param isDefInit
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-29 ����11:01:43
	 */
	private void versionChanged(boolean isDefInit) {
		if (isDefInit) {
			/**
			 * �汾�����������
			 */
			int verCode = CldSetting.getInt("ols_vercode", 0);
			CldLog.d("ols_ver", "lastVer:" + verCode + "," + "curVer:"
					+ CldPackage.getAppVersionCode());
			if (verCode != CldPackage.getAppVersionCode()) {
				CldLog.e("ols_ver", "update new ver clean");
				CldSetting.remove("1001001000");
				CldSetting.remove("1001001100");
				CldSetting.remove("1001003000");
				CldSetting.remove("1003001200");
				CldSetting.remove("2002001000");
				CldSetting.remove("2004001000");
			}
			CldSetting.put("ols_vercode", CldPackage.getAppVersionCode());
		}
	}
}
