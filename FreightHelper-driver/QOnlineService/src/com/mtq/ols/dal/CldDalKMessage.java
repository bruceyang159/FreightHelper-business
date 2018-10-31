/*
 * @Title CldDalKMessage.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.ols.dal;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.cld.setting.CldSetting;
import com.mtq.ols.sap.bean.CldSapKMParm.ShareAKeyCallParm.CldAreaEgg;


/**
 * ��Ϣϵͳ���ݴ洢
 * 
 * @author Zhouls
 * @date 2014-12-18 ����11:12:18
 */
public class CldDalKMessage {

	/** ��Ϣϵͳ��Կ */
	private String cldKMKey;
	/** ��Ϣ��ʷ���ֵ */
	private int maxlength;
	/** ����ʵ��б� */
	private volatile List<CldAreaEgg> listEggs;

	private static CldDalKMessage cldDalKMessage;

	/**
	 * Gets the single instance of CldDalKMessage.
	 * 
	 * @return single instance of CldDalKMessage
	 */
	public static CldDalKMessage getInstance() {
		if (null == cldDalKMessage) {
			cldDalKMessage = new CldDalKMessage();
		}
		return cldDalKMessage;
	}

	/**
	 * Instantiates a new cld dal k message.
	 */
	private CldDalKMessage() {
		listEggs = new ArrayList<CldAreaEgg>();
		maxlength = 0;
	}

	/**
	 * Gets the list eggs.
	 * 
	 * @return the list eggs
	 */
	public synchronized List<CldAreaEgg> getListEggs() {
		return listEggs;
	}

	/**
	 * Sets the list eggs.
	 * 
	 * @param listEggs
	 *            the new list eggs
	 */
	public synchronized void setListEggs(List<CldAreaEgg> listEggs) {
		if (null != this.listEggs) {
			this.listEggs.clear();
			this.listEggs.addAll(listEggs);
		}
	}

	public int getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}

	/** @return the cldKMKey */
	public String getCldKMKey() {
		if (!TextUtils.isEmpty(cldKMKey)) {
			return cldKMKey;
		} else {
			return CldSetting.getString("CldKMKey");
		}
	}

	/**
	 * @param cldKMKey
	 *            the cldKMKey to set
	 */
	public void setCldKMKey(String cldKMKey) {
		this.cldKMKey = cldKMKey;
		CldSetting.put("CldKMKey", cldKMKey);
	}

}
