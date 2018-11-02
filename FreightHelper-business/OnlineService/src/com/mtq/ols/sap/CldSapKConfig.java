/*
 * @Title CldSapKConfig.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.ols.sap;

import java.util.HashMap;
import java.util.Map;
import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.sap.parse.CldKBaseParse;
import com.mtq.ols.tools.CldSapReturn;


/**
 * �ն�����Э��ӿ�
 * 
 * @author Zhouls
 * @date 2014-10-9 ����3:36:31
 */
public class CldSapKConfig {

	/** �����������. */
	private static final int RSCHARSET = 1;
	/** The RSFORMAT. */
	private static final int RSFORMAT = 1;

	/**
	 * ��ȡ�����б�(100)
	 * 
	 * @param classtypes
	 *            the classtypes
	 * @return errCode (0 �и��£�110�޸���) int
	 * @author Zhouls
	 * @date 2014-10-9 ����4:01:53
	 */
	public static CldSapReturn downloadControlList(String classtypes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classtypes", classtypes);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		String key;
		if (CldBllUtil.getInstance().isTestVersion()) {
			// ���԰汾
			key = "D20B600B4C2060EBC21A97AB5557912A";
		} else {
			// ��ʽ�汾
			key = "B9720F0D8E5CBCAFC5B6CF409E01C1ED";
		}
		CldSapReturn errRes = CldKBaseParse
				.getGetParms(map, CldSapUtil.getNaviSvrKConfig()
						+ "tc/control_download.php", key);
		return errRes;
	}
}
