/*
 * @Title CldKAuthcheck.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:59
 * @version 1.0
 */
package com.mtq.ols.bll;

import com.cld.device.CldPhoneNet;
import com.cld.log.CldLog;
import com.mtq.ols.sap.CldSapKAuthcheck;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtBase;
import com.mtq.ols.tools.CldErrUtil;
import com.mtq.ols.tools.CldSapNetUtil;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;



/**
 * 
 * ��Ȩ
 * 
 * @author Zhouls
 * @date 2015-4-8 ����4:24:20
 */
public class CldKAuthcheck {

	private static CldKAuthcheck cldKAuthcheck;

	/**
	 * Instantiates a new cld k authcheck.
	 */
	private CldKAuthcheck() {
	}

	/**
	 * Gets the single instance of CldKAuthcheck.
	 * 
	 * @return single instance of CldKAuthcheck
	 */
	public static CldKAuthcheck getInstance() {
		if (cldKAuthcheck == null)
			cldKAuthcheck = new CldKAuthcheck();
		return cldKAuthcheck;
	}

	/**
	 * ��Ȩ
	 * 
	 * @param key
	 *            ������Կ
	 * @param safeCode
	 *            ��ȫ��
	 * @return
	 * @return CldSapReturn
	 * @author Zhouls
	 * @date 2015-4-8 ����4:24:07
	 */
	public CldSapReturn authCheck(String key, String safeCode) {
		CldSapReturn errRes = new CldSapReturn();
		if (CldPhoneNet.isNetConnected()) {
			errRes = CldSapKAuthcheck.authCheck(key, safeCode);
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
			CldErrUtil.handleErr(errRes);
			CldLog.d("ols", errRes.errCode + "_authcheck");
			CldLog.d("ols", errRes.errMsg + "_authcheck");
		}
		return errRes;
	}
}
