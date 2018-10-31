/*
 * @Title CldOlsUtcRecoder.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2016-1-19 ����11:24:42
 * @version 1.0
 */
package com.mtq.ols.tools.model;

import com.cld.log.CldLog;

/**
 * ��ʱ��¼
 * 
 * @author Zhouls
 * @date 2016-1-19 ����11:24:42
 */
public class CldOlsUtcRecoder {

	private CldOlsUtcRecoder() {

	}

	private static CldOlsUtcRecoder innerRecoder;

	public static CldOlsUtcRecoder getInstance() {
		if (null == innerRecoder) {
			innerRecoder = new CldOlsUtcRecoder();
		}
		return innerRecoder;
	}

	private long utc = 0;

	/**
	 * ��ʼ��¼
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2016-1-19 ����11:13:13
	 */
	public void start() {
		utc = System.currentTimeMillis();
	}

	/**
	 * ������¼
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2016-1-19 ����11:13:38
	 */
	public void end() {
		if (utc == 0) {
			CldLog.w("ols_utc", "Have you called start before end?");
		} else {
			utc = System.currentTimeMillis() - utc;
			if (utc < 0) {
				CldLog.i("ols_utc", "use:" + utc + "ms");
			} else if (utc < 150) {
				CldLog.d("ols_utc", "use:" + utc + "ms");
			} else if (utc < 300) {
				CldLog.w("ols_utc", "use:" + utc + "ms");
			} else {
				CldLog.w("ols_utc", "use:" + utc + "ms");
			}
			// ��־������ɺ�����
			utc = 0;
		}
	}

}
