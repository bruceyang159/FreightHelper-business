/*
 * @Title CldSapKCallNavi.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.ols.sap;

import java.util.HashMap;
import java.util.Map;

import com.mtq.ols.sap.parse.CldKBaseParse;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;


/**
 * һ��ͨЭ���ӿ�
 * 
 * @author Zhouls
 * @date 2014-10-29 ����4:41:07
 */
public class CldSapKCallNavi {

	/** �״����� */
	public static String keyCode;
	/** �����������. */
	private static final int APIVER = 1;
	/** The RSCHARSET. */
	private static final int RSCHARSET = 1;
	/** The RSFORMAT. */
	private static final int RSFORMAT = 1;
	/** The UMSAVER. */
	private static final int UMSAVER = 1;

	/**
	 * ��ʼ��һ��ͨϵͳ��Կ(401)
	 * 
	 * @return int
	 * @author Zhouls
	 * @date 2014-9-22 ����9:12:45
	 */
	public static CldSapReturn initKeyCode() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		String key = "ADE8E2743BD4E4EDDF0C060A8FFC524C";
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrPPT() + "kptt_get_code.php", key);
		return errRes;
	}

	/**
	 * ��ȡһ��ͨ��֤�루402��
	 * 
	 * @param apptype
	 *            Ӧ�����ͣ�1��CM��2��IOS��
	 * @param prover
	 *            �汾��
	 * @param kuid
	 *            �������ʺ�id
	 * @param session
	 *            Session
	 * @param appid
	 *            appid
	 * @param bussinessid
	 *            bussinessid
	 * @param mobile
	 *            �ֻ�����
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-12 ����2:58:57
	 */
	public static CldSapReturn getIdentifycode(int apptype, String prover,
			long kuid, String session, int appid, int bussinessid, String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("apptype", apptype);
		map.put("prover", prover);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("appid", appid);
		map.put("bussinessid", bussinessid);
		map.put("mobile", mobile);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrPPT() + "kptt_get_identifycode.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ��ȡ�������ʺŰ󶨵��ֻ������б�(403)
	 * 
	 * @param apptype
	 *            Ӧ�����ͣ�1��CM��2��IOS��
	 * @param prover
	 *            �汾��
	 * @param kuid
	 *            �������ʺ�id
	 * @param session
	 *            Session
	 * @param appid
	 *            appid
	 * @param bussinessid
	 *            bussinessid
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-12 ����3:04:59
	 */
	public static CldSapReturn getBindMobile(int apptype, String prover,
			long kuid, String session, int appid, int bussinessid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("apptype", apptype);
		map.put("prover", prover);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("appid", appid);
		map.put("bussinessid", bussinessid);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrPPT() + "kptt_get_bind_mobile.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * �������ֻ���(404)
	 * 
	 * @param apptype
	 *            Ӧ�����ͣ�1��CM��2��IOS��
	 * @param prover
	 *            �汾��
	 * @param kuid
	 *            �������ʺ�id
	 * @param session
	 *            Session
	 * @param appid
	 *            appid
	 * @param bussinessid
	 *            bussinessid
	 * @param identifycode
	 *            the identifycode
	 * @param mobile
	 *            �ֻ�����
	 * @param replacemobile
	 *            �滻�ɺ��� ��Ϊ�����滻�󶨵ľɺ��룻Ϊ���������°󶨺���
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-12 ����3:02:58
	 */
	public static CldSapReturn bindToMobile(int apptype, String prover,
			long kuid, String session, int appid, int bussinessid,
			String identifycode, String mobile, String replacemobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("apptype", apptype);
		map.put("prover", prover);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("appid", appid);
		map.put("bussinessid", bussinessid);
		map.put("identifycode", identifycode);
		map.put("mobile", mobile);
		CldSapParser.putStringToMap(map, "replacemobile", replacemobile);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrPPT() + "kptt_bind_mobile.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ɾ�����ֻ���(405).
	 * 
	 * @param apptype
	 *            Ӧ�����ͣ�1��CM��2��IOS��
	 * @param prover
	 *            �汾��
	 * @param kuid
	 *            �������ʺ�id
	 * @param session
	 *            Session
	 * @param appid
	 *            appid
	 * @param bussinessid
	 *            bussinessid
	 * @param mobile
	 *            �ֻ�����
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-12 ����3:07:29
	 */
	public static CldSapReturn delBindMobile(int apptype, String prover,
			long kuid, String session, int appid, int bussinessid, String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("apptype", apptype);
		map.put("prover", prover);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("appid", appid);
		map.put("bussinessid", bussinessid);
		map.put("mobile", mobile);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrPPT() + "kptt_del_bind_mobile.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}

	/**
	 * ע��һ��ͨ(406)
	 * 
	 * @param apptype
	 *            Ӧ�����ͣ�1��CM��2��IOS��
	 * @param prover
	 *            �汾��
	 * @param kuid
	 *            �������ʺ�id
	 * @param session
	 *            Session
	 * @param appid
	 *            appid
	 * @param bussinessid
	 *            bussinessid
	 * @return int
	 * @author Zhouls
	 * @date 2014-11-12 ����2:54:02
	 */
	public static CldSapReturn registerByKuid(int apptype, String prover,
			long kuid, String session, int appid, int bussinessid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiver", APIVER);
		map.put("rscharset", RSCHARSET);
		map.put("rsformat", RSFORMAT);
		map.put("umsaver", UMSAVER);
		map.put("apptype", apptype);
		map.put("prover", prover);
		map.put("kuid", kuid);
		map.put("session", session);
		map.put("appid", appid);
		map.put("bussinessid", bussinessid);
		CldSapReturn errRes = CldKBaseParse.getGetParms(map,
				CldSapUtil.getNaviSvrPPT() + "kptt_register_by_kuid.php",
				CldSapUtil.decodeKey(keyCode));
		return errRes;
	}
}
