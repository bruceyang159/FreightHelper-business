/*
 * @Title CldKDeviceAPI.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-12-15 ����7:04:30
 * @version 1.0
 */
package com.mtq.ols.module.delivery.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.SystemClock;

import com.mtq.ols.dal.CldDalKAccount;


/**
 * �豸��Ϣ�ӿ�
 * 
 * @author Zhouls
 * @date 2015-12-15 ����7:04:30
 */
public class CldKDeviceAPI {
	/**
	 * ��ȡduid
	 * 
	 * @return
	 * @return long
	 * @author Zhouls
	 * @date 2015-12-15 ����7:05:16
	 */
	public static long getDuid() {
		return CldDalDevice.getInstance().getDuid();
	}

	/**
	 * ��ȡ������ʱ��
	 * 
	 * @return ��ǰ������ʱ�� long
	 * @author Zhouls
	 * @date 2014-9-19 ����10:28:30
	 */
	public static long getSvrTime() {
		return SystemClock.elapsedRealtime() / 1000
				- CldDalKAccount.getInstance().getTimeDif();
//		return CldPubFuction.getLocalTime()
//				- CldDalDevice.getInstance().getTimeDif();
	}

	/**
	 * �����ַ�ʱ��
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-10-14 ����9:25:40
	 */
	public static String getStrSvrTime() {
		long utc = CldKDeviceAPI.getSvrTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(new Date(utc * 1000));
		return date;
	}
}
