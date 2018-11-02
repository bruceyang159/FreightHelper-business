/*
 * @Title CldSapToolBox.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.ols.sap;

import com.mtq.ols.dal.CldDalKAccount;

import android.text.TextUtils;



/**
 * 
 * ����������
 * 
 * @author Zhouls
 * @date 2015-3-25 ����5:45:10
 */
public class CldSapToolBox {
	
	/** ���ݷ���. */
	public static final String DRIVING = "driving";
	/** ��������. */
	public static final String CAR_EVALUATE = "car_evaluate";
	/** ���ռ�����. */
	public static final String INSURE_CALCULATOR = "insure_calculator";
	/** Υ�²�ѯ. */
	public static final String VIOLATION = "violation";

	/**
	 * ��ȡ������url(900)
	 * 
	 * @param tool
	 *            ���߱��루drivingΪ���ݷ���car_evaluateΪ����������insure_calculatorΪ���ռ�������
	 *            violationΪΥ�²�ѯ)
	 * @param business
	 *            ҵ����CM ��1
	 * @param type
	 *            �ն���Դ��1ΪCM��2ΪiPhone��
	 * @return String ����url
	 * @author huagx
	 * @date 2014-8-19 ����6:16:26
	 */
	public static String getToolBoxURL(String tool, int business, String type) {
		String dns = CldSapUtil.getNaviSvrWS();
		if (!TextUtils.isEmpty(dns) && !TextUtils.isEmpty(tool)
				&& !TextUtils.isEmpty(type)) {
			String url = dns + "toolbox/go.php?tool=" + tool + "&type=" + type
					+ "&business=" + business;
			long kuid = CldDalKAccount.getInstance().getKuid();
			String session = CldDalKAccount.getInstance().getSession();
			if (0 != kuid) {
				url += "&userid" + kuid;
			}
			if (!TextUtils.isEmpty(session)) {
				url += "&session" + session;
			}
			return url;
		}
		return null;
	}
}
