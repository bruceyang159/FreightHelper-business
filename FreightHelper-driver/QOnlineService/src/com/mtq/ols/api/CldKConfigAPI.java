/*
 * @Title CldKConfigAPI.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.ols.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mtq.ols.api.CldOlsBase.IInitListener;
import com.mtq.ols.bll.CldKConfig;
import com.mtq.ols.bll.CldKConfig.ConfigDomainType;



/**
 * �ն˿������ģ�飬�ṩ�������õȹ���
 * 
 * @author Zhouls
 * @date 2015-3-5 ����3:23:42
 */
public class CldKConfigAPI {

	private static CldKConfigAPI cldKConfigAPI;

	private CldKConfigAPI() {

	}

	public static CldKConfigAPI getInstance() {
		if (null == cldKConfigAPI) {
			cldKConfigAPI = new CldKConfigAPI();
		}
		return cldKConfigAPI;
	}

	/**
	 * ��ʼ��Ĭ��,��������
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:24:18
	 */
	public void initDefConfig() {
		CldKConfig.getInstance().initDefConfig();
	}

	/**
	 * ��������
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:24:28
	 */
	public void updateCofig(final IInitListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean isUpdate = CldKConfig.getInstance().updateCofig();
				if (null != listener && isUpdate) {
					listener.onUpdateConfig();
				}
			}
		}).start();
	}

	/**
	 * ��ȡ����ͷ
	 * 
	 * @param type
	 *            CofigDomainType
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-5 ����3:24:43
	 */
	public String getSvrDomain(int type) {
		return CldKConfig.getInstance().getSvrDomain(type);
	}

	/**
	 * ��ȡ���񿪹�
	 * 
	 * @param type
	 *            ConfigSwitchType
	 * @return 0�أ�1��
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-5 ����3:24:58
	 */
	public int getSvrSwitch(int type) {
		return CldKConfig.getInstance().getSvrSwitch(type);
	}

	/**
	 * ��ȡWebUrl
	 * 
	 * @param type
	 *            ConfigWebUrlType
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-5 ����3:25:20
	 */
	public String getWebUrl(int type) {
		return CldKConfig.getInstance().getWebUrl(type);
	}

	/**
	 * ��ȡ�����Ƶ�����
	 * 
	 * @param type
	 *            ConfigRateType
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-5 ����3:25:34
	 */
	public int getSvrRate(int type) {
		return CldKConfig.getInstance().getSvrRate(type);
	}

	/**
	 * �Ƿ��ǰ������û�
	 * 
	 * @param kuid
	 *            the kuid
	 * @return
	 * @return boolean
	 * @author Zhouls
	 * @date 2015-3-5 ����3:25:52
	 */
	public boolean isInWhiteList(long kuid) {
		return CldKConfig.getInstance().isInWhiteList(kuid);
	}

	/**
	 * �Ƿ����ֻ���
	 * 
	 * @param phone
	 *            �����ֻ���
	 * @return
	 * @return boolean
	 * @author Zhouls
	 * @date 2015-3-5 ����3:26:07
	 */
	public boolean isPhoneNum(String phone) {
		Pattern p = Pattern.compile(CldKConfig.getInstance().getSvrDomain(
				ConfigDomainType.REG_EXPRESS));
		Matcher m = p.matcher(phone);
		return m.matches();
	}
}
