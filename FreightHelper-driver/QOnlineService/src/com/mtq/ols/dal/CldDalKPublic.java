/*
 * @Title CldDalKPublic.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-4-8 ����9:55:21
 * @version 1.0
 */
package com.mtq.ols.dal;

import android.text.TextUtils;

import com.cld.setting.CldSetting;

/**
 * ��ɢ�ӿ����ݲ�
 * 
 * @author Zhouls
 * @date 2015-4-8 ����9:55:21
 */
public class CldDalKPublic {

	/** ��ɢ�ӿ���Կ */
	private String cldKPubKey;

	private static CldDalKPublic cldDalKPublic;

	public static CldDalKPublic getInstance() {
		if (null == cldDalKPublic) {
			cldDalKPublic = new CldDalKPublic();
		}
		return cldDalKPublic;
	}

	private CldDalKPublic() {

	}

	/** @return the cldKPubKey */
	public String getCldKPubKey() {
		if (!TextUtils.isEmpty(cldKPubKey)) {
			return cldKPubKey;
		}
		return CldSetting.getString("ols_CldKPUBKey");
	}

	/**
	 * @param cldKPubKey
	 *            the cldKPubKey to set
	 */
	public void setCldKPubKey(String cldKPubKey) {
		this.cldKPubKey = cldKPubKey;
		CldSetting.put("ols_CldKPUBKey", cldKPubKey);
	}

}
