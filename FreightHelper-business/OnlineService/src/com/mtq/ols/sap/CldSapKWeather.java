/*
 * @Title CldSapKWeather.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-7-8 ����8:36:04
 * @version 1.0
 */
package com.mtq.ols.sap;

import java.util.HashMap;
import java.util.Map;

import com.mtq.ols.api.CldOlsBase;
import com.mtq.ols.sap.parse.CldKBaseParse;
import com.mtq.ols.tools.CldSapReturn;

/**
 * �����ӿ����
 * 
 * @author Zhouls
 * @date 2015-7-8 ����8:36:04
 */
public class CldSapKWeather {
	/** �״�����. */
	public static String keyCode = "";
	/** The APIVER. */
	private final static int APIVER = 1;
	/** The RSCHARSET. */
	private final static int RSCHARSET = 1;
	/** The RSFORMAT. */
	private final static int RSFORMAT = 1;

	/**
	 * ��ʼ��key
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-6-11 ����3:49:55
	 */
	public static void initKey() {
		if (CldOlsBase.getInstance().isTestVersion()) {
			keyCode = "1a86fb49b070f26d7948d7931ed69233";
		} else {
			keyCode = "3578ff7e621432719238730de417fa76";
		}
	}

	/**
	 * ��ȡʵʱ��������
	 * 
	 * @param regioncode
	 *            ����ID
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-7-8 ����8:48:45
	 */
	public static CldSapReturn observeWeather(int regioncode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regioncode", regioncode);
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		CldSapReturn errRes = CldKBaseParse
				.getGetParms(map, CldSapUtil.getNaviSvrWeather()
						+ "weather_observe.php", keyCode);
		return errRes;
	}
	
	/** ��ȡ����Ԥ������
	 * @param regioncode
	 * @return
	 * @return CldSapReturn
	 * @author buxc
	 * @date 2015��11��2�� ����2:37:09
	 */ 
	public static CldSapReturn alarmWerther(int regioncode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regioncode", regioncode);
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		CldSapReturn errRes = CldKBaseParse
				.getGetParms(map, CldSapUtil.getNaviSvrWeather()
						+ "weather_alarm.php", keyCode);
		return errRes;
	}
}
