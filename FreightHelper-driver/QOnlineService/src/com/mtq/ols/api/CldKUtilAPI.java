/*
 * @Title CldKUtilAPI.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.ols.api;

import com.mtq.ols.api.CldOlsBase.CldOlsParam;
import com.mtq.ols.bll.CldBllUtil;



/**
 * �����������ģ��,�ṩ���ó���ģʽ�ȹ���
 * 
 * @author Zhouls
 * @date 2015-3-5 ����3:43:48
 */
public class CldKUtilAPI {

	private static CldKUtilAPI cldKUtilAPI;

	private CldKUtilAPI() {

	}

	public static CldKUtilAPI getInstance() {
		if (null == cldKUtilAPI) {
			cldKUtilAPI = new CldKUtilAPI();
		}
		return cldKUtilAPI;
	}

	/**
	 * ��ʼ����������
	 * 
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:43:58
	 */
	public void init(CldOlsParam parm) {
		CldBllUtil.getInstance().init(parm);
	}

	/**
	 * ����ʼ��
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:44:17
	 */
	public void uninit() {

	}

	/**
	 * ���ó���ģʽ
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:44:24
	 */
	public void setCarMode() {
		CldBllUtil.getInstance().setCarMode();
	}

	/**
	 * ����Iphoneģʽ
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:44:34
	 */
	public void setIponeMode() {
		CldBllUtil.getInstance().setIponeMode();
	}

	/**
	 * �����Ƿ�Ϊ����ģʽ
	 * 
	 * @return
	 * @return boolean
	 * @author Zhouls
	 * @date 2015-3-5 ����3:44:43
	 */
	public boolean isCarMode() {
		int apptype = CldBllUtil.getInstance().getApptype();
		if (apptype == 5) {
			return true;
		} else {
			return false;
		}
	}
}
