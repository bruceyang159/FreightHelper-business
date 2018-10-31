/*
 * @Title CldSapKAuthcheck.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.ols.sap;

import java.util.HashMap;
import java.util.Map;

import com.mtq.ols.sap.parse.CldKBaseParse;
import com.mtq.ols.tools.CldSapReturn;


/**
 * KEY��ȨЭ��ӿ�
 * 
 * @author Zhouls
 * @date 2014-12-9 ����5:50:22
 */
public class CldSapKAuthcheck {

	/**
	 * KEY��Ȩ(501)
	 * 
	 * @param key
	 *            ������Կ
	 * @param safe_code
	 *            ��ȫ��
	 * @return 0Ϊ�ɹ���1Ϊ�������ݸ�ʽ���Ϸ���2Ϊ��ȫ���������Կ��ƥ�䣬 3Ϊ������Կ�����ڻ���ɾ����4Ϊ�û�δ��ˣ�5Ϊ��ȫ����Ȩ�޷��ʣ�
	 *         6Ϊ���ô������� ��100Ϊϵͳ���� int
	 * @author Zhouls
	 * @date 2014-12-9 ����5:57:56
	 */
	public static CldSapReturn authCheck(String key, String safe_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);
		map.put("safe_code", safe_code);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrAC() + "console/api/authcheck_mobile.php",
				null);
		return errRes;
	}
}
