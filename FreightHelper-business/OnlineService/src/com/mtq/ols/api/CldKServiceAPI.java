/*
 * @Title CldKServiceAPI.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:57
 * @version 1.0
 */
package com.mtq.ols.api;

import java.util.List;
import android.text.TextUtils;
import com.cld.log.CldLog;
import com.cld.setting.CldSetting;
import com.mtq.ols.bll.CldBllUtil;
import com.mtq.ols.dal.CldDalKAccount;
import com.mtq.ols.sap.CldSapKMessage;
import com.mtq.ols.sap.bean.CldSapKMParm;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtBase;
import com.mtq.ols.sap.parse.CldKBaseParse.ProtKeyCode;
import com.mtq.ols.tools.CldErrUtil;
import com.mtq.ols.tools.CldSapNetUtil;
import com.mtq.ols.tools.CldSapParser;
import com.mtq.ols.tools.CldSapReturn;



/**
 * ��Ϣ���ͷ���ģ��,�ṩ���ͷ���֧��
 * 
 * @author Zhouls
 * @date 2015-3-5 ����3:38:48
 */
public class CldKServiceAPI {

	private static CldKServiceAPI cldKServiceAPI;

	private CldKServiceAPI() {

	}

	public static CldKServiceAPI getInstance() {
		if (null == cldKServiceAPI) {
			cldKServiceAPI = new CldKServiceAPI();
		}
		return cldKServiceAPI;
	}

	/**
	 * ��ʼ��
	 * 
	 * @param isTestVersion
	 *            �Ƿ��ǲ��԰汾 false ��ʽ true ����
	 * @param appPath
	 *            ����·��
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:38:58
	 */
	public void init(boolean isTestVersion, String appPath) {
		CldBllUtil.getInstance().init(isTestVersion, appPath); 
		CldKConfigAPI.getInstance().initDefConfig();
	}

	/**
	 * ����ʼ��
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:39:17
	 */
	public void uninit() {

	}

	/**
	 * �����ʼ��CldKMKey
	 * 
	 * @return void
	 * @author Zhouls
	 * @date 2015-3-5 ����3:39:28
	 */
	public void initKMkey() {
		String cldKMKey = CldSetting.getString("CldKMKey");
		if (TextUtils.isEmpty(cldKMKey)) {
			CldSapReturn errRes = CldSapKMessage.initKeyCode();
			String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
			ProtKeyCode protKeyCode = CldSapParser.parseJson(strRtn,
					ProtKeyCode.class, errRes);
			CldErrUtil.handleErr(errRes);
			if (null != protKeyCode) {
				if (errRes.errCode == 0) {
					cldKMKey = protKeyCode.getCode();
					CldSetting.put("CldKMKey", cldKMKey);
				}
			}
		}
		CldSapKMessage.keyCode = cldKMKey;
	}

	/**
	 * �����ȡduid�ӿ�
	 * 
	 * @return
	 * @return long
	 * @author Zhouls
	 * @date 2015-3-5 ����3:40:05
	 */
	public long getDuid() {
		String strDuid = CldSetting.getString("duid");
		if (TextUtils.isEmpty(strDuid)) {
			return 0;
		} else {
			return Long.parseLong(strDuid);
		}
	}

	/**
	 * �����ȡkuid�ӿ�
	 * 
	 * @return
	 * @return long
	 * @author Zhouls
	 * @date 2015-3-5 ����3:40:16
	 */
	public long getKuid() {
		String strKuid = CldSetting.getString("kuid");
		if (TextUtils.isEmpty(strKuid)) {
			return 0;
		} else {
			return Long.parseLong(strKuid);
		}
	}

	/**
	 * �����ȡsession
	 * 
	 * @return
	 * @return String
	 * @author Zhouls
	 * @date 2015-3-5 ����3:40:27
	 */
	public String getSession() {
		return CldSetting.getString("session");
	}

	/**
	 * �����ȡapptype
	 * 
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-5 ����3:40:35
	 */
	public int getApptype() {
		String strApptype = CldSetting.getString("apptype");
		if (TextUtils.isEmpty(strApptype)) {
			return 0;
		} else {
			return Integer.parseInt(strApptype);
		}
	}

	/**
	 * ��ȡҵ��Id
	 * 
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-26 ����3:03:55
	 */
	public int getBussinessid() {
		String strBussinessid = CldSetting.getString("bussinessid");
		if (TextUtils.isEmpty(strBussinessid)) {
			return 0;
		} else {
			return Integer.parseInt(strBussinessid);
		}
	}

	/**
	 * ��ȡҵ��id
	 * 
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-26 ����3:06:34
	 */
	public int getAppId() {
		String strAppId = CldSetting.getString("appid");
		if (TextUtils.isEmpty(strAppId)) {
			return 0;
		} else {
			return Integer.parseInt(strAppId);
		}
	}

	/**
	 * ������Ϣ
	 * 
	 * @param duid
	 *            �û��豸id
	 * @param apptype
	 *            Ӧ�����ͣ�1��CM��2��IOS���������";"�ָ�����"1;2"
	 * @param prover
	 *            �汾�ţ������";"�ָ�����"6.0;11.0"
	 * @param list
	 *            �ش���Ϣ�б�
	 * @param kuid
	 *            �û�Id��û����0��
	 * @param regionCode
	 *            ����������
	 * @param x
	 *            ��ǰ����x
	 * @param y
	 *            ��ǰ����y
	 * @param bussinessid
	 *            ҵ���� CM(1)
	 * @param session
	 *            ��¼���ص�session
	 * @param appid
	 *            CM(9)
	 * @return
	 * @return int
	 * @author Zhouls
	 * @date 2015-3-5 ����3:40:45
	 */
	public int recMessage(long duid, int apptype, String prover,
			List<CldSapKMParm> list, long kuid, int regionCode, long x, long y,
			int bussinessid, String session, int appid) {
		initKMkey();
		CldSapReturn errRes = CldSapKMessage.recShareMsg(duid, apptype, prover,
				kuid, regionCode, x, y, bussinessid, session, appid, 1);
		CldLog.d("ols", "errRes.url: " + errRes.url);
		String strRtn = CldSapNetUtil.sapGetMethod(errRes.url);
		CldSapParser.parseJson(strRtn, ProtBase.class, errRes);
		CldErrUtil.handleErr(errRes);
		CldLog.d("ols", errRes.errCode + "_downloadMsg");
		CldLog.d("ols", errRes.errMsg + "_downloadMsg");
		errCodeFix(errRes);
		if (errRes.errCode == 0) {
			CldSapKMessage.parseMessage(strRtn, list, CldDalKAccount
					.getInstance().getKuid(), CldBllUtil.getInstance()
					.getApptype());
		}
		return errRes.errCode;
	}
	
	/**
	 * �����봦��
	 * 
	 * @param res
	 * @return void
	 * @author zhaoqy
	 * @date 2017-4-24
	 */
	public void errCodeFix(CldSapReturn res) {
		switch (res.errCode) {
		case 501: {
			/**
			 * �������ߴ���
			 */
			if (res.session.equals(CldDalKAccount.getInstance().getSession())) {
				/**
				 * ���ӿ�ʹ��session�͵�ǰ�ʻ����session��ͬ�ż�����
				 */
				if (!TextUtils.isEmpty(res.session)) {
					CldKAccountAPI.getInstance().sessionInvalid(501, 0);
				}
			}
		}
			break;
		}
	}

	/**
	 * У���ʻ�ϵͳsession�Ƿ�ʧЧ
	 * 
	 * @param errCode
	 * @return
	 * @return boolean
	 * @author Zhouls
	 * @date 2015-3-5 ����3:41:26
	 */
	public boolean isSessionInValid(int errCode) {
		if (500 == errCode || 501 == errCode) {
			return true;
		} else {
			return false;
		}
	}
}
