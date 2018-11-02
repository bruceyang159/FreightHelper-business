/*
 * @Title CldInterfaceType.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-10-27 ����2:24:51
 * @version 1.0
 */
package com.mtq.ols.tools.model;

import java.util.HashMap;

import android.text.TextUtils;

import com.cld.log.CldLog;
import com.cld.net.CldHttpClient;

/**
 * �ӿ�cancel������
 * 
 * @author Zhouls
 * @date 2015-10-27 ����2:24:51
 */
public class CldOlsInterfaceManager {

	/** �洢����ӿ�����Ψһ�ӿ���-urlMd5У���� */
	private HashMap<String, String> mapOfInterface;

	/**
	 * ���ⲿ�ӿ�ֵput���
	 * 
	 * @param key
	 *            �ӿڶ�Ӧ����ϵ�ӱ��
	 * @param value
	 *            url md5ֵ
	 * @return void
	 * @author Zhouls
	 * @date 2015-10-27 ����2:35:10
	 */
	public void putKeyValue(int key, String value) {
		if (null == mapOfInterface) {
			CldLog.e("ols_CldOlsInterface", "mapOfInterface is null!");
			return;
		}
		if (TextUtils.isEmpty(key + "") || TextUtils.isEmpty(value)) {
			CldLog.e("ols_CldOlsInterface", "put null key or null value!");
		} else {
			mapOfInterface.put(key + "", value);
		}
	}

	/**
	 * ȡ����Ӧ������
	 * 
	 * @param type
	 *            CldOlsInterfaceType
	 * @return void
	 * @author Zhouls
	 * @date 2015-10-27 ����7:10:49
	 */
	public void cancel(int type) {
		String md5Url = getValue(type);
		if (!TextUtils.isEmpty(md5Url)) {
			CldHttpClient.cancelRequest(md5Url);
		} else {
			CldLog.e("ols_CldOlsInterface", "no key !");
		}
	}

	/**
	 * ȡ��Ӧ��ֵ��Ӧ��url
	 * 
	 * @param type
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-10-27 ����2:37:44
	 */
	private String getValue(int type) {
		String key = type + "";
		if (null == mapOfInterface) {
			return null;
		}
		if (mapOfInterface.containsKey(key)) {
			return mapOfInterface.get(key);
		} else {
			return null;
		}
	}

	/**
	 * �ӿ�����
	 * 
	 * @author Zhouls
	 * @date 2015-10-27 ����6:47:15
	 */
	public static class CldOlsInterfaceType {
		/**
		 * 
		 * �ʻ��½ӿ�
		 * 
		 * @author Zhouls
		 * @date 2015-10-27 ����6:49:27
		 */
		public static class KAccount {
			public static final int FAST_LOGIN = 10001;

		}

		/**
		 * 
		 * ��������
		 * 
		 * @author Zhouls
		 * @date 2015-10-27 ����6:49:41
		 */
		public static class KSearch {
			public static final int geoCode = 20001;
			public static final int reverseGeoCode = geoCode + 1;
			public static final int requestPoiSuggestion = reverseGeoCode + 1;
			public static final int requestBusSuggestion = requestPoiSuggestion + 1;
			public static final int searchInBounds = requestBusSuggestion + 1;
			public static final int searchInCity = searchInBounds + 1;
			public static final int searchNearby = searchInCity + 1;
			public static final int searchInCityByBounds = searchNearby + 1;
			public static final int searchByKCode = searchInCityByBounds + 1;
			public static final int searchJourney = searchByKCode + 1;
			public static final int searchBusLine = searchJourney + 1;
			public static final int searchPoiDetail = searchBusLine + 1;
			public static final int searchBusLineDetail = searchPoiDetail + 1;
			public static final int requestHotQuery = searchBusLineDetail + 1;
		}
	}

	private static CldOlsInterfaceManager cldOlsInterface;

	private CldOlsInterfaceManager() {
		mapOfInterface = new HashMap<String, String>();
	}

	public static CldOlsInterfaceManager getInstance() {
		if (null == cldOlsInterface) {
			cldOlsInterface = new CldOlsInterfaceManager();
		}
		return cldOlsInterface;
	}
}
