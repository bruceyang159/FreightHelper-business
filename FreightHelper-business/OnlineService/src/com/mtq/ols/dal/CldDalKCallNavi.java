/*
 * @Title CldDalKCallNavi.java
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
import com.cld.log.CldLog;
import com.cld.setting.CldSetting;
import com.mtq.ols.sap.bean.CldSapKMParm.ShareAKeyCallParm;


/**
 * һ��ͨ���ݲ�
 * 
 * @author Zhouls
 * @date 2014-10-29 ����5:14:01
 */
public class CldDalKCallNavi {

	/** һ��ͨ��Կ */
	private String cldKCallKey;
	/** ��ǰ��¼��kuid ��Ӧ�İ󶨵��ֻ������б� */
	private List<String> mobiles;
	/** һ��ͨ��Ϣ�б� */
	private List<ShareAKeyCallParm> msgList;

	private static CldDalKCallNavi cldDataKCallNavi;

	/**
	 * Gets the single instance of CldDalKCallNavi.
	 * 
	 * @return single instance of CldDalKCallNavi
	 */
	public static CldDalKCallNavi getInstance() {
		if (null == cldDataKCallNavi) {
			cldDataKCallNavi = new CldDalKCallNavi();
		}
		return cldDataKCallNavi;
	}

	/**
	 * Instantiates a new cld dal k call navi.
	 */
	private CldDalKCallNavi() {
		cldKCallKey = "";
		mobiles = new ArrayList<String>();
		msgList = new ArrayList<ShareAKeyCallParm>();
	}

	/**
	 * Uninit.
	 */
	public void uninit() {
		mobiles = new ArrayList<String>();
		msgList = new ArrayList<ShareAKeyCallParm>();
	}

	/**
	 * Gets the mobiles.
	 * 
	 * @return the mobiles
	 */
	public List<String> getMobiles() {
		if (mobiles.size() > 0) {
			// ��¼��ȡ���ֻ���OK
			return mobiles;
		} else {
			String StrMobile = CldSetting.getString("ols_AKeyCall_mobiles");
			if (!TextUtils.isEmpty(StrMobile)) {
				// ����˽�������к���ֱ�Ӷ�ȡ
				String[] strTemp = StrMobile.split(",");
				mobiles = new ArrayList<String>();
				for (int i = 0; i < strTemp.length; i++) {
					mobiles.add(strTemp[i]);
				}
			}
			return mobiles;
		}
	}

	/**
	 * Sets the mobiles.
	 * 
	 * @param mobiles
	 *            the new mobiles
	 */
	public void setMobiles(List<String> mobiles) {
		this.mobiles = mobiles;
		String strMobile = "";
		for (int i = 0; i < mobiles.size(); i++) {
			strMobile += mobiles.get(i);
			if (i + 1 != mobiles.size()) {
				strMobile += ",";
			}
		}
		CldLog.e("ols", strMobile);
		CldSetting.put("ols_AKeyCall_mobiles", strMobile);
	}

	/**
	 * Gets the msg list.
	 * 
	 * @return the msg list
	 */
	public List<ShareAKeyCallParm> getMsgList() {
		return msgList;
	}

	/**
	 * Sets the msg list.
	 * 
	 * @param msgList
	 *            the new msg list
	 */
	public void setMsgList(List<ShareAKeyCallParm> msgList) {
		if (null != msgList) {
			this.msgList.clear();
			this.msgList.addAll(msgList);
		}
	}

	/** @return the cldKCallKey */
	public String getCldKCallKey() {
		if (!TextUtils.isEmpty(cldKCallKey)) {
			return cldKCallKey;
		} else {
			return CldSetting.getString("CldKCallKey");
		}
	}

	/**
	 * @param cldKCallKey
	 *            the cldKCallKey to set
	 */
	public void setCldKCallKey(String cldKCallKey) {
		this.cldKCallKey = cldKCallKey;
		CldSetting.put("CldKCallKey", cldKCallKey);
	}
}
