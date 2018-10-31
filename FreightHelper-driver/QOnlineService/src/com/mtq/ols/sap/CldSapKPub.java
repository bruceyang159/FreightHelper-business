/*
 * @Title CldSapKPub.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @author Zhouls
 * @date 2015-3-4 ����10:52:09
 * @version 1.0
 */
package com.mtq.ols.sap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.mtq.ols.api.CldKServiceAPI;
import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.bll.CldKAccount;
import com.mtq.ols.dal.CldDalKAccount;
import com.mtq.ols.module.delivery.tool.CldKDeviceAPI;
import com.mtq.ols.sap.parse.CldKBaseParse;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;

/**
 * ��ɢ�ӿ�
 * 
 * @author Zhouls
 * @date 2015-3-4 ����10:52:09
 */
public class CldSapKPub {
	/** �״�����. */
	public static String keyCode;
	/** �����������. */
	private static final int APIVER = 1;
	/** The APPID. */
	public static int APPID;
	/** The RSCHARSET. */
	private static final int RSCHARSET = 1;
	/** The RSFORMAT. */
	private static final int RSFORMAT = 1;
	/** The UMSAVER. */
	private static final int UMSAVER = 2;

	/**
	 * ��ȡ��ɢ�ӿ���Կ(700)
	 * 
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2014-8-13 ����1:08:04
	 */
	public static CldSapReturn initKeyCode() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		String key;
		if (CldBllUtil.getInstance().isTestVersion()) {
			// ���԰汾
			key = "BBA9B3328AD5D4DBEF5C756D3DBF335D";
		} else {
			// ��ʽ�汾
			key = "BBA9B3328AD5D4DBEF5C756D3DBF335D";
		}
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrPub() + "php/pub_get_code.php", key);
		return errRes;
	}

}
