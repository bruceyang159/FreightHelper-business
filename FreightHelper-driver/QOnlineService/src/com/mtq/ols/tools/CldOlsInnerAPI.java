/*
 * @Title CldOlsInnerAPI.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-12-11 ����4:45:34
 * @version 1.0
 */
package com.mtq.ols.tools;

/**
 * ģ���ڲ�����ӿ�
 * 
 * @author Zhouls
 * @date 2015-12-11 ����4:45:34
 */
public class CldOlsInnerAPI {

	private ICldOlsInnerListener listener;

	public void setListener(ICldOlsInnerListener listener) {
		if (null != listener) {
			this.listener = listener;
		}
	}

	/**
	 * �����������
	 * 
	 * @param getURL
	 *            url
	 * @param postJson
	 *            postjson
	 * @param returnJson
	 *            webreturn
	 * @param fuctionName
	 *            fuctionName ������
	 * @return void
	 * @author Zhouls
	 * @date 2015-12-11 ����4:54:12
	 */
	public void dealInOut(String getURL, String postJson, String fuctionName,
			int apiNum, Object parm, Object result) {
		if (null != listener) {
			listener.dealInOut(getURL, postJson, fuctionName, apiNum, parm,
					result);
		}
	}

	/**
	 * 
	 * �ӿ��ڲ�����ص�
	 * 
	 * @author Zhouls
	 * @date 2015-12-11 ����4:48:36
	 */
	public static interface ICldOlsInnerListener {

		/**
		 * ��������
		 * 
		 * @param getURL
		 *            url
		 * @param postJson
		 *            post json
		 * @param fuctionName
		 *            ������
		 * @param apiNum
		 *            �ӿڱ��
		 * @param parm
		 *            �������
		 * @param result
		 *            ������
		 * @return void
		 * @author Zhouls
		 * @date 2015-12-14 ����3:33:42
		 */
		public void dealInOut(String getURL, String postJson,
				String fuctionName, int apiNum, Object parm, Object result);
	}

	private static CldOlsInnerAPI cldOlsInnerAPI;

	public static CldOlsInnerAPI getInstance() {
		if (null == cldOlsInnerAPI) {
			cldOlsInnerAPI = new CldOlsInnerAPI();
		}
		return cldOlsInnerAPI;
	}

	private CldOlsInnerAPI() {

	}
}
